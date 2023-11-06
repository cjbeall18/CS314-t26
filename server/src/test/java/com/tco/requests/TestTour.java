package com.tco.requests;

import com.tco.requests.OneOpt;
import com.tco.requests.Tour;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestTour {
    Tour tour;
    
    @Test
    @DisplayName("tamo: test no improve using 1Opt")
    public void test1Opt() {
        tour = new OneOpt();
        Places places = new Places();
        places.add(new Place("38.84", "-104.859"));
        places.add(new Place("-22.739", "-47.629"));
        places.add(new Place("35.458", "138.76"));
        places.add(new Place("-33.9", "151.165"));

        assertEquals(tour.calculateTourDistance(places, 5225616L), 39985224);

        // Now check that shorter is shorter than regular distance
        Places shorterTour = tour.shorter(places, 5225616L);
        long shorterTourDistance = tour.calculateTourDistance(shorterTour, 5225616L);
        assertTrue(shorterTourDistance < 39985224);
        
    }
}