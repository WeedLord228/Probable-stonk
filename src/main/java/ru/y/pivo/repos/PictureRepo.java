package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Pictures;

public interface PictureRepo extends JpaRepository<Pictures,Integer> {

    Pictures findByName(String name);

}
