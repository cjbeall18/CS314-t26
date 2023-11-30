package com.tco.requests;
import java.util.ArrayList;

public class Places extends ArrayList<Place> {
    
    public Places() {}

    public Places(Places other) {
        for(Place place : other) { this.add(place); }
    }

    public Places(Place[] other) {
        for(int i=0; i<other.length; i++) { this.add(other[i]); }
    }
}
