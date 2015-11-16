package com.nikolay.r3s.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.utils.RssHelper;

public class SplashScreen extends Activity {
    private void goToHome() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        boolean isConnectedToNetwork = NetworkManager.checkNetworkConnection(this.getApplication());

        if (isConnectedToNetwork) {
            UpdateDataTask updateDataTask = new UpdateDataTask();
            updateDataTask.execute();
        } else {
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        goToHome();
                    }
                }
            };

            timerThread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    private class UpdateDataTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            RssHelper.refreshAll(SplashScreen.this);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            goToHome();
        }
    }
}
