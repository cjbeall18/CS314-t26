package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

public class TwoOpt extends Tour {
    double[][] distances_matrix; 
    void twoOpt() {}

    @Override
    void improve() {
        Place firstPlace = this.globalPlaces[0];
        Place[] routeArray = this.globalPlaces;
        boolean improvement = true;
        distances_matrix = this.distances;
        System.out.println("DISTANCES: " + distances_matrix);

        while (improvement) {
            System.out.println("While loop condition: " + improvement);
            improvement = false;
            for (int i=0; i<=routeArray.length-3; i++) {
                System.out.println("Outer for loop condition: " + (i<=routeArray.length-3));
                System.out.println("routeArray.length: " + routeArray.length);
                System.out.println("i: " + i);
                for (int k=i+2; k<routeArray.length-1; k++) {
                    System.out.println("Inner for loop condition: " + (k<routeArray.length-1));
                    System.out.println("k: " + k);
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
        //System.out.println("Distances less than " + distances_matrix[i][k] + distances_matrix[i+1][k+1]);
        //System.out.println("Distances greater than " + distances_matrix[i][i+1] + distances_matrix[k][k+1]);
        System.out.println("If Statement Condition:  " + (distances_matrix[i][k] + distances_matrix[i+1][k+1] < distances_matrix[i][i+1] + distances_matrix[k][k+1]));
        return distances_matrix[i][k] + distances_matrix[i+1][k+1] < distances_matrix[i][i+1] + distances_matrix[k][k+1];

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