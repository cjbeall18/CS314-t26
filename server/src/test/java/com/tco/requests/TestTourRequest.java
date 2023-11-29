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
        assertEquals(response, 0.0);
        assertEquals(placesSize, 0);
    }

    @Test
    @DisplayName("ejpitera: Test response value")
    public void testTourRequestResponse() {
        tourReq = new TourRequest(1.0, 50.0, new Places());
        double response = tourReq.getResponse();
        assertEquals(response, 50.0);
    }

    @Test
    @DisplayName("evanloy: Test response value below threshold")
    public void testResponseValueBelowThreshold() {
        double testResponse = 25.0;
        tourReq = new TourRequest(1.0, testResponse, new Places());
        tourReq.buildResponse();
        assertEquals(tourReq.getResponse(), testResponse);
    }

    @Test
    @DisplayName("evanloy: Test optimizeTour with zero or negative response")
    public void testOptimizeTourWithZeroOrNegativeResponse() {
        tourReq = new TourRequest(1.0, -10.0, new Places());
        tourReq.buildResponse();
        assertEquals(tourReq.getPlaces().size(), 0);
    }
    
    @Test
    @DisplayName("evanloy: Test optimizeTour with positive response and empty places")
    public void testOptimizeTourWithPositiveResponseAndEmptyPlaces() {
        tourReq = new TourRequest(1.0, 20.0, new Places());
        tourReq.buildResponse();
        assertEquals(tourReq.getPlaces().size(), 0);
    }
}