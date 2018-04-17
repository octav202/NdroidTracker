package com.ndroid.ndroidtracker;


import android.os.AsyncTask;

import java.util.List;

public class GetLocationTask extends AsyncTask<Integer, Void, List<Location>> {

    private GetLocationCallback mCallback;

    public GetLocationTask(GetLocationCallback callback) {
        mCallback = callback;
    }

    @Override
    protected List<Location> doInBackground(Integer... deviceId) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Authenticate
        return Service.getLocation(deviceId[0]);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(List<Location> result) {
        mCallback.onFinished(result);
    }

    public interface GetLocationCallback {
        void onStarted();

        void onFinished(List<Location> result);
    }


}

