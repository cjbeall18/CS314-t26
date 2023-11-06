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

    // Method to find the shortest tour using the nearest neighbor heuristic
    public Places shorter(Places places, double earthRadius) {
        Places bestTour = null;
        long bestDistance = Long.MAX_VALUE;

        // Calculate the distance between all pairs of cities once, to avoid recalculating
        distances = new double[places.size()][places.size()];
        for (int i = 0; i < places.size(); i++) {
            for (int j = 0; j < places.size(); j++) {
                distances[i][j] = DistanceCalculator.calculator(places.get(i), places.get(j), earthRadius);
            }
        }

        // Try constructing a tour starting from each city
        for (int i = 0; i < places.size(); i++) {
            Places currentTour = construct(i, places, earthRadius);
            long currentDistance = calculateTourDistance(currentTour, earthRadius);

            if (currentDistance < bestDistance) {
                bestDistance = currentDistance;
                bestTour = currentTour;
            }
        }

        // Need if block to rotate bestTour to make first place same as first place in given places arraylist from user
        if (places.get(0) != bestTour.get(0)) {
            int firstPlaceIndex = bestTour.indexOf(places.get(0));
            firstPlaceIndex *= -1;
            Collections.rotate(bestTour, firstPlaceIndex);
        }
        return bestTour;
    }
    
    // Helper method to construct a tour starting from a given city index
    private Places construct(int startIndex, Places places, double earthRadius) {
        Places tour = new Places();
        boolean[] visited = new boolean[places.size()];
        Arrays.fill(visited, false);
        visited[startIndex] = true;
        tour.add(places.get(startIndex));

        int currentCityIndex = startIndex;
        while (tour.size() < places.size()) {
            int nearestCityIndex = -1;
            double nearestDistance = Double.MAX_VALUE;

            // Find the nearest unvisited city
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
                break; // This shouldn't happen if the logic is correct
            }

            // Mark the nearest city as visited and add it to the tour
            visited[nearestCityIndex] = true;
            tour.add(places.get(nearestCityIndex));
            currentCityIndex = nearestCityIndex;
        }

        return tour;
    }
    
    // Helper method to calculate the total distance of a tour
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
