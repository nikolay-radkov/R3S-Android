package com.nikolay.r3s.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHelper {
    private static InputStream makeRequest(String urlStr) throws IOException{
        InputStream is = null;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10 * 1000);
        connection.setConnectTimeout(10 * 1000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        int response = connection.getResponseCode();
        if (response > 400) {
            is = null;
        } else {
            is = connection.getInputStream();
        }

        return is;
    }


    public static InputStream getRequestStream(String urlStr) {
        String rssUrl =  "http://" + urlStr;
        InputStream is = null;

        try {
            is = makeRequest(rssUrl);
        } catch (IOException e) {
            try{
            rssUrl =  "https://" + urlStr;
            is = makeRequest(rssUrl);;

            } catch (IOException er) {
                er.printStackTrace();
            }
        }

        return is;
    }
}
