package com.tco.requests;

import com.tco.requests.Places;
//import com.tco.requests.Tour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);

    private double earthRadius;
    private double response;
    private Places places;
    //public Tour tour;

    @Override
    public void buildResponse() {
        optimizeTour();
        log.trace("buildResponse -> {}", this);
    }

    public TourRequest () {
        this(1,1,new Places());
    }
    
    public TourRequest(double earthRadius, double response, Places places) {
        super();
        this.requestType = "tour";
        this.earthRadius = 1;
        this.response = response;
        this.places = places;
    }

    private void optimizeTour() {
        Tour tour = new OneOpt();
        Places shorterTour = tour.shorter(this.places, this.earthRadius, this.response * 0.75);
        this.places = shorterTour;
        // if (this.response <= 0.0) {
        //     //Do Nothing
        // } 
        
    }
}
