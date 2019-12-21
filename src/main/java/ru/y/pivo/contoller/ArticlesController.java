package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.Comment;
import ru.y.pivo.entity.User;
import ru.y.pivo.repos.CommentRepo;
import ru.y.pivo.repos.UserRepo;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class ArticlesController {
    @Autowired
    private ru.y.pivo.repos.ArticleRepo ArticleRepo;
    @Autowired
    private UserRepo UserRepo;
    @Autowired
    private CommentRepo CommentRepo;

    @GetMapping("/article")
    public String getArticle(@RequestParam Integer id,
                             Map<String, Object> model) {
        model.put("username1", SecurityContextHolder.getContext().getAuthentication().getName());
        Article article = ArticleRepo.getOne(id);
        model.put("comments",CommentRepo.findByArticle(article));
        model.put("article", article);
        return "article";
    }

    @GetMapping("/articles")
    public String main(Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        ArrayList<Article> articles = (ArrayList<Article>) ArticleRepo.findAll();
        model.put("articles", articles);
        return "articles";
    }

    @GetMapping("/addArticle")
    public String addMain(Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "addArticle";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam Integer id,
                             @RequestParam String content,
                             Map<String, Object> model) {
        User user = UserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Article article = ArticleRepo.getOne(id);

        Comment comment = new Comment(user,article,content);
        CommentRepo.save(comment);
        model.put("comments",CommentRepo.findAll());
        return "redirect:article?id="+id;
    }


    @PostMapping("/addArticle")
    public String add(@RequestParam String header,
                      @RequestParam String content,
                      Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());

        Article article = ArticleRepo.findByName(header);

        if (article == null) {
            User user = UserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            article = new Article(user, 0, content, header);
            ArticleRepo.save(article);
        }

        return "articles";
    }
}
