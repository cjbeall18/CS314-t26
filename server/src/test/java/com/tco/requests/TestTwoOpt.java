package com.tco.requests;

import com.tco.requests.TwoOpt;
import com.tco.requests.Tour;
import com.tco.requests.TourRequest;
import com.tco.requests.TestOneOpt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;
import java.util.Arrays;

public class TestTwoOpt {
    Tour tour;

    @Test
    @DisplayName("clayroby: test that TwoOpt optimizes better than NN")
    public void testTwoOptBetterOneOpt() {
        Places places = new Places();
        long earthRadius = 6371;
        Tour tourTwo = new OneOpt();
        double response = 10.0;

        places.add(new Place("39.174930", "-106.955120"));
        places.add(new Place("39.96180", "-105.50891"));
        places.add(new Place("38.265665", "-104.610022"));
        places.add(new Place("40.062586", "-105.204699"));
        places.add(new Place("39.740889", "-104.830027"));

        Place[] placeArray = places.toArray(new Place[places.size()]);
        long originalTourDistance = tourTwo.calculateTourDistance(placeArray, earthRadius);

        Places bestTour = tourTwo.shorter(places, earthRadius, response);
        Place[] bestTourArr = bestTour.toArray(new Place[bestTour.size()]);

        long tourDistance = tourTwo.calculateTourDistance(bestTourArr, earthRadius);

        assertTrue(originalTourDistance > tourDistance);
    }

    @Test
    @DisplayName("cjbeall: test that places are different")
    public void testPlacesAreDifferent() {
        tour = new TwoOpt();
        tour.globalPlaces = new Place[]{
            new Place("14.34", "-37.62"),
            new Place("-8.86", "-1.76"),
            new Place("-10.76", "-49.04"),
            new Place("19.88", "10.72"), 
            new Place("-18.33", "-27.77")
        };

        Place[] checkPlaces = new Place[]{
            new Place("14.34", "-37.62"),
            new Place("-8.86", "-1.76"), 
            new Place("-10.76", "-49.04"),
            new Place("19.88", "10.72"),
            new Place("-18.33", "-27.77")
        };
        tour.improve();
        
        assertEquals(tour.globalPlaces[0],checkPlaces[0]); 
        assertEquals(tour.globalPlaces[1],checkPlaces[3]);
        assertEquals(tour.globalPlaces[2],checkPlaces[1]);
        assertEquals(tour.globalPlaces[3],checkPlaces[2]);
        assertEquals(tour.globalPlaces[4],checkPlaces[4]);
    }

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
