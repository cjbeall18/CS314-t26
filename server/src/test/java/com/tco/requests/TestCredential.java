package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCredential {

    static class MockCredential extends Credential {

        private static String useTunnelValue = null;
        private static String onDockerValue = null;
    
        protected static String useTunnelEnv() {
            return useTunnelValue;
        }
    
        protected static String onDockerEnv() {
            return onDockerValue;
        }
    
        public static void setUseTunnelValue(String value) {
            useTunnelValue = value;
        }
    
        public static void setOnDockerValue(String value) {
            onDockerValue = value;
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

    private MockCredential mockCredential;

    @BeforeEach
    public void setup() {
        mockCredential = new MockCredential();
    }

    @Test
    @DisplayName("evanloy: Test URL with CS314_USE_DATABASE_TUNNEL=true")
    public void testUseTunnel() {
        mockCredential.setUseTunnelValue("true");
        assertEquals("jdbc:mariadb://127.0.0.1:56247/cs314", mockCredential.url());
    }

    @Test
    @DisplayName("evanloy: Test URL with CS314_DOCKER=true")
    public void testOnDocker() {
        mockCredential.setUseTunnelValue("false");
        mockCredential.setOnDockerValue("true");
        assertEquals("jdbc:mariadb://127.0.0.1:3306/cs314", mockCredential.url());
    }

    @Test
    @DisplayName("evanloy: Test default URL")
    public void testDefaultURL() {
        assertEquals("jdbc:mariadb://faure.cs.colostate.edu/cs314", mockCredential.url());
    }
}
