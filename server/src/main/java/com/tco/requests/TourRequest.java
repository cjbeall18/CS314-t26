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

    @Override
    public void buildResponse() {
        if (this.response > 30.0) {
            this.response = 30.0;
        }
        optimizeTour();
        log.trace("buildResponse -> {}", this);
    }

    public TourRequest () {
        this(1,0.0,new Places());
    }
    
    public TourRequest(double earthRadius, double response, Places places) {
        super();
        this.requestType = "tour";
        this.earthRadius = 1;
        this.response = response;
        this.places = places;
    }

    private void optimizeTour() {
        if (this.response <= 0.0) {
            this.places = this.places;
        } else if (this.places.size() > 0 && this.places.size() >= 100) {
            Tour tour = new OneOpt();
            Places shorterTour = tour.shorter(this.places, this.earthRadius, this.response * 0.75);
            this.places = shorterTour;
        } //else if (this.places.size() > 0){
        //     Tour tour = new TwoOpt();
        //     tour.places = new Places(this.places);
        //     tour.improve();
        //     this.places = new Places(tour.places);
        // }
        
    }

    public double getEarthRadius() { return this.earthRadius; }
    public double getResponse() { return this.response; }
    public Places getPlaces() { return this.places; }
}
