package com.nikolay.r3s.controllers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.nikolay.r3s.activities.EntriesActivity;
import com.nikolay.r3s.activities.MainActivity;

public class ShakeManager {
    private SensorManager mSensorManager;
    private float  mAccel; // acceleration apart from gravity
    private float  mAccelCurrent; // current acceleration including gravity
    private float  mAccelLast; // last acceleration including gravity
    private MainActivity mainActivity;
    private EntriesActivity entriesActivity;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ShakeManager(MainActivity mainActivity, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mainActivity = mainActivity;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;

        mSensorManager = (SensorManager)this.mainActivity.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    public ShakeManager(EntriesActivity entriesActivity, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.entriesActivity = entriesActivity;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;

        mSensorManager = (SensorManager)this.entriesActivity.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 12) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);

                    if (mainActivity != null) {
                        mainActivity.onRefresh();
                    } else if (entriesActivity != null) {
                        entriesActivity.onRefresh();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void registerListener() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void removeListener() {
        mSensorManager.unregisterListener(mSensorListener);
    }
}
