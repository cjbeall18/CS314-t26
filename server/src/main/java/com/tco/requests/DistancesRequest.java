package com.tco.requests;

import com.tco.requests.Distances;
import com.tco.requests.Places;
import static com.tco.misc.DistanceCalculator.calculator;
import com.tco.misc.GeographicCoordinate;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistancesRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private Places places;
    private long earthRadius;
    private Distances distances;

    @Override
    public void buildResponse() {
        distances = buildDistanceList();
        log.trace("buildResponse -> {}", this);
    }

    private Distances buildDistanceList() {

        Distances distances = new Distances();

        Place to;
        for(int i = 0; i < places.size(); i++) {
            Place from = places.get(i);
            try {
                to = places.get(i+1);
            } catch (Exception e) {
                to = places.get(0);
            }
            distances.add(calculator(from, to, earthRadius));
        }

        return distances;
    }

    public DistancesRequest(long earthRadius, Places places) {
        super();
        this.requestType = "distances";
        this.earthRadius = earthRadius;
        this.places = places;
    }

    public Places getPlaces() {
        return places;
    }

    public Distances getDistances(){
        return distances;
    }

    public long getEarthRadius() {
        return earthRadius;
    }
}
