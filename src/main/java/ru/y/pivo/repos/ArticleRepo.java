package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Article;

import java.util.Optional;

public interface ArticleRepo extends JpaRepository<Article,Integer> {
//    Article findByArticleId(Integer articleId);

    Article findByName(String name);

}
