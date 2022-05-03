package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.api.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Collection<Product>> getAll() {
        Collection<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product product = productService.get(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Product product) throws SQLException {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Product updateProduct, @PathVariable Long id) {
        productService.update(updateProduct, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/own")
    public ResponseEntity<Collection<Product>> getOwnAll() {
        Collection<Product> products = productService.getOwnProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}