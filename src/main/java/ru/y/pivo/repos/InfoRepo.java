package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Info;

public interface InfoRepo extends JpaRepository<Info,Integer> {

    Info findByName(String name);

}
