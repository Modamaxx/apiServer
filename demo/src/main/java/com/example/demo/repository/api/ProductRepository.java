package com.example.demo.repository.api;

import com.example.demo.model.Product;

import java.util.Collection;

public interface ProductRepository extends Repository<Product, Long> {
    public Collection<Product> getOwnProduct();

    public Collection<Product> getAllProduct();
}
