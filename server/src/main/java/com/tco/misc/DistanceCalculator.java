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

    public static long calculator(GeographicCoordinate place1, 
                                  GeographicCoordinate place2, 
                                  double earthRadius) {

        Double place1Lat = place1.latRadians();
        Double place1Lon = place1.lonRadians();
        Double place2Lat = place2.latRadians();
        Double place2Lon = place2.lonRadians();
        Double deltaLon = abs(place1Lon - place2Lon);
             
        // calculate the y value for atan
        Double y = sqrt(
            pow(cos(place2Lat) * sin(deltaLon), 2) + 
            pow(cos(place1Lat) * sin(place2Lat) - sin(place1Lat) * cos(place2Lat) * cos(deltaLon), 2)
        );

        // calculate the x value for atan
        Double x = sin(place1Lat) * sin(place2Lat) + cos(place1Lat) * cos(place2Lat) * cos(deltaLon);

        // calculate arctan(y/x)
        Double arctan = atan2(y, x);
        
        // calculate arc_length = r * arctan(y/x)
        Double distance = earthRadius * arctan;

        return (long)round(distance);
    }
}