package com.tco.requests;

import com.tco.Distances;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistanceRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);

    private long earthRadius;
    private List<String> places;
    private List<int> distances;


    @Override
    public void buildResponse() {
        
        // FILL WITH NECESSARY COMPONENTS

    }

    public DistanceRequest() {
        this.requestType = "distances";
    }
}
