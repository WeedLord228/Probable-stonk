package ru.y.pivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.y.pivo.domain.User;
import ru.y.pivo.repos.UserRepo;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/signup")
    public String registration()
    {
        return "signup";
    }

    @GetMapping("/signup")
    public String addUser(User user, Map<String,Object> model)
    {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null)
        {
            model.put("message","Пользователь уже существует!");
            return "signup";
        }

        user.setActive(true);


        return "redirect:/signin";
    }

}
