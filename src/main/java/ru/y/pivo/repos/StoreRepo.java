package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Store;

public interface StoreRepo extends JpaRepository<Store,Integer> {
    Store findByAdress(String address);

    Store findByName(String name);
}
