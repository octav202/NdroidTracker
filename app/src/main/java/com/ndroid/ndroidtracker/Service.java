package com.ndroid.ndroidtracker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.ndroid.ndroidtracker.Constants.GET_DEVICE_ID;
import static com.ndroid.ndroidtracker.Constants.SERVER_URL;
import static com.ndroid.ndroidtracker.Constants.TAG;

public class Service {

    /**
     * Create the URL for the GET Request getDeviceIdUrl.
     * @param name
     * @param pass
     * @return the URL.
     */
    public static URL getDeviceIdUrl(String name, String pass) {
        String template = SERVER_URL+GET_DEVICE_ID +"?name=%s&pass=%s";
        String stringUrl = String.format(template, name,pass);
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Authenticate user by name and pass
     * @param name
     * @param pass
     * @return the id of the user.
     */
    public static int getDeviceId(String name, String pass) {
        URL url = getDeviceIdUrl(name, pass);
        Log.d(TAG, "getDeviceIdURL() :" + url);

        InputStream stream = null;
        HttpURLConnection connection = null;
        int result = 0;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            if (response != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Request Failed :" +response);
                return 0;
            }
            stream = connection.getInputStream();
            if (stream != null) {
                try(BufferedReader reader = new BufferedReader(
                        new InputStreamReader(stream, "UTF8")) ) {
                    result = Integer.parseInt(reader.readLine());
                }
                Log.d(TAG, "Result : " + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        Log.d(TAG, "Result : " + result);
        return result;
    }
}
