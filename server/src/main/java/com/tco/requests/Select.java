package com.tco.requests;

public class Select {

    private static String COLUMNS = "id,name,municipality,iso_region,iso_country,continent,latitude,longitude,altitude";
    private static String TABLE = "world";

    static String match(String match, int limit) {
        return statement(match, "DISTINCT " + COLUMNS, "LIMIT " + limit);
    }

    static String found(String match) {
        return statement(match, "COUNT(*) AS count ", "");
    }

    static String statement(String match, String data, String limit) {
        String order = "name";
        if (match.contains(("Random").toUpperCase())) {
            order = "rand()";
            match = match.substring(6);
        }

        return "SELECT "
            + data
            + " FROM " + TABLE
            + " WHERE "
            + "name LIKE \"%" + match + "%\" "
            + "OR iso_region LIKE \"%" + match + "%\" "
            + "OR municipality LIKE \"%" + match + "%\" "
            + "OR iso_country LIKE \"%" + match + "%\" "
            + "OR id LIKE \"%" + match + "%\" "
            + "ORDER BY " + order + " " + limit
            + " ;";
    }
}
