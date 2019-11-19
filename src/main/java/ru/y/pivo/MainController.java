package ru.y.pivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping
    public String main(Map<String, Object> model) {
        return "index";
    }

    @GetMapping("/index")
    public String index(Map<String, Object> model) {
        return "index";
    }
    @GetMapping("/maps")
    public String maps(Map<String, Object> model) {
        return "maps";
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

//      TODO:
//    this can show username on main page
//    @GetMapping("/information")
//    public String information() {return "information";}
//
//    @PostMapping("/information")
//    public String getInfo(){
//        return
//    }
}
