package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tco.requests.Database;

public class TestDatabase {
    private Database db = new Database();

    @Test
    @DisplayName("tamo: existential test")
    public void testDatabaseInit() {
        // db = new Database();
        try {
            assertEquals(35, db.found("Texas"));
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }
}