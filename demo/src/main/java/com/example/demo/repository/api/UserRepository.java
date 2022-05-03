package com.example.demo.repository.api;

import com.example.demo.model.User;

public interface UserRepository extends Repository<User, Long> {
    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);
}
