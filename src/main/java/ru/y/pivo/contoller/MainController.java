package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.y.pivo.entity.Role;
import ru.y.pivo.entity.User;
import ru.y.pivo.repos.ArticleRepo;
import ru.y.pivo.repos.InfoRepo;
import ru.y.pivo.repos.UserRepo;

import java.util.Collections;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private InfoRepo InfoRepo;

    @Autowired
    private ArticleRepo ArticleRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/login")
    public String login(Map<String, Object> model) {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "login";
    }

    @GetMapping("/index")
    public String index(Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setVisited(user.getVisited() + 1);
        userRepo.save(user);
        return "index";
    }

    @GetMapping("/maps")
    public String maps(Map<String, Object> model) {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "maps";
    }

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
