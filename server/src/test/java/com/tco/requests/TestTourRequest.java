package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTourRequest {
    private TourRequest tourReq;

    @BeforeEach
    public void createTourRequestForTestCases() {
        tourReq = new TourRequest();
        tourReq.buildResponse();
    }

    @Test
    @DisplayName("tamo: Request type is \"tour\"")
    public void testTour() {
        String type = tourReq.getRequestType();
        assertEquals("tour", type);
    }

    @Test
    @DisplayName("tamo: Test default ctor values")
    public void testDefaultTourRequestVariables() {
        double earthRadius = tourReq.getEarthRadius();
        double response = tourReq.getResponse();
        int placesSize = tourReq.getPlaces().size();
        assertEquals(earthRadius, 1);
        assertEquals(response, 1);
        assertEquals(placesSize, 0);
    }
}