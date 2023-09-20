package com.tco.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlace {
    
    @Test
    @DisplayName("davematt:")
    public void testorigin() {
        Place place = new Place("0", "0");
        assertEquals(0.0, place.latRadians());
        assertEquals(0.0, place.lonRadians());
    }

    @Test
    @DisplayName("davematt:")
    public void testDatelinePositive() {
        Place place = new Place("0", "180");
        assertEquals(0.0, place.latRadians());
        assertEquals(PI, place.lonRadians());
    }

    @Test
    @DisplayName("davematt:")
    public void testNorthPole() {
        Place place = new Place("90", "0");
        assertEquals(PI/2.0, place.latRadians());
        assertEquals(0, place.lonRadians());
    }

    @Test
    @DisplayName("davematt:")
    public void testDatelineNegative() {
        Place place = new Place("0", "-180");
        assertEquals(0.0, place.latRadians());
        assertEquals(-PI, place.lonRadians());
    }

    @Test
    @DisplayName("evanloy: Testing South Pole. Expected output (-PI/2, 0)")
    public void testSouthPole() {
        Place place = new Place("-90", "0");
        assertEquals(-PI/2.0, place.latRadians());
        assertEquals(0, place.lonRadians());
    }

    @Test
    @DisplayName("ejpitera: Testing lat 45, long 150. Expected output: (Pi/4, 5*PI/6)")
    public void test45_150() {
        Place place = new Place("45", "150");
        assertEquals(PI/4, place.latRadians());
        assertEquals(5*PI/6, place.lonRadians());
    }

    @Test
    @DisplayName("tamo: Testing lat -180, long 180. Expected output: (-PI, PI)")
    public void testNeg180_180() {
        Place place = new Place("-180", "180");
        assertEquals(-PI, place.latRadians());
        assertEquals(PI, place.lonRadians());
    }
    @Test
    @DisplayName("cjbeall: Testing lat 60, long 120. Expected output: (PI/3, 2PI/3)")
    public void test60_120() {
        Place place = new Place("60", "120");
        assertEquals(PI/3, place.latRadians());
        assertEquals(2*PI/3, place.lonRadians());
    }
    
    @Test
    @DisplayName("clayroby: Testing lat 34, long -207. Expected output: (34 * (PI/180), -207 * (PI/180))")
    public void test34_Neg207() {
        Place place = new Place("34", "-207");
        assertEquals(34 * (PI/180), place.latRadians());
        assertEquals(-207 * (PI/180), place.lonRadians());
    }
}
