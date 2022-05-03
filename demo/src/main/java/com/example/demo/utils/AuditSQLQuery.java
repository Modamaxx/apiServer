package com.example.demo.utils;

public class AuditSQLQuery {

    private AuditSQLQuery() {
    }

    public static final String save = "insert into demo.audit (description, dt_create, essence_id, essence_name, user_id) " +
            "values (?, ?, ?, ?, ?)";
}
