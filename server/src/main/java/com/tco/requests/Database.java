package com.tco.requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.lang.Exception;
import com.tco.requests.Places;
import com.tco.requests.Distances;
import com.tco.requests.Select;
import com.tco.requests.Credential;

public class Database {
	
	private final static String COLUMNS = "id,name,municipality,iso_region,iso_country,continent,latitude,longitude,altitude";

	Database() {}

	public Integer found() {return 0;}

	public static Places places(String match, Integer limit) throws Exception {
		String sql      = Select.match(match, limit);
		String url      = Credential.url();
		String user     = Credential.USER;
		String password = Credential.PASSWORD;
		try (
			// connect to the database and query
			Connection conn    = DriverManager.getConnection(url, user, password);
			Statement  query   = conn.createStatement();
			ResultSet  results = query.executeQuery(sql)
		) {
			return convertQueryResultsToPlaces(results, COLUMNS);
		} catch (Exception e) {
			throw e;
		}
	}
	
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