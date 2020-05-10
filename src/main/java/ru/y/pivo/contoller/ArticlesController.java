package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.Comment;
import ru.y.pivo.entity.Tag;
import ru.y.pivo.entity.User;
import ru.y.pivo.maps.Pair;
import ru.y.pivo.repos.CommentRepo;
import ru.y.pivo.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ArticlesController {
    @Autowired
    private ru.y.pivo.repos.ArticleRepo ArticleRepo;
    @Autowired
    private UserRepo UserRepo;
    @Autowired
    private CommentRepo CommentRepo;
    @Autowired
    private ru.y.pivo.repos.TagRepo TagRepo;

    @GetMapping("/article")
    public String getArticle(@RequestParam Integer id,
                             Map<String, Object> model) {
        model.put("username1", SecurityContextHolder.getContext().getAuthentication().getName());
        Article article = ArticleRepo.getOne(id);
        String tags = parseTags(TagRepo.findByArticle(article));
        model.put("tags", tags);
        model.put("comments", CommentRepo.findByArticle(article));
        model.put("article", article);
        return "article";
    }

    @GetMapping("/articles")
    public String main(Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        ArrayList<Article> articles = (ArrayList<Article>) ArticleRepo.findAll();
        ArrayList<Pair<Article, String>> pairs = new ArrayList<>();
        for (Article article : articles) {
            String tagsString = parseTags(TagRepo.findByArticle(article));
            pairs.add(new Pair<>(article, tagsString));
        }

        model.put("pairs", pairs);
//        model.put("articles", articles);
        return "articles";
    }

    @GetMapping("/addArticle")
    public String addMain(Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.put("response", "");
        return "addArticle";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam Integer id,
                             @RequestParam String content,
                             Map<String, Object> model) {
        User user = UserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Article article = ArticleRepo.getOne(id);


        Comment comment = new Comment(user, article, content);
        CommentRepo.save(comment);
        model.put("comments", CommentRepo.findAll());
        return "redirect:article?id=" + id;
    }


    @PostMapping("/addArticle")
    public String add(@RequestParam String header,
                      @RequestParam String content,
                      @RequestParam String tags,
                      Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());

        Article article = ArticleRepo.findByName(header);

        if ((header.equals("") || content.equals("") || tags.equals(""))  ||
                (content.length() < 3 )  || (header.length() < 3))
        {
            model.put("response", "Отсутсвует одно из полей!");
            return "addArticle";
        }

        if (article == null) {
            User user = UserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            article = new Article(user, 0, content, header);
            ArticleRepo.save(article);
        }

        for (String s : tags.split(",")) {
            s = s.trim();
            if (!s.equals("")) {
                if (TagRepo.findByTagAndArticle(s, article) != null) {
                    Tag tag = new Tag(article, s);
                    TagRepo.save(tag);
                }
            }
        }

        return "articles";
    }

    private String parseTags(List<Tag> tags) {
        String tagsString = "";
        for (Tag tag : tags) {
            tagsString += tag.getTag() + ", ";
        }
        if (tagsString.equals("") ) return "У этой статьи нет тэгов!!!!";

        return tagsString.trim().substring(0, tagsString.length() - 2) + ".";
    }
}
