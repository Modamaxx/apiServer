package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.type.Role;
import com.example.demo.repository.api.UserRepository;
import com.example.demo.repository.apiDao.UserDao;
import com.example.demo.service.api.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDao userDao;

    public DefaultUserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    @Override
    public void save(User user) throws SQLException {
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
//        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
//        userRepository.delete(id, dtUpdate);
    }

    @Override
    public User get(Long id) {
        return userDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Пользователя по данному id не существует"));
//        return userRepository.get(id);
    }

    @Override
    public void update(User updateUser, Long idUser) {
        User user = get(idUser);
        updateUser.setId(idUser);
        updateUser.setRole(user.getRole());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(updateUser);
//        userRepository.update(updateUser, dtUpdate);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
//        return userRepository.getAll(filter);
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
//        return userRepository.findByLogin(login);
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
}