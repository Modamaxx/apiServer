package com.example.demo.service.validator;

import com.example.demo.model.Product;
import com.example.demo.service.api.ProductService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductValidatorService {

    private final ProductService productService;

    public ProductValidatorService(ProductService productService) {
        this.productService = productService;
    }

    @Before("execution(* com.example.demo.service.DefaultProductService.save(..))")
    public void save(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Product product = (Product) args[0];
        validBasicParameters(product);
    }

    @Before("execution(* com.example.demo.service.DefaultProductService.update(..))")
    public void update(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Product updateProduct = (Product) args[0];
        validBasicParameters(updateProduct);
    }

    @Before("execution(* com.example.demo.service.DefaultProductService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long idProduct = (Long) args[0];
    }

    public void validBasicParameters(Product product) {
        if (product.getCalories() <= 0) {
            throw new IllegalArgumentException("Данные калории введены неверено");
        }
        if (product.getCarbohydrates() <= 0) {
            throw new IllegalArgumentException("Данные углеводов введены неверено");
        }
        if (product.getFats() <= 0) {
            throw new IllegalArgumentException("Данные жиров введены неверено");
        }
        if (product.getProteins() <= 0) {
            throw new IllegalArgumentException("Данные белков введены неверено");
        }
        if (product.getMass() <= 0) {
            throw new IllegalArgumentException("Данные массы продукта введены неверено");
        }

        if (product.getName() == null || product.getName().equals("")) {
            throw new IllegalArgumentException("Имя продукта введено неверено");
        }
    }
}
