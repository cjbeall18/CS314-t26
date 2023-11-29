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
        Places route = this.places;
        Places OGPlaces = new Places(this.places);
        route.add(route.get(0));
        Place[] routeArray = route.toArray(new Place[route.size()]);
        System.out.println("routeArray: " + routeArray[0]);
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i=0; i<=routeArray.length-3; i++) {
                for (int k=i+2; k<routeArray.length-1; k++) {
                    if (twoOptImproves(routeArray, i, k)) {
                        routeArray = twoOptReverse(routeArray, i+1, k);
                        improvement = true;
                    }
                }
            }
        }
        route = new Places(routeArray);
        rotateStart(route, OGPlaces);
        System.out.println("EXITING TWO OPT IMPROVE distance:" + calculateTourDistance(route, 3959.0));
    }

    public static void rotateStart (Places route, Places places) {
        route.remove(route.size()-1);
        if (route.get(0) != places.get(0)) {
            int firstPlaceIndex = route.indexOf(places.get(0));
            firstPlaceIndex *= -1;
            Collections.rotate(route, firstPlaceIndex);
        }
    }

    private boolean twoOptImproves(Place[] routeArray, int i, int k) {
        return DistanceCalculator.calculator(routeArray[i], routeArray[k], 3695.0) + 
        DistanceCalculator.calculator(routeArray[i+1], routeArray[k+1], 3695.0) < 
        DistanceCalculator.calculator(routeArray[i], routeArray[i+1], 3695.0) + 
        DistanceCalculator.calculator(routeArray[k], routeArray[k+1], 3695.0);
    }

    public static Place[] twoOptReverse(Place[] routeArray, int i1, int k) {
        while (i1 < k) {
            Place tmp = routeArray[i1];
            routeArray[i1] = routeArray[k];
            routeArray[k] = tmp;

            i1++;
            k--;
        }
        return routeArray;
    }

}