package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.User;
import ru.y.pivo.repos.UserRepo;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class ArticlesController {
    @Autowired
    private ru.y.pivo.repos.ArticleRepo ArticleRepo;
    @Autowired
    private UserRepo UserRepo;

    @GetMapping("/article")
    public String getArticle(@RequestParam Integer id,
                             Map<String, Object> model)
    {
        Article article = ArticleRepo.getOne(id);
        model.put("article",article);
        return "article";
    }


    @GetMapping("/articles")
    public String main(Map<String, Object> model) {
        ArrayList<Article> articles = (ArrayList<Article>) ArticleRepo.findAll();
        model.put("articles", articles);
        return "articles";
    }

    @GetMapping("/addArticles")
    public String addMain() {

        return "addArticles";
    }

    @PostMapping("/addArticle")
    public String add(@RequestParam String header,
                      @RequestParam String content,
                      Map<String, Object> model) {

        Article article = ArticleRepo.findByName(header);


        if (article == null) {
            User user = UserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            article = new Article(user, 0, content, header);
            ArticleRepo.save(article);
        }

        return "addArticles";
    }
}
