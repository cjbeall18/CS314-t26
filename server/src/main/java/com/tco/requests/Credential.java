package com.tco.requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Exception;

class Credential {
    // shared user with read-only access
	final static String USER = "cs314-db";
	final static String PASSWORD = "eiK5liet1uej";
	// connection information when using port forwarding from localhost
	
    // method to get the 'CS314_USE_DATABASE_TUNNEL' environment variable
    protected static String useTunnelEnv() {
        return System.getenv("CS314_USE_DATABASE_TUNNEL");
    }
    
    // method to get the 'CS314_DOCKER' environment variable
    protected static String onDockerEnv() {
        return System.getenv("CS314_DOCKER");
    }

	static protected String url() {
        String useTunnel = useTunnelEnv();
        String onDocker = onDockerEnv();
        String URL = "";

        if(useTunnel != null && useTunnel.equals("true")) {
            URL = "jdbc:mariadb://127.0.0.1:56247/cs314";
        }
        else if(onDocker != null && onDocker.equals("true")) {
            URL = "jdbc:mariadb://127.0.0.1:3306/cs314";
        }
        else {
            URL = "jdbc:mariadb://faure.cs.colostate.edu/cs314";
        }
		return URL;
	}
}