package com.nikolay.r3s.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.Message;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.utils.RssHelper;

public class SubscribeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText rssValue;

    private void goToHome() {
        Intent intent = new Intent(SubscribeActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        this.rssValue = (EditText) this.findViewById(R.id.txtRss);
        this.findViewById(R.id.btnSubscribe).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean isConnectedToNetwork = NetworkManager.checkNetworkConnection(this.getApplication());

        if (isConnectedToNetwork) {
            switch (v.getId()) {
                case R.id.btnSubscribe:
                    String rssUrl = this.rssValue.getText().toString();

                    DownloadRssTask downloadRssTask = new DownloadRssTask();
                    downloadRssTask.execute(rssUrl);
                    break;
            }
        } else {
            // TODO: show toast
        }
    }

    private class DownloadRssTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String rssUrl = params[0];

            boolean isSuccessful = RssHelper.load(SubscribeActivity.this, rssUrl);
            if (isSuccessful) {
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Message message = new Message(SubscribeActivity.this);

            if (result) {
                message.print("RSS successfully added");

                goToHome();
            } else {
                message.print("Cannot download the RSS");
            }
        }
    }
}
