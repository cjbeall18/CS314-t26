package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.requests.Database;

public class FindRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);

    private Places placesList;
    private String match;
    private String [] type;
    private String [] where;
    private Integer limit;
    private Integer found;

    public FindRequest (String match, Integer limit) {
        super();
        this.requestType = "find";
        this.match = match;
        this.limit = limit;
    }

    @Override
    public void buildResponse() {
        // placesList = buildPlacesList();
        buildQuery();
        log.trace("buildResponse -> {}", this);
    }

    private void buildQuery() {
        Database db = new Database();

        try {
            this.placesList = db.places(this.match, this.limit);
            this.found = db.found(this.match);
        } catch (Exception e) {
            System.err.println("Caught Error from Database: " + e);
        }
    }

    public Integer getPlacesListSize() { return this.placesList.size(); }
}
