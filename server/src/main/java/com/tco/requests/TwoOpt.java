package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

public class TwoOpt extends Tour {
    void twoOpt() {}

    @Override
    void improve() {
        System.out.println("inside improve");
        Places route = this.places;
        Places OGPlaces = new Places(this.places);
        route.add(route.get(0));
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i=0; i<=route.size()-3; i++) {
                for (int k=i+2; k<route.size()-1; k++) {
                    if (twoOptImproves(route, i, k)) {
                        twoOptReverse(route, i+1, k);
                        improvement = true;
                    }
                }
            }
        }
        rotateStart(route, OGPlaces);
    }

    public static void rotateStart (Places route, Places places) {
        route.remove(route.size()-1);
        if (route.get(0) != places.get(0)) {
            int firstPlaceIndex = route.indexOf(places.get(0));
            firstPlaceIndex *= -1;
            Collections.rotate(route, firstPlaceIndex);
        }
    }

    private boolean twoOptImproves(Places route, int i, int k) {
        return DistanceCalculator.calculator(route.get(i), route.get(k), 3695.0) + 
        DistanceCalculator.calculator(route.get(i+1), route.get(k+1), 3695.0) < 
        DistanceCalculator.calculator(route.get(i), route.get(i+1), 3695.0) + 
        DistanceCalculator.calculator(route.get(k), route.get(k+1), 3695.0);
    }

    public static void twoOptReverse(Places route, int i1, int k) {
        while (i1 < k) {
            Collections.swap(route, i1, k);
            i1++;
            k--;
        }
    }

}