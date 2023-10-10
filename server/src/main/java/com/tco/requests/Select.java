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
        return "SELECT "
            + data
            + " FROM " + TABLE
            + " WHERE name LIKE \"%" + match + "%\" "
            + limit
            + " ;";
    }
}