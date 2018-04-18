package com.ndroid.ndroidtracker;


import android.os.AsyncTask;

import java.util.List;

public class SendLocationTask extends AsyncTask<Location, Void, Boolean> {

    private SendLocationCallback mCallback;

    public SendLocationTask(SendLocationCallback callback) {
        mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Location... location) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Authenticate
        return Service.sendLocation(location[0]);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mCallback.onFinished(result);
    }

    public interface SendLocationCallback {
        void onStarted();

        void onFinished(Boolean result);
    }


}

