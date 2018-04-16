package com.ndroid.ndroidtracker;

import android.os.AsyncTask;

public class SignInTask extends AsyncTask<String, Void, Integer> {

    private SignInCallback mCallback;
    public SignInTask(SignInCallback callback){
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
        return 0;
    }

    @Override
    protected void onPreExecute() {
        mCallback.onStarted();
    }

    @Override
    protected void onPostExecute(Integer result) {
        mCallback.onFinished(result);
    }

    public interface SignInCallback{
        void onStarted();
        void onFinished(int id);
    }

}

