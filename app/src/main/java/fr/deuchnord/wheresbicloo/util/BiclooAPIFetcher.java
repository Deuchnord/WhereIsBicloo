package fr.deuchnord.wheresbicloo.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.deuchnord.wheresbicloo.util.bicloo.Station;

/**
 * Created by jerome on 10/06/17.
 */

public class BiclooAPIFetcher {
    private static final String TAG = "BiclooAPIFetcher";
    private static String API_KEY = "3e068b6d3c113cd90788acb88f916869712cf4ad";

    public static List<Station> getStations() throws IOException, JSONException {
        JSONArray jsonArray = JSONAPI.getJSONArray("https://api.jcdecaux.com/vls/v1/stations?contract=Nantes&apiKey=" + BiclooAPIFetcher.API_KEY);
        Log.d(TAG, "getStations: " + jsonArray.toString());
        List<Station> stations = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            Log.d(TAG, "getStations: " + i + "/" + jsonArray.length());
            Log.d(TAG, "getStations: " + jsonArray.getJSONObject(i));
            stations.add(new Station(jsonArray.getJSONObject(i)));
            Log.d(TAG, "getStations: added to list");
        }

        Log.d(TAG, "getStations: loop finished");

        return stations;
    }
}
