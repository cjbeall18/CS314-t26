package com.tco.requests;

import java.util.HashMap;
import com.tco.misc.GeographicCoordinate;
import static java.lang.Math.toRadians;
import static java.lang.Double.parseDouble;


class Place extends HashMap<String,String> implements GeographicCoordinate{

    // for testing

    Place(String lat, String lon) {
        this.put("latitude", lat);
        this.put("longitude", lon);
    }

    // required for GSON

    Place() {}

    // implements the interface

    public Double latRadians() {
        return toRadians(parseDouble(this.get("latitude")));
    }

    public Double lonRadians() {
        return toRadians(parseDouble(this.get("longitude")));
    }
    
}
