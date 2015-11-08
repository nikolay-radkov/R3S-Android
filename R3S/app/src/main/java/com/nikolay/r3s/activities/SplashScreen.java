package com.nikolay.r3s.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;

import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.data.repositories.GenericRepository;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.HttpHelper;
import com.nikolay.r3s.utils.XmlParserHelper;

import java.io.InputStream;
import java.util.ArrayList;

public class SplashScreen extends Activity {
    private void goToHome() {
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        boolean isConnectedToNetwork = NetworkManager.checkNetworkConnection(this.getApplication());

        if (isConnectedToNetwork) {
            UpdateDataTask updateDataTask = new UpdateDataTask();
            updateDataTask.execute();
        } else {
            LoadDataTask loadDataTask = new LoadDataTask();
            loadDataTask.execute();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.finish();
    }

    private class UpdateDataTask extends AsyncTask<String, Integer, Subscription> {

        @Override
        protected Subscription doInBackground(String... params) {
            // TODO: Read links from db
            // TODO: Save to DataContext
            // TODO: Save to db
            String urlStr = "http://www.gamespot.com/feeds/image-galleries/";
            InputStream is = HttpHelper.getRequestStream(urlStr);
            Subscription result = XmlParserHelper.parse(is);
            return result;
        }

        @Override
        protected void onPostExecute(Subscription subscription) {
            GenericRepository<Subscription> subscriptions = new GenericRepository<Subscription>(Subscription.class);
            GenericRepository<Entry> entries = new GenericRepository<Entry>(Entry.class);
            subscriptions.create(subscription);

            for (Entry entry : subscription.getEntries()) {
                entry.setSubscriptionId(subscription.getId());
                entries.create(entry);
            }

            goToHome();
        }
    }

    private class LoadDataTask extends AsyncTask<String, Integer, ArrayList<Subscription>> {

        @Override
        protected ArrayList<Subscription> doInBackground(String... params) {
            // Load from db

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Subscription> subscriptions) {
            goToHome();
        }
    }
}
