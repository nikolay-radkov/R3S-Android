package com.nikolay.r3s.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHelper {
    public static InputStream getRequestStream(String urlStr) {
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int response = connection.getResponseCode();
            Log.d("debug", "The response is: " + response);
            is = connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return is;
    }
}
