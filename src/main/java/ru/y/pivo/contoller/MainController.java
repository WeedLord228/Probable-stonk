package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.Role;
import ru.y.pivo.entity.User;
import ru.y.pivo.repos.ArticleRepo;
import ru.y.pivo.repos.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Map<String, Object> model) {
            return "home";
    }

    @GetMapping("/login")
    public String login(Map <String,Object> model){
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "login";
    }

    @GetMapping("/index")
    public String index(Map<String, Object> model) {
        model.put("username",SecurityContextHolder.getContext().getAuthentication().getName());
        return "index";
    }

    @GetMapping("/maps")
    public String maps(Map<String, Object> model) {
        model.put("username",SecurityContextHolder.getContext().getAuthentication().getName());
        return "maps";
    }

    @Autowired
    private ru.y.pivo.repos.ArticleRepo ArticleRepo;

    @GetMapping("/information")
    public String information(Map<String, Object> model){
        ArrayList<Article> articles = (ArrayList<Article>) ArticleRepo.findAll();
        model.put("username",SecurityContextHolder.getContext().getAuthentication().getName());
        model.put("articleCount" , articles.size()-1);
        model.put("articles",articles);

        return "information";
    }

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
