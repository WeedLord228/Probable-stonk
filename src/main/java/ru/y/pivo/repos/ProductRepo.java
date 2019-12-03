package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.y.pivo.entity.Product;
import ru.y.pivo.entity.Store;

import java.util.List;


public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findByName(String name);

    Product findByStoreAndName(Store id, String name);
}
