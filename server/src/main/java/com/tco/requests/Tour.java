package com.tco.requests;

import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Tour {
    double[][] distances; 
    int[] tour;
    Places places;

    public Tour () {

    }

    public void shorter () {

    }
    public void construct () {
        
    }

    public long calculateTourDistance(Places tour, double earthRadius) {
        long totalDistance = 0;

        for (int i = 0; i < tour.size(); i++) {
            Place from = tour.get(i);
            Place to = (i + 1 < tour.size()) ? tour.get(i + 1) : tour.get(0); // loop back to the start
            totalDistance += DistanceCalculator.calculator(from, to, earthRadius);
        }

        return totalDistance;
    }

    abstract void improve ();
}
