package com.example.demo.utils;

public class JournalSQLQuery {
    private JournalSQLQuery() {
    }

    public static final String getOneDay = "select journal.id,journal.dt_create," +
            "journal.dt_update,journal.mass,journal.product_id,journal.profile_id," +
            "journal.recipe_id,journal.type_eating " +
            "from demo.journal journal left outer join " +
            "demo.profile profile " +
            "on journal.profile_id=profile.id " +
            "where profile.id=? and (journal.dt_create between ? and ?)";
}
