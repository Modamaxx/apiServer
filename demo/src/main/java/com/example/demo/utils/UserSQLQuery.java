package com.example.demo.utils;

public class UserSQLQuery {
    private UserSQLQuery() {

    }

    public static final String findByLogin = "SELECT id,create_time,update_time,login,password,role" +
            " FROM demo.user WHERE login = ?";
    public static final String save = "INSERT INTO demo.user(login,password,role,create_time,update_time) VALUES (?,?,?,?,?);";
    public static final String delete = "delete from demo.user where id=?;";
    public static final String get = "SELECT id,create_time,update_time,login,password,role FROM demo.user WHERE id = ?";
    public static final String update = "update demo.user set create_time=?, update_time=?, login=?, password=?, role=?" +
            " where  id=?";


}
