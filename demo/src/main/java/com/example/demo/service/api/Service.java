package com.example.demo.service.api;

import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;

public interface Service<T, ID> {
    void save(T object) throws SQLException;

    void delete(ID id);

    T get(ID id);

    void update(T object, Long id);

    List<T> getAll();
}
