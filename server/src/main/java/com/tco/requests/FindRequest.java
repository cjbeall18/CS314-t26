package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);



    @Override
    public void buildResponse() {

    }

    public FindRequest () {
        this.requestType = "Find";
    }
}
