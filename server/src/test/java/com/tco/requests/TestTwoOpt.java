package com.tco.requests;

import com.tco.requests.TwoOpt;
import com.tco.requests.Tour;
import com.tco.requests.TourRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;

public class TestTwoOpt {
    Tour tour;

    @Test
    @DisplayName("cjbeall")
    public void testPlacesAreDifferent() {
        tour = new TwoOpt();
        Places unoptimizedPlaces = new Places();
        unoptimizedPlaces.add(new Place("0", "0")); //A
        unoptimizedPlaces.add(new Place("0", "2")); //B
        unoptimizedPlaces.add(new Place("1", "0")); //C
        unoptimizedPlaces.add(new Place("5", "5")); //D
    
        tour.places = unoptimizedPlaces;

        Places checkPlaces = new Places();
        checkPlaces.add(new Place("0", "0")); //A
        checkPlaces.add(new Place("0", "2")); //B
        checkPlaces.add(new Place("1", "0")); //C
        checkPlaces.add(new Place("5", "5")); //D
        //reorder should be ACDB

        tour.improve();
        assertEquals(tour.places.get(0),checkPlaces.get(0));
        assertEquals(tour.places.get(1), checkPlaces.get(2));
        assertEquals(tour.places.get(2), checkPlaces.get(3));
        assertEquals(tour.places.get(3), checkPlaces.get(1));
    }
}
