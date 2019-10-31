package ru.y.pivo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        //model.put("some", "hello, letsCode!");
        return "index";
    }

    @GetMapping("/signin")
    public String signIn(Map<String, Object> model) {
        return "signin";
    }

    @GetMapping("signup")
    public String signUp(Map<String, Object> model) {
        return "signup";
    }
}
