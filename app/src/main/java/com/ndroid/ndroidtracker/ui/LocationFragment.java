package com.ndroid.ndroidtracker.ui;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ndroid.ndroidtracker.server.GetLocationTask;
import com.ndroid.ndroidtracker.server.ServerApi;
import com.ndroid.ndroidtracker.models.DeviceLocation;

import java.util.ArrayList;
import java.util.List;


import static com.ndroid.ndroidtracker.Constants.TAG;

public class LocationFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<DeviceLocation> mDeviceLocations = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        getMapAsync(this);
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
        new GetLocationTask(new GetLocationTask.GetLocationCallback() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onFinished(List<DeviceLocation> result) {
                mDeviceLocations.addAll(result);
                setMarkersForLocations(mDeviceLocations);
            }
        }).execute(ServerApi.getCurrentDeviceId());
    }

    public void setMapType(int type) {
        Log.d(TAG, "setMapType() " + type);
        if (mMap != null) {
            mMap.setMapType(type);
        } else {
            Log.e(TAG, "Null Map Object");
        }
    }

    private void setMarkersForLocations(List<DeviceLocation> deviceLocations) {
        for (DeviceLocation deviceLocation : deviceLocations) {
            LatLng coord = new LatLng(deviceLocation.getLat(), deviceLocation.getLon());
            MarkerOptions marker = new MarkerOptions().position(coord).title(deviceLocation.getTimeStamp());
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
        }
    }
}
