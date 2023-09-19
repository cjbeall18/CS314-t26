package com.tco.requests;

import com.tco.requests.Distances;
import com.tco.requests.Places;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistanceRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);

    private Places places;
    private long earthRadius;
    private Distances distances;

    @Override
    public void buildResponse() {
        
        // FILL WITH IMPLEMENTATION

    }

    public void DistanceRequest() {
        this.requestType = "distances";
    }

    public Places getPlaces() {
        return places;
    }
}
