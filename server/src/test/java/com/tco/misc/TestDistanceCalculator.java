package com.tco.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tco.misc.DistanceCalculator.calculator;
import static java.lang.Math.toRadians;
import static java.lang.Math.round;
import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

public class TestDistanceCalculator {

    static class Geo implements GeographicCoordinate{
        // initialize the latitude and longitude
        private Double degreesLatitude;
        private Double degreesLongitude;

        // override interface to return longitude in radians
        public Double lonRadians() {
            return toRadians(this.degreesLongitude);
        }

        // override interface to return latitude in radians
        public Double latRadians() {
            return toRadians(this.degreesLatitude);
        }

        // constructor for the interface
        public Geo(Double lat, Double lon) {
            this.degreesLatitude = lat;
            this.degreesLongitude = lon;
        }
    }

    // create the test coordinates
    final Geo origin = new Geo(0., 0.);
    final Geo east_180 = new Geo(0., 180.);
    final Geo west_180 = new Geo(0., -180.);
    final Geo south_90 = new Geo(-90., 0.);
    final Geo north_90 = new Geo(90., 0.);
    final Geo lessThanOneDegree = new Geo(0.00000000001, 0.);
    final Geo north_45 = new Geo(45.0, 0.0);
    final Geo south_45 = new Geo(-45.0, 0.0);
    final Geo east_90 = new Geo(0.0, 90.0);
    final Geo west_90 = new Geo(0.0, -90.0); 

    // Setup a small earthRadius variable set
    final static long small_radius = 1L;
    final static long pi_small_radius = round(PI * small_radius);
    final static long halfpi_small_radius = round(PI / 2.0 * small_radius);
    final static double small_circumference = (2 * PI * small_radius);

    // Setup a big earthRadius variable set
    final static long big_radius = 1000000000L;
    final static long pi_big_radius = round(PI * big_radius);
    final static long halfpi_big_radius = round(PI / 2.0 * big_radius);
    final static double big_circumference = (2 * PI * big_radius);

    // test same location
    @Test
    @DisplayName("davematt: distance to self. Expected 0")
    public void testDistanceToSelf() {
        assertEquals(0L, calculator(origin, origin, small_radius));
        assertEquals(0L, calculator(origin, origin, big_radius));
    }

    // test same place, different location
    @Test
    @DisplayName("davematt: distance to same place. Expected 0")
    public void testDistanceToSamePlace() {
        assertEquals(0L, calculator(east_180, west_180, small_radius));
        assertEquals(0L, calculator(west_180, east_180, big_radius));
    }

    // test halfway around world
    @Test
    @DisplayName("clayroby: halfway around world. Expected earthCircumference / 2")
    public void testDistanceToHalfway() {
        assertEquals((long)round(small_circumference / 2), calculator(origin, west_180, small_radius));
        assertEquals((long)round(big_circumference / 2), calculator(origin, east_180, big_radius));
    }

    @Test
    @DisplayName("Cjbeall18: testing distance between origin and lat value less than 1 degree. Expected 0")
    public void testDistanceBetweenCoorinatePoints() {
        assertEquals(0L, calculator(origin, lessThanOneDegree, small_radius));
        assertEquals(0L, calculator(origin, lessThanOneDegree, big_radius));
    }

    @Test
	@DisplayName("evanloy: Distance from North Pole to South Pole. Expected earthCircumference / 2")
	public void testDistancePoleToPole() {
		assertEquals((long)round(small_circumference / 2), calculator(north_90, south_90, small_radius));
		assertEquals((long)round(big_circumference / 2), calculator(north_90, south_90, big_radius));
	}

    @Test
    @DisplayName("epitera: testing equator to the poles. Expected circumference / 4")
    public void testEquatorToPoles() {
        assertEquals((long)round(small_circumference / 4), calculator(origin, north_90, small_radius));
        assertEquals((long)round(big_circumference / 4), calculator(origin, south_90, big_radius));
    }

    @Test
    @DisplayName("tamo: testing equator half way north, south, east, west. Expected circumference / 8 NORTH&SOUTH. Expected circumference / 4 EAST&WEST")
    public void test() {
        assertEquals((long)round(small_circumference / 8), calculator(origin,  north_45, small_radius));
        assertEquals((long)round(big_circumference / 8), calculator(origin,  north_45, big_radius));
        assertEquals((long)round(small_circumference / 8), calculator(origin,  south_45, small_radius));
        assertEquals((long)round(big_circumference / 8), calculator(origin,  south_45, big_radius));

        assertEquals((long)round(small_circumference / 4), calculator(origin,  east_90, small_radius));
        assertEquals((long)round(big_circumference / 4), calculator(origin,  east_90, big_radius));
        assertEquals((long)round(small_circumference / 4), calculator(origin,  west_90, small_radius));
        assertEquals((long)round(big_circumference / 4), calculator(origin,  west_90, big_radius));

    }
}
