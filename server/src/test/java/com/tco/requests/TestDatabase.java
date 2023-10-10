package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tco.requests.Database;

public class TestDatabase {
    private Database db;

    @Test
    @DisplayName("tamo: existential test")
    public void testDatabaseInit() {
        db = new Database();
        assertEquals(0, db.found());
    }
}