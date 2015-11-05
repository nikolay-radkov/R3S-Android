package com.nikolay.r3s.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;

import com.nikolay.r3s.R;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.HttpHelper;
import com.nikolay.r3s.utils.XmlParserHelper;

import java.io.InputStream;
import java.util.ArrayList;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        LoadDataTask loadData = new LoadDataTask();
        loadData.execute("http://www.gamespot.com/feeds/image-galleries/");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.finish();
    }

    private class LoadDataTask extends AsyncTask<String, Integer, ArrayList<Subscription>> {

        @Override
        protected ArrayList<Subscription> doInBackground(String... params) {
            String urlStr = params[0];
            InputStream is = HttpHelper.getRequestStream(urlStr);
            ArrayList<Subscription> result = XmlParserHelper.parse(is);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Subscription> subscriptions) {
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
