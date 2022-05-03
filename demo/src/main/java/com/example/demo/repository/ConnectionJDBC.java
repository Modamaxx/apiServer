package com.example.demo.repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionJDBC {
    private static final ComboPooledDataSource cpds;

    static {
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.postgresql.Driver");

        } catch (PropertyVetoException e) {
            throw new RuntimeException("Ошибка загрузки драйвера");
        }
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/crm");
        cpds.setUser("postgres");
        cpds.setPassword("eputet91");
    }

    public static Connection getInstance() throws SQLException {
        return cpds.getConnection();
    }
}
