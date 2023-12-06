package com.tco.requests;

import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Tour {
    double[][] distances; 
    Places places;
    Place[] globalPlaces;

    public Places shorter (Places places, double earthRadius, double response) {
        boolean isNN;
        boolean isTwoOpt = false;
        Place[] places_arr;
     

        if (places.size() <= 250) {
            isTwoOpt = true; 
            places_arr = places.toArray(new Place[places.size()+1]);
            places_arr[places_arr.length-1] = places_arr[0];
        }
        else {
            isNN = true;
            places_arr = places.toArray(new Place[places.size()]);
        }

        Place[] bestTour = places_arr;
        long bestDistance = calculateTourDistance(places_arr, earthRadius);
        long startTime = System.currentTimeMillis();

        distances = new double[places_arr.length][places_arr.length];
        for (int i = 0; i < places_arr.length; i++) {
            for (int j = 0; j < places_arr.length; j++) {
                if (timeCheck(startTime, response)) {break;}
                distances[i][j] = DistanceCalculator.calculator(places_arr[i], places_arr[j], earthRadius);
            }
        }
        for (int i = 0; i < places_arr.length; i++) {
            if (timeCheck(startTime, response)) {break;}
            Place[] currentTour = construct(i, places_arr, earthRadius);
            if (isTwoOpt) {
                Tour twoOptTour = new TwoOpt();
                twoOptTour.globalPlaces = currentTour;
                twoOptTour.improve();
                //currentTour = twoOptTour.globalPlaces;
            }
            long currentDistance = calculateTourDistance(currentTour, earthRadius);

            if (currentDistance < bestDistance) {
                bestDistance = currentDistance;
                bestTour = currentTour;
            }
        }
        Places bestTourList = new Places(bestTour);
        if (isTwoOpt) {
            bestTourList.remove(bestTourList.indexOf(places.get(0)));
        }
        if (!places.isEmpty()) {
            if (places.get(0) != bestTourList.get(0)) {
                int firstPlaceIndex = bestTourList.indexOf(places.get(0));
                firstPlaceIndex *= -1;
                Collections.rotate(bestTourList, firstPlaceIndex);
            }
        }
        return bestTourList;
    }

    private boolean timeCheck(long startTime, double endTime) {
        long currTime = System.currentTimeMillis();
        long totTime = currTime - startTime;

        if (endTime * 1000 <= totTime) {
            return true;
        } else {
            return false;
        }
    }
    
    private Place[] construct (int startIndex, Place[] places_arr, double earthRadius) {
        Place[] tour = new Place[places_arr.length];
        boolean[] visited = new boolean[places_arr.length];
        Arrays.fill(visited, false);
        tour[0]=places_arr[startIndex];

        int currentCityIndex = startIndex;
        int increment = 0;
        for(int j = 0; j < places_arr.length; j++) {
            int nearestCityIndex = -1;
            double nearestDistance = Double.MAX_VALUE;

            for (int i = 0; i < places_arr.length; i++) {
                if (!visited[i]) {
                    double distance = distances[currentCityIndex][i];
                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearestCityIndex = i;
                    }
                }
            }
            if (nearestCityIndex == -1) {
                break; 
            }
            visited[nearestCityIndex] = true;
            tour[j] = places_arr[nearestCityIndex];
            currentCityIndex = nearestCityIndex;
        }
        return tour;
    }

    public long calculateTourDistance(Place[] tour, double earthRadius) {
        long totalDistance = 0;
        for (int i = 0; i < tour.length; i++) {
            Place from = tour[i];
            Place to = (i + 1 < tour.length - 1) ? tour[i + 1] : tour[0]; // loop back to the start
            totalDistance += DistanceCalculator.calculator(from, to, earthRadius);
        }

        return totalDistance;
    }

    abstract void improve();
}
