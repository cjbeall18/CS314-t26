package com.tco.requests;
import java.util.ArrayList;

public class Distances extends ArrayList<Long> {
    
    public Long total() {
        
        Long totalDistance = 0L;

        for (Long distance : this) {
                totalDistance += distance;
        }
        
        return totalDistance;
    }    
}
