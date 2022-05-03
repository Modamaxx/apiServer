package com.example.demo.repository.api;

import org.springframework.data.domain.Page;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T, ID> {
    void save(T object) throws SQLException;

    void delete(ID id, long dtUpdate);

    T get(ID id);

    void update(T object,long dtUpdate);

    List<T> getAll();
}
