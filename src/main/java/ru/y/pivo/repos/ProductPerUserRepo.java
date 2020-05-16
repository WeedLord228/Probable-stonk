package ru.y.pivo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.y.pivo.entity.Product;
import ru.y.pivo.entity.ProductPerUser;
import ru.y.pivo.entity.User;

public interface ProductPerUserRepo extends JpaRepository<ProductPerUser, Integer> {
    ProductPerUser findByUserAndProduct(User userId, Product productId);
    ProductPerUser findTopByOrderBySearchesDesc();
}

