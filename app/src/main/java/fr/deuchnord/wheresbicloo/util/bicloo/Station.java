package fr.deuchnord.wheresbicloo.util.bicloo;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jerome on 11/06/17.
 */

public class Station {
    protected long number;
    protected String name, address;
    protected LatLng position;
    protected boolean banking, bonus, isOpen;
    protected int bikeStands, bikeStandAvailable, availableBikes;
    protected Date lastUpdate;

    public Station(long number, String name, String address, LatLng position, boolean banking,
                   boolean bonus, boolean isOpen, int bikeStands, int bikeStandAvailable,
                   int availableBikes, Date lastUpdate) {
        this.number = number;
        this.name = name;
        this.address = address;
        this.position = position;
        this.banking = banking;
        this.bonus = bonus;
        this.isOpen = isOpen;
        this.bikeStands = bikeStands;
        this.bikeStandAvailable = bikeStandAvailable;
        this.availableBikes = availableBikes;
        this.lastUpdate = lastUpdate;
    }

    public Station(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getLong("number"), jsonObject.getString("name"),
                jsonObject.getString("address"), null, jsonObject.getBoolean("banking"),
                jsonObject.getBoolean("bonus"), jsonObject.getString("status").equals("OPEN"),
                jsonObject.getInt("bike_stands"), jsonObject.getInt("available_bike_stands"),
                jsonObject.getInt("available_bikes"), new Date(jsonObject.getLong("last_update")));
        this.position = new LatLng(jsonObject.getJSONObject("position").getDouble("lat"), jsonObject.getJSONObject("position").getDouble("long"));
    }

    public long getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getPosition() {
        return position;
    }

    public boolean isBanking() {
        return banking;
    }

    public boolean isBonus() {
        return bonus;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getBikeStands() {
        return bikeStands;
    }

    public int getBikeStandAvailable() {
        return bikeStandAvailable;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
