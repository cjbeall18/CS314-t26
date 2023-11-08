package com.tco.requests;

import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Tour {
    double[][] distances; 
    int[] tour;
    Places places;

    public void shorter () {

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

    abstract void improve ();
}
