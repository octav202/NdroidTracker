package com.ndroid.ndroidtracker;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import static com.ndroid.ndroidtracker.Constants.TAG;

public class LocationFragment extends SupportMapFragment implements OnMapReadyCallback{


    private GoogleMap mMap;

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
    }

    public void setMapType(int type) {
        Log.d(TAG, "setMapType() " + type);
        if (mMap != null) {
            mMap.setMapType(type);
        } else {
            Log.e(TAG, "Null Map Object");
        }
    }
}
