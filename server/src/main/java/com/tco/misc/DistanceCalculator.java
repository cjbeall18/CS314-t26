package com.tco.misc;

import static java.lang.Math.toRadians;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.round;

public final class DistanceCalculator {
    private DistanceCalculator() {}

    public static long calculator(GeographicCoordinate from, 
                                  GeographicCoordinate to, 
                                  double earthRadius) {

        Double fromLat = from.latRadians();
        Double fromLon = from.lonRadians();
        Double toLat = to.latRadians();
        Double toLon = to.lonRadians();
        Double deltaLon = abs(fromLon - toLon);
             
        // calculate the x value for atan
        Double y = sqrt(
            pow(cos(toLat) * sin(deltaLon), 2) + 
            pow(cos(fromLat) * sin(toLat) - sin(fromLat) * cos(toLat) * cos(deltaLon), 2)
        );

        // calculate the y value for atan
        Double x = sin(fromLat) * sin(toLat) + cos(fromLat) * cos(toLat) * cos(deltaLon);

        // calculate arctan(x/y)
        Double arctan = atan2(y, x);
        
        // calculate arc_length = r * arctan(x/y)
        Double distance = earthRadius * arctan;

        return (long)round(distance);
    }
}