package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Article;
import ru.y.pivo.entity.Comment;
import ru.y.pivo.entity.User;

import java.util.List;

public interface CommentRepo extends JpaRepository <Comment,Integer> {
    List<Comment> findByArticle(Article article);

    List<Comment> findByAuthor(User user);
}
