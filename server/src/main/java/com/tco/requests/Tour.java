package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Tour {
    double[][] distances; 
    int[] tour;
    Places places;

    public Tour () {

    }

    public void shorter () {

    }
    public void construct () {
        
    }

    abstract void improve ();
}
