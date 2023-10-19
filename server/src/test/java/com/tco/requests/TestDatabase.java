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
        try {
            assertEquals(40, db.found("Texas"));
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }

    @Test
    @DisplayName("tamo: empty string")
    public void testEmptyString() {
        try {
            assertEquals(50427, db.found(""));
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }

    @Test
    @DisplayName("clayroby: ryp match")
    public void testRYPString() {
        try {
            assertEquals(4, db.found("ryp"));
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }

    @Test
    @DisplayName("evanloy: retrieve places based on match")
    public void testRetrievePlaces() {
        try {
            Places result = db.places("Texas", 5);
            assertEquals(5, result.size());
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }

    @Test
    @DisplayName("ejpitera: test colorado")
    public void testColorado() {
        try {
            Places result = db.places("Colorado", 40);
            assertEquals(40, result.size());
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }
}