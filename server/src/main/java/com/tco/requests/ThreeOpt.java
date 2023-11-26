package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.Collections;

public class ThreeOpt extends Tour {
    void improve() {}

    private boolean threeOptReverseI1J(int reversals) { return (reversals & 0b001) > 0; }
    private boolean threeOptReverseJ1K(int reversals) { return (reversals & 0b010) > 0; }
    private boolean threeOptReverseI1K(int reversals) { return (reversals & 0b100) > 0; }

    private int threeOptReversals(Places route, int i, int j, int k) {
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
        return binVal;
    }
}