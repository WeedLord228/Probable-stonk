package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.Info;
import ru.y.pivo.entity.Pictures;
import ru.y.pivo.entity.User;
import ru.y.pivo.repos.ArticleRepo;
import ru.y.pivo.repos.InfoRepo;
import ru.y.pivo.repos.PictureRepo;
import ru.y.pivo.repos.UserRepo;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class InfoController {
    @Autowired
    private InfoRepo InfoRepo;

    @Autowired
    private PictureRepo PictureRepo;

    @Autowired
    private ArticleRepo ArticleRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/information")
    public String information(Map<String, Object> model) {
        ArrayList<Article> articles = (ArrayList<Article>) ArticleRepo.findAll();
        User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ArrayList<Article> articl = new ArrayList<Article>();

        int i = 0;

        for (Article a : articles) {
            if (articles.get(i).getAuthor().getId() == user.getId())
                articl.add(a);
            i++;
        }

        Pictures picture = PictureRepo.findByName(user.getUsername());
        Info info = InfoRepo.findByName(user.getUsername());
        model.put("picture", picture);
        model.put("info", info);
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.put("articleCount", articl.size());
        model.put("articles", articl);
        model.put("visited", user.getVisited());
        return "information";
    }

    @PostMapping("/information")
    public String addInfo(@RequestParam String content, Map<String, Object> model) {
        User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Info info = InfoRepo.findByName(user.getUsername());
        if (info == null) {
            info = new Info(user, content, user.getUsername());
        } else {
            info.setContent(content);
        }
        InfoRepo.save(info);
        return "redirect:/information";
    }

    @GetMapping("/addPhoto")
    public String addPhoto(Map<String, Object> model) {
        return "addPhoto";
    }

    @PostMapping("/addPhoto")
    public String addPicture(@RequestParam String url, Map<String, Object> model) {
        User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Pictures picture = PictureRepo.findByName(user.getUsername());
        if (picture == null) {
            picture = new Pictures(user, url.replace("\"", ""), user.getUsername());
        } else {
            picture.setUrl(url.replace("\"", ""));
        }
        PictureRepo.save(picture);
        return "redirect:/information";
    }
}
