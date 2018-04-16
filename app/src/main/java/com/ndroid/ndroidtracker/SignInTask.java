package com.ndroid.ndroidtracker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.ndroid.ndroidtracker.Constants.TAG;


public class SignInTask extends AsyncTask<String, Void, Integer> {

    private SignInCallback mCallback;

    public SignInTask(SignInCallback callback) {
        mCallback = callback;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String name = strings[0];
        String pass = strings[1];

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Authenticate
        return Service.getDeviceId(name, pass);
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(Integer result) {
        mCallback.onFinished(result);
    }

    public interface SignInCallback {
        void onStarted();

        void onFinished(int id);
    }


}

