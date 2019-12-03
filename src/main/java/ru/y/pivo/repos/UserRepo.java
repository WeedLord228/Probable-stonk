package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.y.pivo.entity.User;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
