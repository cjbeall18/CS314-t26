package com.tco.requests;

import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Tour {
    double[][] distances; 
    Places places;

    public Places shorter (Places places, double earthRadius, double response) {
        System.out.println("inside shorter");
        Places bestTour = places;
        long bestDistance = calculateTourDistance(places, earthRadius);
        long startTime = System.currentTimeMillis();

        distances = new double[places.size()][places.size()];
        for (int i = 0; i < places.size(); i++) {
            for (int j = 0; j < places.size(); j++) {
                if (timeCheck(startTime, response)) {break;}
                distances[i][j] = DistanceCalculator.calculator(places.get(i), places.get(j), earthRadius);
            }
        }
        for (int i = 0; i < places.size(); i++) {
            if (timeCheck(startTime, response)) {break;}
            Places currentTour = construct(i, places, earthRadius);
            Tour twoOptTour = new TwoOpt();
            twoOptTour.places = currentTour;
            twoOptTour.improve();
            currentTour = twoOptTour.places;
            long currentDistance = calculateTourDistance(currentTour, earthRadius);

            System.out.println("count: " + i + " || currentDistance: " + currentDistance);

            if (currentDistance < bestDistance) {
                bestDistance = currentDistance;
                bestTour = currentTour;
            }
        }
        if (!places.isEmpty()) {
            if (places.get(0) != bestTour.get(0)) {
                int firstPlaceIndex = bestTour.indexOf(places.get(0));
                firstPlaceIndex *= -1;
                Collections.rotate(bestTour, firstPlaceIndex);
            }
        }
        return bestTour;
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
    
    private Places construct (int startIndex, Places places, double earthRadius) {
        Places tour = new Places();
        boolean[] visited = new boolean[places.size()];
        Arrays.fill(visited, false);
        visited[startIndex] = true;
        tour.add(places.get(startIndex));

        int currentCityIndex = startIndex;
        while (tour.size() < places.size()) {
            int nearestCityIndex = -1;
            double nearestDistance = Double.MAX_VALUE;

            for (int i = 0; i < places.size(); i++) {
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
            tour.add(places.get(nearestCityIndex));
            currentCityIndex = nearestCityIndex;
        }
        return tour;
    }

    public long calculateTourDistance(Places tour, double earthRadius) {
        long totalDistance = 0;

        for (int i = 0; i < tour.size(); i++) {
            Place from = tour.get(i);
            Place to = (i + 1 < tour.size()) ? tour.get(i + 1) : tour.get(0); // loop back to the start
            totalDistance += DistanceCalculator.calculator(from, to, earthRadius);
        }

        return totalDistance;
    }

    abstract void improve();
}
