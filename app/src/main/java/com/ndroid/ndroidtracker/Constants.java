package com.ndroid.ndroidtracker;

public class Constants {

    public final static String TAG = "NT_";


    public static String SERVER_URL_PREFIX = "http://";
    public static String SERVER_URL_SUFFIX = ":8080/";
    public static String SERVER_IP = "128.224.170.137";
    //public static final String SERVER_IP = "128.224.170.137";
    public static String SERVER_URL = SERVER_URL_PREFIX + SERVER_IP + SERVER_URL_SUFFIX;
    public static final String ADD_DEVICE = "addDevice";
    public static final String GET_DEVICE = "getDevice";
    public static final String GET_DEVICE_ID = "getDeviceId";
    public static final String SEND_LOCATION = "sendLocation";
    public static final String GET_LOCATION = "getLocation";
    public static final String GET_DEVICE_STATUS = "getDeviceStatus";
    public static final String SEND_DEVICE_STATUS = "sendDeviceStatus";
    public static final String GET_DEVICE_ALERT = "getDeviceAlert";
    public static final String SEND_DEVICE_ALERT= "sendDeviceAlert";

    public static final String SHARED_KEY_DEVICE_ID = "DEVICE_ID";
    public static final String SHARED_KEY_DEVICE_NAME = "DEVICE_NAME";
    public static final String SHARED_KEY_DEVICE_PASS = "DEVICE_PASS";
    public static final String SHARED_KEY_IP_ADDRESS = "IP_ADDRESS";
}
