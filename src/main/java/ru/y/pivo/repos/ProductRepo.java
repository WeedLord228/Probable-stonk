package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import ru.y.pivo.entity.Product;

import java.util.ArrayList;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    ArrayList<Repository> findByName(String name);
}
