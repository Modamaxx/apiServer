package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.api.ProductRepository;
import com.example.demo.repository.apiDao.ProductDao;
import com.example.demo.service.api.ProductService;
import com.example.demo.service.api.UserService;
import com.example.security.UserHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    private final ProductDao productDao;
    private final UserHolder userHolder;
    private final UserService userService;
    private final ProductRepository productRepository;

    public DefaultProductService(ProductDao productDao, UserHolder userHolder, UserService userService, ProductRepository productRepository) {
        this.productDao = productDao;
        this.userHolder = userHolder;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) throws SQLException {
        String login = userHolder.getAuthentication().getName();
        User user = userService.findByLogin(login);
        product.setUser(user);
        productDao.save(product);
//      productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product по id не найден"));
        if (!isValidWhoReq(product)) {
            throw new IllegalArgumentException("Доступ к данному продукту вам закрыт");
        }
        return product;
//      return productRepository.get(id);
    }


    @Override
    public void update(Product updateProduct, Long idProduct) {
        Product product = get(idProduct);
        updateProduct.setId(idProduct);
        updateProduct.setUser(product.getUser());
        productDao.save(updateProduct);
//        productRepository.update(updateProduct, dtUpdate);
    }

    @Override
    public void delete(Long id) {
        Product product = get(id);
        productDao.delete(product);
//        productRepository.delete(id, dtUpdate);
    }

    @Override
    public Collection<Product> getOwnProduct() {
        String login = userHolder.getAuthentication().getName();
        Long id = userService.findByLogin(login).getId();
        return productDao.getProductsByIdCreator(id);
    }

    @Override
    public Collection<Product> getAllProduct() {
        Long id = userService.findByLogin("admin").getId();
        return productDao.getProductsByIdCreator(id);
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    private boolean isValidWhoReq(Product product) {
        if (product.getUser().getLogin().equals("admin")) {
            return true;
        }
        String login = userHolder.getAuthentication().getName();
        return product.getUser().getLogin().equals(login) || login.equals("admin");
    }
}
