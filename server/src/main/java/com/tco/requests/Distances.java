package com.tco.requests;
import java.util.ArrayList;

public class Distances extends ArrayList<Integer> {
    
    public long total() {
        
        long totalDistance = 0;

        for (Integer distance : this) {
                totalDistance += distance;
        }
        
        return totalDistance;
    }    
}
