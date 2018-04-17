package com.ndroid.ndroidtracker;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.ndroid.ndroidtracker.Constants.TAG;

public class LocationFragment extends SupportMapFragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private List<Location> mLocations = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        getMapAsync(this);

        new GetLocationTask(new GetLocationTask.GetLocationCallback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onFinished(List<Location> result) {
                mLocations.addAll(result);
                setMarkersForLocations(mLocations);
            }
        }).execute(Service.getCurrentDeviceId());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        mMap = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mMap = googleMap;
    }

    public void setMapType(int type) {
        Log.d(TAG, "setMapType() " + type);
        if (mMap != null) {
            mMap.setMapType(type);
        } else {
            Log.e(TAG, "Null Map Object");
        }
    }

    private void setMarkersForLocations(List<Location> locations) {
        for (Location location : locations) {
            LatLng coord = new LatLng(location.getLat(), location.getLon());
            MarkerOptions marker = new MarkerOptions().position(coord).title(location.getTimeStamp());
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
        }
    }
}
