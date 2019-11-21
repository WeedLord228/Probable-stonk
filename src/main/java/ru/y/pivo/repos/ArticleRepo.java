package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.User;

import java.util.ArrayList;

public interface ArticleRepo extends JpaRepository<Article,Integer> {
    Article findByName(String name);

    ArrayList<Article> findByAuthor(User auyhor);

}
