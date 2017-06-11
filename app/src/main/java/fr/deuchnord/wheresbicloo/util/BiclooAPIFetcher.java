package fr.deuchnord.wheresbicloo.util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import fr.deuchnord.wheresbicloo.util.bicloo.Station;

/**
 * Created by jerome on 10/06/17.
 */

public class BiclooAPIFetcher {
    private static String API_KEY = "3e068b6d3c113cd90788acb88f916869712cf4ad";

    public ArrayList<Station> getStations() throws IOException, JSONException {
        JSONArray jsonArray = JSONAPI.getJSONArray("https://api.jcdecaux.com/vls/v1/stations?contract=Nantes&apiKey=" + BiclooAPIFetcher.API_KEY);
        ArrayList<Station> stations = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            stations.add(new Station(jsonArray.getJSONObject(i)));
        }

        return stations;
    }
}
