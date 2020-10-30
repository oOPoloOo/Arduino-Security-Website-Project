package com.example.lab2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Tools {
    public static String RestURL = "http://10.0.2.2:3100/android/getAllNotes";
    public static String DelURL = "http://10.0.2.2:3100/android/DelNote/";
    public static String AscURL = "http://10.0.2.2:3100/android/getSortASC";
    public static String DescURL = "http://10.0.2.2:3100/android/getSortDESC";
    public static String EditURL = "http://10.0.2.2:3100/android/edit/";
    public static String InsertURL = "http://10.0.2.2:3100/android/postNote/";
    public static String CatsURL = "http://10.0.2.2:3100/android/getCats/";



    public static String HacksURL = "http://10.0.2.2:3100/android/getAllHacks";
    public static String DelAllURL = "http://10.0.2.2:3100/android/delAllHacks";
    public static String HacksCountURL = "http://10.0.2.2:3100/android/getAllHacksCount/";
    public static String responseURL = "http://10.0.2.2:3100/android/toNode/";






    public static boolean IsOnline(Context nContext) {
        boolean connected;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) nContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            connected = false;
        }
        return connected;
    }


}
