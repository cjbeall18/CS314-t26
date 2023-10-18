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
}
