package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.type.Role;
import com.example.demo.repository.api.UserRepository;
import com.example.demo.repository.apiDao.UserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

import static com.example.demo.utils.UserSQLQuery.*;

@Repository
public class DefaultUserRepository implements UserRepository {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public DefaultUserRepository(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) throws SQLException {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(save, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getRole().ordinal());
//                preparedStatement.setObject(4, user.getDtCreate());
//                preparedStatement.setObject(5, (user.getDtUpdate()));
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys();) {
                    generatedKeys.next();
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работы с базой данных " + e.getMessage());
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

    @Override
    public User get(Long id) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(get)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                User user = new User();
                user.setId(resultSet.getLong(1));
//                user.setDtCreate(resultSet.getTimestamp(2).toLocalDateTime());
//                user.setDtUpdate(resultSet.getTimestamp(3).toLocalDateTime());
                user.setLogin(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setRole(Role.values()[converterEnum(resultSet, 6)]);
                return user;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("User с данным id не существует");
        }
    }

    @Override
    public void update(User updateUser, long dtUpdate) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(update)) {
//                preparedStatement.setObject(1, updateUser.getDtCreate());
//                preparedStatement.setObject(2, updateUser.getDtUpdate());
                preparedStatement.setString(3, updateUser.getLogin());
                preparedStatement.setString(4, updateUser.getPassword());
                preparedStatement.setInt(5, updateUser.getRole().ordinal());
                preparedStatement.setLong(6, updateUser.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка работы с базой данных " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public User findByLogin(String login) {
        try (Connection con = ConnectionJDBC.getInstance();) {
            try (PreparedStatement preparedStatement = con.prepareStatement(findByLogin)) {
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                User user = new User();
                user.setId(resultSet.getLong(1));
//                user.setDtCreate(resultSet.getTimestamp(2).toLocalDateTime());
//                user.setDtUpdate(resultSet.getTimestamp(3).toLocalDateTime());
                user.setLogin(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setRole(Role.values()[converterEnum(resultSet, 6)]);
                return user;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка работы с базой данных " + e.getMessage());
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new IllegalArgumentException("Данные введены неверно, проверьте ваш пароль или логин");
    }

    private int converterEnum(ResultSet resultSet, int index) throws SQLException {
        return Integer.parseInt(resultSet.getString(index));
    }
}