package com.ndroid.ndroidtracker.server;


import android.os.AsyncTask;

public class SimulateLocationTask extends AsyncTask<Integer, Void, Void> {

    private SimulateLocationCallback mCallback;

    public SimulateLocationTask(SimulateLocationCallback callback) {
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Integer... deviceId) {
        ServerApi.simulateLocation(deviceId[0]);
        return null;
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(Void result) {
        mCallback.onFinished();
    }

    public interface SimulateLocationCallback {
        void onStarted();
        void onFinished();
    }


}

