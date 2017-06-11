package fr.deuchnord.wheresbicloo.util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jerome on 11/06/17.
 */

public class JSONAPI {
    /**
     * Fetch a JSON string from the given URL.
     * <strong>Note:</strong> using this method directly is not recommended. Use getJSONObject() or getJSONArray() instead.
     * @param url the URL to fetch
     * @return a string representing the JSON response from the remote server
     * @throws IOException thrown if an error happens while contacting the remote server
     */
    public static String getJSONString(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String jsonString = "";
        String line;

        while((line = reader.readLine()) != null) {
            // Generally does one loop, but some JSON API are formated with several lines
            jsonString += line;
        }

        return jsonString;
    }

    public static JSONArray getJSONArray(URL url) throws IOException, JSONException {
        return new JSONArray(getJSONString(url));
    }

    public static JSONArray getJSONArray(String url) throws IOException, JSONException {
        return new JSONArray(getJSONString(new URL(url)));
    }
}
