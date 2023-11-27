package com.tco.requests;
import java.util.ArrayList;

public class Places extends ArrayList<Place> {
    
    public Places() {}

    public Places(Places other) {
        for(Place place : other) { this.add(place); }
    }
}
