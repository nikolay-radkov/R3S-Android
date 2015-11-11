package com.nikolay.r3s.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.nikolay.r3s.R;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.data.sqlite.EntriesTable;
import com.nikolay.r3s.data.sqlite.SubscriptionsTable;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.HttpHelper;
import com.nikolay.r3s.utils.XmlParserHelper;

import java.io.InputStream;

public class SubscribeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText rssValue;

    private void goToHome() {
        Intent intent = new Intent(SubscribeActivity.this,MainActivity.class);
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

    private class DownloadRssTask extends AsyncTask<String, Integer, Subscription> {
        @Override
        protected Subscription doInBackground(String... params) {
            String rssUrl = params[0];
            InputStream is = HttpHelper.getRequestStream(rssUrl);
            Subscription subscription = XmlParserHelper.parse(is);

            if (subscription != null) {
                SubscriptionsTable subscriptions = new SubscriptionsTable(SubscribeActivity.this);
                EntriesTable entries = new EntriesTable(SubscribeActivity.this);
                int subscriptionId = subscriptions.insert(subscription);

                for (Entry entry : subscription.getEntries()) {
                    entry.setSubscriptionId(subscriptionId);
                    entries.insert(entry);
                }

            } else {
                // TODO: show message with not supported RSS format
            }

          return subscription;
        }

        @Override
        protected void onPostExecute(Subscription subscription) {
            if( subscription != null) {
                goToHome();
            }
        }
    }
}
