package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.Tag;

import java.util.List;

public interface TagRepo extends JpaRepository<Tag,Integer> {
    List<Tag> findByArticle(Article article);

    List<Tag> findByTag(String tag);

    Tag findByTagAndArticle(String tag, Article article);
}
