package com.example.demo.repository.apiDao;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductDao extends JpaRepository<Product, Long> {

    Page<Product> findProductByName(String name, Pageable pageable);

    @Query(value = "SELECT * FROM demo.product p WHERE p.user_id = ?",
            nativeQuery = true)
    Collection<Product> getProductsByIdCreator(Long id);
}
