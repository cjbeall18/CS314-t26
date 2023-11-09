package com.tco.requests;

import com.tco.requests.OneOpt;
import com.tco.requests.Tour;
import com.tco.requests.TourRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;


public class TestOneOpt {
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

        tour.improve();
        assertEquals(tour.calculateTourDistance(places, 5225616L), 39985224);
    }
    
    @Test
    @DisplayName("tamo: test shorter tour method is shorter than base tour distance")
    public void testShorter() {
        tour = new OneOpt();
        Places places = new Places();
        places.add(new Place("38.84", "-104.859"));
        places.add(new Place("-22.739", "-47.629"));
        places.add(new Place("35.458", "138.76"));
        places.add(new Place("-33.9", "151.165"));

        // Now check that shorter is shorter than regular distance
        long earthRadius =  5225616L;
        Places shorterTour = tour.shorter(places, earthRadius, 1.0);
        long shorterTourDistance = tour.calculateTourDistance(shorterTour, earthRadius);
        long regTourDistance = tour.calculateTourDistance(places, earthRadius);

        assertTrue(shorterTourDistance < regTourDistance);
    }

    @Test
    @DisplayName("evanloy: Test OneOpt inherits calculateTourDistance from Tour with expected distance")
    public void testInheritanceOfCalculateTourDistanceWithRoundedExpectedDistance() {
        tour = new OneOpt();
        Places places = new Places();
        places.add(new Place("1", "0"));
        places.add(new Place("2", "0")); 
    
        double earthRadius = 6371;
        long expectedDistance = Math.round(2 * Math.toRadians(1) * earthRadius);
        long calculatedDistance = tour.calculateTourDistance(places, earthRadius);
    
        assertEquals(expectedDistance, calculatedDistance);
    }
    
    @Test
    @DisplayName("evanloy: Test OneOpt improve method is callable")
    public void testOneOptImproveIsCallable() {
        tour = new OneOpt();
        tour.improve();
        assertTrue(true);
    }

    @Test
    @DisplayName("evanloy: test Tour shorter method rotates to the original starting place")
    public void testTourRotationLogic() {
        tour = new OneOpt();
        Places places = new Places();
        places.add(new Place("38.84", "-104.859"));
        places.add(new Place("-22.739", "-47.629"));
        places.add(new Place("35.458", "138.76"));
        places.add(new Place("-33.9", "151.165"));
    
        long earthRadius = 6371;
        Places bestTour = tour.shorter(places, earthRadius, 1.0);
    
        assertEquals(places.get(0), bestTour.get(0));
    }

    @Test
    @DisplayName("clayroby: test Tour timeout. Expect control group time to be greater than test group time")
    public void testTourTimeout() {
        Places places = new Places();
        long earthRadius = 6371;
        tour = new OneOpt();
        double response = 1.0;
        boolean result = false;
        Random rand = new Random();

        places = randTourBuilder(rand, 1000, places);
       
        // run test with short response. usually take 1.4 seconds to run
        long testStartTime = System.currentTimeMillis();
        Places bestTour = tour.shorter(places, earthRadius, response);
        long testEndTime = System.currentTimeMillis();
        long testTotTime = testEndTime - testStartTime;

        // run a control test on same places, but with no limit to response
        long controlStartTime = System.currentTimeMillis();
        Places controlTour = tour.shorter(places, earthRadius, 10.0);
        long controlEndTime = System.currentTimeMillis();
        long controlTotTime = controlEndTime - controlStartTime;

        result = testTotTime < controlTotTime;
        
        assertEquals(true, result);
    }

    private Places randTourBuilder(Random rand, int num_places, Places places) {
        for (int i = 0; i < num_places; i++) {
            String lat = String.valueOf(rand.nextInt(90) + 1);
            String lon = String.valueOf(rand.nextInt(180) + 1);
            places.add(new Place(lat, lon));
        }
        return places;
    }
}