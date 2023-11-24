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

    
}