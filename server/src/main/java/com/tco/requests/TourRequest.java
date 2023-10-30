package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);



    @Override
    public void buildResponse() {

    }

    public TourRequest () {
        this.requestType = "Tour";
    }
}