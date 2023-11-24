package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;

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

    @Test
    @DisplayName("ejpitera: Test response value")
    public void testTourRequestResponse() {
        tourReq = new TourRequest(1.0, 50.0, new Places());
        double response = tourReq.getResponse();
        assertEquals(response, 50.0);
    }
}