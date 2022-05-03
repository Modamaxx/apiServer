package com.example.demo.service.api;

import com.example.demo.model.Product;

import java.util.Collection;

public interface ProductService extends Service<Product, Long> {
    public Collection<Product> getOwnProduct();

    public Collection<Product> getAllProduct();
}
