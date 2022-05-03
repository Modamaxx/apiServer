package com.example.demo.utils;

public class ProductSQLQuery {

    private ProductSQLQuery() {
    }

    public static final String save = " insert into demo.product(calories, carbohydrates, company, data_create," +
            "data_update,fats,mass,name,proteins,user_id) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String update = "update demo.product " +
            "set calories=?,carbohydrates=?,company=?," +
            "data_create=?,data_update=?,fats=?," +
            "mass=?,name=?,proteins=?,user_id=? " +
            "where id=?";

    public static final String delete = "delete from demo.product where id=?";

    public static final String get = "select product.id,product.calories,product.carbohydrates,product.company,\n" +
            "product.data_create,product.data_update,product.fats,\n" +
            "product.mass,product.name,product.proteins,\n" +
            "demo.user.id,create_time,update_time,login,password,\n" +
            "role\n" +
            "from demo.product product\n" +
            "left outer join demo.user on product.user_id=demo.user.id \n" +
            "where product.id=?";
}
