package com.example.demo.utils;

public class IngredientSQLQuery {
    private IngredientSQLQuery() {
    }
    public static final String save="insert into demo.ingredient " +
            "(mass, product) " +
            "values (?, ?)";
}
