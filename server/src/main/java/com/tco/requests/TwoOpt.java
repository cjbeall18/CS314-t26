package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

public class TwoOpt extends Tour {
    double[][] distances; 
    void twoOpt() {}

    @Override
    void improve() {
        Place firstPlace = this.globalPlaces[0];
        Place[] routeArray = this.globalPlaces;
        boolean improvement = true;

        while (improvement) {
            improvement = false;
            for (int i=0; i<=routeArray.length-3; i++) {
                for (int k=i+2; k<routeArray.length-1; k++) {
                    if (twoOptImproves(/*routeArray,*/ i, k)) {
                        routeArray = twoOptReverse(routeArray, i+1, k);
                        improvement = true;
                    }
                }
            }
        }
    }

    public static void rotateStart (Places route, Place firstPlace) {
        if (route.get(0) != firstPlace) {
            int firstPlaceIndex = route.indexOf(firstPlace);
            firstPlaceIndex *= -1;
            Collections.rotate(route, firstPlaceIndex);
        }
    }

    private boolean twoOptImproves(/*Place[] routeArray,*/ int i, int k) {
        return distances[i][k] + distances[i+1][k+1] < distances[i][i+1] + distances[k][k+1];
        // return DistanceCalculator.calculator(routeArray[i], routeArray[k], 3695.0) + 
        // DistanceCalculator.calculator(routeArray[i+1], routeArray[k+1], 3695.0) < 
        // DistanceCalculator.calculator(routeArray[i], routeArray[i+1], 3695.0) + 
        // DistanceCalculator.calculator(routeArray[k], routeArray[k+1], 3695.0);
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