package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.y.pivo.entity.Store;


public interface StoreRepo extends JpaRepository<Store, Integer> {
    Store findByAddress(String addresss);
}
