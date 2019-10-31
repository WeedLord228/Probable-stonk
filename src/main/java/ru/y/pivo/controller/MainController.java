package ru.y.pivo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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


}
