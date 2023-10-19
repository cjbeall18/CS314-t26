package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestFindRequest {
    private FindRequest findObject;

    @Test
    @DisplayName("cjbeall: testing that request type is Find")
    public void testType() {
        findObject = new FindRequest("Denver", 20);
        findObject.buildResponse();
        String type = findObject.getRequestType();
        assertEquals("find", type);
    }

    @Test
    @DisplayName("tamo: test FindRequest buildQuery")
    public void testBuildQuery() {
        findObject = new FindRequest("Texas", 10);
        findObject.buildResponse();
        assertEquals(10, findObject.getPlacesSize());
    }

    @Test
    @DisplayName("clayroby: testing FindRequest Ryp")
    public void testRYPQuery() {
        findObject = new FindRequest("ryp", 0);
        findObject.buildResponse();
        assertEquals(4, findObject.getPlacesSize());
    }

    @Test
    @DisplayName("ejpitera: testing default limit")
    public void testDefaultLimit() {
        findObject = new FindRequest("C", 0);
        findObject.buildResponse();
        assertEquals(100, findObject.getPlacesSize());
    }

    @Test
    @DisplayName("evanloy: response with no string")
    public void testNoString() {
        findObject = new FindRequest("", 1);
        findObject.buildResponse();
        assertEquals(1, findObject.getPlacesSize());
    }
}
