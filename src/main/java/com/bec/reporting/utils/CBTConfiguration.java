package com.bec.reporting.utils;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class CBTConfiguration {
	 /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(CBTConfiguration.class);
	public static String score = null;
	public static String sessionId=null;
	private String username,authkey;
	private String apiUrl = "crossbrowsertesting.com/api/v3/selenium";
	public CBTConfiguration(String username, String authkey) {
		// for java URL's must be character encoded. If you use 
		// your email, let's replace that character

		if (username.contains("@")) {
			username = username.replace("@", "%40");
		} 
		this.username = username;
		this.authkey = authkey;
	}
	
	public void setScore(String score) {
		String url = "https://" + apiUrl + "/" + CBTConfiguration.sessionId;
		String payload = "{\"action\": \"set_score\", \"score\": \"" + score + "\"}";
		makeRequest("PUT", url,payload);
	}
	
	public void takeSnapshot() {
		if (CBTConfiguration.sessionId != null) {
			String url = "https://" + apiUrl + "/" + CBTConfiguration.sessionId + "/snapshots";
			String payload = "{\"selenium_test_id\": \"" + CBTConfiguration.sessionId + "\"}";
			makeRequest("POST",url,payload);
		}
	}
	
	public void setDescription(String desc) {
		String url = "https://" + apiUrl + "/" + CBTConfiguration.sessionId;
		String payload = "{\"action\": \"set_description\", \"description\": \"" + desc + "\"}";
		makeRequest("PUT", url,payload);
	}
	
	private void makeRequest(String requestMethod, String apiUrl, String payload) {
		URL url;
		String auth = "";

        if (username != null && authkey != null) {
            auth = "Basic " + Base64.encodeBase64String((username+":" + authkey).getBytes());
        }
        try {
            url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", auth);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(payload);
            osw.flush();
            osw.close();
            conn.getResponseMessage();
        } catch (Exception e) {
        	log.error(e.getMessage());
        }
	}
	
	public URL getHubUrl() {
		URL hubUrl = null;
		try {
			hubUrl = new URL("http://" + username + ":" + authkey + "@hub.crossbrowsertesting.com:80/wd/hub");
		} catch (MalformedURLException e) {
			log.error("Invalid HUB URL");
		}
		return hubUrl;
	}
}
