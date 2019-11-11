package ru.y.pivo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
