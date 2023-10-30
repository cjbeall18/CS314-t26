package com.tco.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelect {
    Select sel = new Select();

    @Test
    @DisplayName("cjbeall: test match, chris limit 15")
    public void testMatch() {
        assertEquals(sel.match("Chris", 15), "SELECT DISTINCT world.id,world.name,world.municipality,region.name AS region,country.name AS country,continent.name AS continent,world.latitude,world.longitude,world.altitude FROM world INNER JOIN continent ON world.continent = continent.id INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE world.id LIKE \"%" + "Chris" + "%\"" + " OR world.name LIKE \"%" + "Chris" + "%\"" + " OR continent.name LIKE \"%" + "Chris" + "%\"" + " OR country.name LIKE \"%" + "Chris" + "%\"" +  " OR region.name LIKE \"%" + "Chris" + "%\"" + " OR world.municipality LIKE \"%" + "Chris" + "%\"" + " ORDER BY continent.name, country.name, region.name, world.municipality, world.name ASC " + "LIMIT 15 ;"); 
    }
    @Test
    @DisplayName("cjbeall: test match chris")
    public void testFound() {
        assertEquals( sel.found("Chris"), "SELECT COUNT(*) AS count  FROM world INNER JOIN continent ON world.continent = continent.id INNER JOIN country ON world.iso_country = country.id INNER JOIN region ON world.iso_region = region.id WHERE world.id LIKE \"%" + "Chris" + "%\"" + " OR world.name LIKE \"%" + "Chris" + "%\"" + " OR continent.name LIKE \"%" + "Chris" + "%\"" + " OR country.name LIKE \"%" + "Chris" + "%\"" +  " OR region.name LIKE \"%" + "Chris" + "%\"" + " OR world.municipality LIKE \"%" + "Chris" + "%\"" + " ORDER BY continent.name, country.name, region.name, world.municipality, world.name ASC " + " ;"); 
    }
}