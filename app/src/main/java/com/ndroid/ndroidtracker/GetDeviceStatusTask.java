package com.ndroid.ndroidtracker;


import android.os.AsyncTask;

import java.util.List;

public class GetDeviceStatusTask extends AsyncTask<Integer, Void, DeviceStatus> {

    private GetDeviceStatusCallback mCallback;

    public GetDeviceStatusTask(GetDeviceStatusCallback callback) {
        mCallback = callback;
    }

    @Override
    protected DeviceStatus doInBackground(Integer... deviceId) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Service.getDeviceStatus(deviceId[0]);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(DeviceStatus status) {
        mCallback.onFinished(status);
    }

    public interface GetDeviceStatusCallback {
        void onStarted();

        void onFinished(DeviceStatus status);
    }
}

