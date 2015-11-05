//package com.nikolay.r3s.controllers;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.view.View;
//import android.widget.LinearLayout;
//
///**
// * Created by Nikolay on 03-Nov-15.
// */
//public class ProgressBarController extends AsyncTask<String, Integer, Boolean> {
//    private LinearLayout progressBar;
//    public ProgressBarController(Activity activity, String progressBarId) {
//        LinearLayout linlaHeaderProgress = (LinearLayout) activity.findViewById(progressBarId);
//        this.progressBar = progressBar;
//    }
//
//    @Override
//    protected Boolean doInBackground(String... params) {
//        return null;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
//    }
//}
