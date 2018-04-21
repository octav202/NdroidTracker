package com.ndroid.ndroidtracker.server;


import android.os.AsyncTask;

import com.ndroid.ndroidtracker.models.DeviceLocation;

import java.util.List;

public class GetLocationTask extends AsyncTask<Integer, Void, List<DeviceLocation>> {

    private GetLocationCallback mCallback;

    public GetLocationTask(GetLocationCallback callback) {
        mCallback = callback;
    }

    @Override
    protected List<DeviceLocation> doInBackground(Integer... deviceId) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ServerApi.getLocation(deviceId[0]);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(List<DeviceLocation> result) {
        mCallback.onFinished(result);
    }

    public interface GetLocationCallback {
        void onStarted();

        void onFinished(List<DeviceLocation> result);
    }


}

