package com.ndroid.ndroidtracker;


import android.os.AsyncTask;

public class SendDeviceStatusTask extends AsyncTask<DeviceStatus, Void, Boolean> {

    private SendDeviceStatusCallback mCallback;

    public SendDeviceStatusTask(SendDeviceStatusCallback callback) {
        mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(DeviceStatus... status) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Authenticate
        return Service.sendDeviceStatus(status[0]);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mCallback.onFinished(result);
    }

    public interface SendDeviceStatusCallback {
        void onStarted();

        void onFinished(Boolean result);
    }


}

