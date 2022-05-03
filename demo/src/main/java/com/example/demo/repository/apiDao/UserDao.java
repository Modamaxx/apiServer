package com.example.demo.repository.apiDao;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
