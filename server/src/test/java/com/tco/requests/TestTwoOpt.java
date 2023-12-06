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

//     @Test
//     @DisplayName("cjbeall")
//     public void testPlacesAreDifferent() {
//         tour = new TwoOpt();
//         Places unoptimizedPlaces = new Places();
//         unoptimizedPlaces.add(new Place("0", "0")); //A
//         unoptimizedPlaces.add(new Place("0", "2")); //B
//         unoptimizedPlaces.add(new Place("1", "0")); //C
//         unoptimizedPlaces.add(new Place("5", "5")); //D
    
//         tour.places = unoptimizedPlaces;

//         Places checkPlaces = new Places();
//         checkPlaces.add(new Place("0", "0")); //A
//         checkPlaces.add(new Place("0", "2")); //B
//         checkPlaces.add(new Place("1", "0")); //C
//         checkPlaces.add(new Place("5", "5")); //D
//         //reorder should be ACDB

//         tour.improve();
//         assertEquals(tour.places.get(0),checkPlaces.get(0));
//         assertEquals(tour.places.get(1), checkPlaces.get(2));
//         assertEquals(tour.places.get(2), checkPlaces.get(3));
//         assertEquals(tour.places.get(3), checkPlaces.get(1));
//     }

    @Test
    @DisplayName("evanloy: Test for No Improvement Needed")
    public void testNoImprovementNeeded() {
        tour = new TwoOpt();
        tour.globalPlaces = new Place[]{new Place("0", "0"), new Place("1", "0"), new Place("2", "0")};

        Place[] checkPlaces = new Place[]{new Place("0", "0"), new Place("1", "0"), new Place("2", "0")};

        tour.improve();

        for (int i = 0; i < tour.globalPlaces.length; i++) {
            assertEquals(tour.globalPlaces[i], checkPlaces[i]);
        }
    }

    
    @Test
    @DisplayName("evanloy: Test with Two Places")
    public void testWithTwoPlaces() {
        tour = new TwoOpt();
        tour.globalPlaces = new Place[]{new Place("0", "0"), new Place("1", "1")};
    
        Place[] checkPlaces = new Place[]{new Place("0", "0"), new Place("1", "1")};
    
        tour.improve();
    
        assertEquals(2, tour.globalPlaces.length);
        assertEquals(tour.globalPlaces[0], checkPlaces[0]);
        assertEquals(tour.globalPlaces[1], checkPlaces[1]);
    }
    
    
    @Test
    @DisplayName("evanloy: Test that starting point is always the same")
    public void testSpecificRouteRotation() {
        tour = new TwoOpt();
        tour.globalPlaces = new Place[]{new Place("1", "1"), new Place("100", "100"), new Place("99", "99")};
    
        tour.improve();
    
        Place expectedFirstPlace = new Place("1", "1");
    
        assertEquals(expectedFirstPlace, tour.globalPlaces[0]);
    }
    
}
