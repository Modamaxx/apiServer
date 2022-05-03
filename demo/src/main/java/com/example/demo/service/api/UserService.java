package com.example.demo.service.api;

import com.example.demo.model.User;

public interface UserService extends Service<User, Long> {
    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);
}
