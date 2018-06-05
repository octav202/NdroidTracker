package com.ndroid.ndroidtracker.ui;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ndroid.ndroidtracker.Constants;
import com.ndroid.ndroidtracker.models.DeviceLocation;
import com.ndroid.ndroidtracker.server.GetLocationTask;
import com.ndroid.ndroidtracker.server.ServerApi;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends SupportMapFragment implements OnMapReadyCallback {

    private static final String TAG = Constants.TAG + LocationFragment.class.getSimpleName();
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
        refresh();
    }

    private void setMarkersForLocations(final List<DeviceLocation> deviceLocations) {
        Log.d(TAG,"setMarkersForLocations " + deviceLocations.size());
        if (mMap != null) {
            mMap.clear();
            for (DeviceLocation deviceLocation : deviceLocations) {
                LatLng coord = new LatLng(deviceLocation.getLat(), deviceLocation.getLon());
                MarkerOptions marker = new MarkerOptions().position(coord).title(deviceLocation.getTimeStamp());
                mMap.addMarker(marker);
                moveToCurrentLocation(coord);
            }
        } else {
            Log.e(TAG, "Map is null");
        }
    }

    // Move and zoom to marker.
    private void moveToCurrentLocation(LatLng currentLocation) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }
    }

    private void refresh() {
        new GetLocationTask(new GetLocationTask.GetLocationCallback() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onFinished(List<DeviceLocation> result) {
                Log.d(TAG, "onFinished() " + result.size());
                mDeviceLocations.clear();
                mDeviceLocations.addAll(result);
                setMarkersForLocations(mDeviceLocations);
            }
        }).execute(ServerApi.getCurrentDeviceId());
    }
}
