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
        System.out.println("global places: " + Arrays.toString(this.globalPlaces));
        boolean improvement = true;
        distances_matrix = this.distances;
        System.out.println("distances: " + distances);
        //System.out.println("Distances matrix: " + distances_matrix);

        for (int x = 0; x< distances_matrix.length; x++)
        {
            for (int y = 0; y< distances_matrix.length; y++)
            {
                System.out.println("Matrix: " + distances_matrix[x][y]);
            }
        }

        while (improvement) {
            System.out.println("In Improve");
            improvement = false;
            for (int i=0; i<=routeArray.length-3; i++) {
                for (int k=i+2; k<routeArray.length-1; k++) {
                    System.out.println("T/F improves: " + twoOptImproves(/*routeArray, */i, k));
                    if (twoOptImproves(/*routeArray, */i, k)) {
                        for (int l =  0; l < routeArray.length; l++){
                            System.out.println("RouteArray at l: " + l + " " +  routeArray[l]);
                        }
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

    private boolean twoOptImproves(/*Place[] routeArray, */int i, int k) {
        return (distances_matrix[i][k] + distances_matrix[i+1][k+1]) < (distances_matrix[i][i+1] + distances_matrix[k][k+1]);
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