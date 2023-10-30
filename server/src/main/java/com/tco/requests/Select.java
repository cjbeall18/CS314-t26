package com.tco.requests;

public class Select {

    private static String COLUMNS = "world.id,world.name,world.municipality,region.name AS region,country.name AS country,continent.name AS continent,world.latitude,world.longitude,world.altitude";
                                    // "id,name,municipality,iso_region,iso_country,continent,latitude,longitude,altitude";
    private static String TABLE = "world";

    static String match(String match, int limit) {
        return statement(match, "DISTINCT " + COLUMNS, "LIMIT " + limit);
    }

    static String found(String match) {
        return statement(match, "COUNT(*) AS count ", "");
    }

    static String statement(String match, String data, String limit) {
        String order = "continent.name, country.name, region.name, world.municipality, world.name ASC";
        if (match.contains(("Random").toUpperCase()) || match == "") {
            order = "rand()";
            if (match != "") {
                match = match.substring(6);
            }
        }

        return "SELECT "
            + data
            + " FROM " + TABLE
            + " INNER JOIN continent ON world.continent = continent.id"
            + " INNER JOIN country ON world.iso_country = country.id"
            + " INNER JOIN region ON world.iso_region = region.id"
            + " WHERE "
            + "world.id LIKE \"%" + match + "%\" "
            + "OR world.name LIKE \"%" + match + "%\" "
            + "OR continent.name LIKE \"%" + match + "%\" "
            + "OR country.name LIKE \"%" + match + "%\" "
            + "OR region.name LIKE \"%" + match + "%\" "
            + "OR world.municipality LIKE \"%" + match + "%\" "
            + "ORDER BY " + order + " " + limit
            + " ;";
    }
}
