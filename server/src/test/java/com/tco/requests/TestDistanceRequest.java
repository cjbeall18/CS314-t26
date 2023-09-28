package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestDistanceRequest {
    
    private DistancesRequest distance;

    @Test
    @DisplayName("cjbeall and ejpitera: testing that request type is distances")
    public void testType() {
        Places places = new Places();
        distance = new DistancesRequest(1L, places);
        distance.buildResponse();
        String type = distance.getRequestType();
        assertEquals("distances", type);
    }

    @Test
    @DisplayName("ejpitera: Test distance total")
    public void testDistances() {
        Places ethanPlaces = new Places();
        ethanPlaces.add(new Place("38.84", "-104.859"));
        ethanPlaces.add(new Place("-22.739", "-47.629"));
        ethanPlaces.add(new Place("35.458", "138.76"));
        ethanPlaces.add(new Place("-33.9", "151.165"));
        distance = new DistancesRequest(5225616L, ethanPlaces);
        distance.buildResponse();
        assertEquals(distance.getDistances().total(), 39985224);
    }

    @BeforeEach
    public void setup() {
        Places places = new Places();
        places.add(new Place("0", "0"));
        places.add(new Place("0", "90"));
        distance = new DistancesRequest(6371L, places);
    }

    @Test
    @DisplayName("evanloy: Test Earth radius")
    public void testEarthRadius() {
        long radius = distance.getEarthRadius();
        assertEquals(6371L, radius);
    }   
    @Test
    @DisplayName("cjbeall: Test my total distances with two points in each quadrant")
    public void testTwoPointsInEachQuadrant() {
        Places chrisPlaces = new Places();
        chrisPlaces.add(new Place("-31.91", "124.45"));
        chrisPlaces.add(new Place("-26.91", "147.30"));
        chrisPlaces.add(new Place("31.49", "111.09"));
        chrisPlaces.add(new Place("59.26", "93.87"));
        chrisPlaces.add(new Place("61.01", "-98.44"));
        chrisPlaces.add(new Place("56.07", "-117.42"));
        chrisPlaces.add(new Place("-6.50", "-78.48"));
        chrisPlaces.add(new Place("-24.87", "-48.52"));
        distance = new DistancesRequest(26713603L, chrisPlaces);
        distance.buildResponse();
        assertEquals(distance.getDistances().total(), 193921231);
    }
}