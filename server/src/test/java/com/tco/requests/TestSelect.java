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
        assertEquals(sel.match("Chris", 15), "SELECT DISTINCT id,name,municipality,iso_region,iso_country,continent,latitude,longitude,altitude FROM world WHERE name LIKE \"%" + "Chris" + "%\" " + "LIMIT 15 ;"); 
    }

    @Test
    @DisplayName("cjbeall: test match chris")
    public void testFound() {
        assertEquals( sel.found("Chris"), "SELECT COUNT(*) AS count  FROM world WHERE name LIKE \"%" + "Chris" + "%\"" + "  ;"); 
    }
}
