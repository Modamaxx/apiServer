package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.type.Role;
import com.example.demo.repository.api.ProductRepository;
import com.example.demo.repository.apiDao.ProductDao;
import com.example.demo.service.api.UserService;
import com.example.security.UserHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.List;

import static com.example.demo.utils.ProductSQLQuery.*;

@Repository
public class DefaultProductRepository implements ProductRepository {

    private final ProductDao productDao;
    private final UserHolder userHolder;
    private final UserService userService;

    public DefaultProductRepository(ProductDao productDao, UserHolder userHolder, UserService userService) {
        this.productDao = productDao;
        this.userHolder = userHolder;
        this.userService = userService;
    }

    @Override
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(save, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setDouble(1, product.getCalories());
                preparedStatement.setDouble(2, product.getCarbohydrates());
//                preparedStatement.setString(3, product.getCompany());
//                preparedStatement.setObject(4, product.getUser().getDtCreate());
//                preparedStatement.setObject(5, product.getUser().getDtUpdate());
                preparedStatement.setDouble(6, product.getFats());
                preparedStatement.setDouble(7, product.getMass());
                preparedStatement.setString(8, product.getName());
                preparedStatement.setDouble(9, product.getProteins());
                preparedStatement.setLong(10, product.getUser().getId());
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys();) {
                    generatedKeys.next();
                    product.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работы с базой данных " + e.getMessage());
        }

    }

    @Override
    public Product get(Long id) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(get)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                Product product = new Product();
                product.setId(resultSet.getLong(1));
                product.setCalories(resultSet.getDouble(2));
                product.setCarbohydrates(resultSet.getDouble(3));
//                product.setCompany(resultSet.getString(4));
//                product.setDataCreate(resultSet.getTimestamp(5).toLocalDateTime());
//                product.setDataUpdate(resultSet.getTimestamp(6).toLocalDateTime());
                product.setFats(resultSet.getDouble(7));
                product.setMass(resultSet.getDouble(8));
                product.setName(resultSet.getString(9));
                product.setProteins(resultSet.getDouble(10));
                User user = new User();
                user.setId(resultSet.getLong(11));
//                user.setDtCreate(resultSet.getTimestamp(12).toLocalDateTime());
//                user.setDtUpdate(resultSet.getTimestamp(13).toLocalDateTime());
                user.setLogin(resultSet.getString(14));
                user.setPassword(resultSet.getString(15));
                user.setRole(Role.values()[converterEnum(resultSet, 16)]);
                product.setUser(user);
                return product;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работа с базы данных");
        }
    }


    @Override
    public void update(Product updateProduct, long dtUpdate) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
                preparedStatement.setDouble(1, updateProduct.getCalories());
                preparedStatement.setDouble(2, updateProduct.getCarbohydrates());
//                preparedStatement.setString(3, updateProduct.getCompany());
//                preparedStatement.setObject(4, updateProduct.getDataCreate());
//                preparedStatement.setObject(5, updateProduct.getDataUpdate());
                preparedStatement.setDouble(6, updateProduct.getFats());
                preparedStatement.setDouble(7, updateProduct.getMass());
                preparedStatement.setString(8, updateProduct.getName());
                preparedStatement.setDouble(9, updateProduct.getProteins());
                preparedStatement.setDouble(10, updateProduct.getUser().getId());
                preparedStatement.setDouble(11, updateProduct.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка работы с базой данных " + e.getMessage());
        }
    }


    @Override
    public void delete(Long id, long dtUpdate) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(delete)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работы с базой данных " + e.getMessage());
        }
    }

    private int converterEnum(ResultSet resultSet, int index) throws SQLException {
        return Integer.parseInt(resultSet.getString(index));
    }

    @Override
    public Collection<Product> getOwnProduct() {
        return null;
    }

    @Override
    public Collection<Product> getAllProduct() {
        return null;
    }
}
