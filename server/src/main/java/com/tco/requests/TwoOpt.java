package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

public class TwoOpt extends Tour {
    void twoOpt() {}

    void improve() {}

    private boolean twoOptImproves(Places route, int i, int k) {
        return DistanceCalculator.calculator(route.get(i), route.get(k), 3695.0) + 
        DistanceCalculator.calculator(route.get(i+1), route.get(k+1), 3695.0) < 
        DistanceCalculator.calculator(route.get(i), route.get(i+1), 3695.0) + 
        DistanceCalculator.calculator(route.get(k), route.get(k+1), 3695.0);
    }

    private void twoOptReverse() {}

}