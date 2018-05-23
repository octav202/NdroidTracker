package com.ndroid.ndroidtracker.server;


import android.os.AsyncTask;

import com.ndroid.ndroidtracker.models.DeviceAlert;

public class SendDeviceAlertTask extends AsyncTask<DeviceAlert, Void, Boolean> {

    private SendDeviceAlertCallback mCallback;

    public SendDeviceAlertTask(SendDeviceAlertCallback callback) {
        mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(DeviceAlert... status) {
        // Authenticate
        return ServerApi.sendDeviceAlert(status[0]);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mCallback.onFinished(result);
    }

    public interface SendDeviceAlertCallback {
        void onStarted();

        void onFinished(Boolean result);
    }


}

