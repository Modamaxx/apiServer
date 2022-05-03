package com.example.demo.utils;

public class ProfileSQLQuery {
    private ProfileSQLQuery() {

    }
    public static final String save = "insert into demo.profile (birthday, create_time, update_time," +
            " gender, goal, goal_weight, height, lifestyle, user_id, weight_actual)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String get = "select p.id,p.birthday,p.create_time,p.update_time,p.gender,p.goal,p.goal_weight,\n" +
            "p.height,p.lifestyle,p.weight_actual,u.id,u.create_time,\n" +
            "u.update_time,u.login,u.password,u.role\n" +
            "from demo.profile p\n" +
            "left outer join demo.user u on p.user_id=u.id\n" +
            "where p.id=?";
    public static final String update = "update demo.profile " +
            "set birthday=?,create_time=?,update_time=?," +
            "gender=?,goal=?,goal_weight=?,height=?," +
            "lifestyle=?,user_id=?,weight_actual=? " +
            "where id=?";

    public static final String delete="delete from demo.profile where id=?";
}
