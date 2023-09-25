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
}

