package com.example.demo.utils;

public class RecipeSQLQuery {
    private RecipeSQLQuery() {
    }

    public static final String saveRecipe = "insert into demo.recipe " +
            "(dt_create, dt_update, name, user_id) " +
            "values (?, ?, ?, ?)";

    public static final String saveRecipeIngredient = "insert into " +
            "demo.recipe_ingredient (recipe_id, ingredient_id) " +
            "values (?, ?)";

    public static final String get = "select recipe.id,recipe.dt_create,recipe.dt_update,\n" +
            "recipe.name,ingredient.recipe_id, \n" +
            "ingredient2.id,ingredient2.mass,\n" +
            "product.id,product.calories,\n" +
            "product.carbohydrates,product.company,product.data_create,\n" +
            "product.data_update,product.fats,\n" +
            "product.mass,product.name,product.proteins,ingredient2.product,\n" +
            "user4.id,user4.create_time,user4.update_time,\n" +
            "user4.login,user4.password,user4.role,recipe.user_id,product.user_id\n" +
            "from demo.recipe recipe \n" +
            "left outer join demo.recipe_ingredient ingredient\n" +
            "on recipe.id=ingredient.recipe_id \n" +
            "left outer join demo.ingredient ingredient2 \n" +
            "on ingredient.ingredient_id=ingredient2.id \n" +
            "left outer join demo.product product \n" +
            "on ingredient2.product=product.id \n" +
            "left outer join demo.user user4 \n" +
            "on product.user_id=user4.id \n" +
            "left outer join demo.user user5 \n" +
            "on recipe.user_id=user5.id \n" +
            "where recipe.id=?";
}
