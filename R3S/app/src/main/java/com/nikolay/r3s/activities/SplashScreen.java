package com.nikolay.r3s.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nikolay.r3s.R;

public class SplashScreen extends Activity {
    private void goToHome() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

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

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
