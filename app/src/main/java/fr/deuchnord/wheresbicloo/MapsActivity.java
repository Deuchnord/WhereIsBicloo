package fr.deuchnord.wheresbicloo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.deuchnord.wheresbicloo.util.BiclooAPIFetcher;
import fr.deuchnord.wheresbicloo.util.bicloo.Station;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d(TAG, "onMapReady: map ready");

        ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Fetching the data about the stations...", true);
        double latAverage = 0, lngAverage = 0;

        try {
            List<Station> stations;
            final MapsFillerTask mapsFillerTask = new MapsFillerTask();

            stations = mapsFillerTask.execute().get();
            for (Station station : stations) {
                latAverage += station.getPosition().latitude;
                lngAverage += station.getPosition().longitude;
                mMap.addMarker(new MarkerOptions().position(station.getPosition()).title(station.getAddress()));
                progressDialog.hide();
            }
            latAverage /= stations.size();
            lngAverage /= stations.size();

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latAverage, lngAverage), 12.9f));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class MapsFillerTask extends AsyncTask<Void, Void, List<Station>> {
        private static final String TAG = "MapsFillerTask";
        private Exception exception;

        @Override
        public List<Station> doInBackground(Void... args) {
            exception = null;

            Log.d(TAG, "doInBackground: Fetching stations");
            List<Station> stations = new ArrayList<>();

            try {
                stations = BiclooAPIFetcher.getStations();
                Log.d(TAG, "doInBackground: stations fetched");
            } catch (IOException e) {
                exception = new Exception(getString(R.string.err_connectivity) + e.getLocalizedMessage());
            } catch (JSONException e) {
                exception = new Exception(getString(R.string.err_invalid_json) + e.getLocalizedMessage());
            }

            return stations;
        }

        @Override
        protected void onPostExecute(List<Station> stations) {
            if(exception != null) {
                Log.d(TAG, "onPostExecute: " + exception.getMessage());
            }
            super.onPostExecute(stations);
        }
    }
}
