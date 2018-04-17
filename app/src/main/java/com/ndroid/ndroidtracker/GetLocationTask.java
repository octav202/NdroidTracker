package com.ndroid.ndroidtracker;


import android.os.AsyncTask;

public class GetLocationTask extends AsyncTask<Integer, Void, String> {

    private GetLocationCallback mCallback;

    public GetLocationTask(GetLocationCallback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Integer... deviceId) {

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
    protected void onPostExecute(String result) {
        mCallback.onFinished(result);
    }

    public interface GetLocationCallback {
        void onStarted();

        void onFinished(String result);
    }


}

