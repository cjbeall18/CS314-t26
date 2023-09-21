package com.tco.requests;
import java.util.ArrayList;

public class Distances extends ArrayList<Long> {
    
    public long total() {
        
        long totalDistance = 0;

        for (Long distance : this) {
                totalDistance += distance;
        }
        
        return totalDistance;
    }    
}
