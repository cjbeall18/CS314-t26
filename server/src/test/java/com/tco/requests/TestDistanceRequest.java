package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestDistanceRequest {
    
    private DistanceRequest distance;

    @BeforeEach
    public void createDistanceForTestCases() {
        distance = new DistanceRequest();
        distance.buildResponse();
    }

    @Test
    @DisplayName("cjbeall: testing that request type is distances")
    public void testType() {
        String type = distance.getRequestType();
        assertEquals("distances", type);
    }
}

