package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Article;

public interface ArticleRepo extends JpaRepository<Article,Integer> {

    Article findByName(String name);
}
