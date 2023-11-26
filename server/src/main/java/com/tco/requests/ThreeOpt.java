package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;
import com.tco.requests.TwoOpt;

public class ThreeOpt extends Tour {
    @Override
    void improve() {
        System.out.println("inside improve");
        Places route = this.places;
        boolean improvement = true;
        route.add(route.get(0));
        int routeSize = route.size();
        while (improvement) {
            System.out.println("inside while");
            improvement = false;
            for (int i = 0; i <= routeSize-3; i++) {
                System.out.println("inside i for");
                for (int j = i+1; j < routeSize-2; j++) {
                    System.out.println("inside j for");
                    for (int k = j+1; k <= routeSize-1; k++) {
                        System.out.println("inside k for");
                        int reversals = threeOptReversals(route, i, j, k);
                        if (threeOptReverseI1J(reversals)) {
                            TwoOpt.twoOptReverse(route, i+1, j);
                            reversals -= 1;
                        }
                        if (threeOptReverseJ1K(reversals)) {
                            TwoOpt.twoOptReverse(route, j+1, k);
                            reversals -= 2;
                        }
                        if (threeOptReverseI1K(reversals)) {
                            TwoOpt.twoOptReverse(route, i+1, k);
                            reversals -= 4;
                        }
                        if (reversals > 0) {
                            improvement = true;
                        }
                    }
                }
            }
        }
        TwoOpt.rotateStart(route, this.places);
    }

    private boolean threeOptReverseI1J(int reversals) { return (reversals & 0b001) > 0; }
    private boolean threeOptReverseJ1K(int reversals) { return (reversals & 0b010) > 0; }
    private boolean threeOptReverseI1K(int reversals) { return (reversals & 0b100) > 0; }

    private int threeOptReversals(Places route, int i, int j, int k) {
        System.out.println("inside reversals");
        int binVal = 0b000;
        long i1_leg = DistanceCalculator.calculator(route.get(i), route.get(i+1), 3958.8);
        long j1_leg = DistanceCalculator.calculator(route.get(i), route.get(j+1), 3958.8);
        long j_leg = DistanceCalculator.calculator(route.get(i), route.get(j), 3958.8);
        long k_leg = DistanceCalculator.calculator(route.get(i), route.get(k), 3958.8);
        if (i1_leg < k_leg) {
            binVal = binVal | 0b001;
        } else {
            binVal = binVal | 0b000;
        }
        if (j1_leg < k_leg) {
            binVal = binVal | 0b010;
        } else {
            binVal = binVal | 0b000;
        }
        if (i1_leg < j_leg) {
            binVal = binVal | 0b100;
        } else {
            binVal = binVal | 0b000;
        }
        System.out.println("binVal: " + binVal);
        return binVal;
    }
}