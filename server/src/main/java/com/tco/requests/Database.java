package com.tco.requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.lang.Exception;
import com.tco.requests.Places;
import com.tco.requests.Distances;

public class Database {

	Database() {}

	public Integer found() {return 0;}

	private static Places convertQueryResultsToPlaces(ResultSet results, String columns) throws Exception {
		int count = 0;
		String[] cols = columns.split(",");
		Places places = new Places();
		while (results.next()) {
			Place place = new Place();
			for (String col: cols) {
				place.put(col, results.getString(col));
			}
			place.put("index", String.format("%d",++count));
			places.add(place);
		}
		return places;
	}
}