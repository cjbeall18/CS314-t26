package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);

    private Places placesList;
    private String match;
    private String [] type;
    private String [] where;
    private Integer limit;
    private Integer found;


    @Override
    public void buildResponse() {
        placesList = buildPlacesList();
        log.trace("buildResponse -> {}", this);
    }
    private Places buildPlacesList() {
        Places placesList = new Places();

        //function buildPlacesList is incomplete. Complete by matching variable "match"
        // to results found in database
        found = (Integer)placesList.size();
        return placesList;
    }

    public FindRequest (String match, Integer limit) {
        super();
        this.requestType = "find";
        this.match = match;
        this.limit = limit;
    }
}
