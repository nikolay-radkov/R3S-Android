package com.nikolay.r3s.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nikolay.r3s.R;
import com.nikolay.r3s.constants.Suggestions;
import com.nikolay.r3s.controllers.Message;
import com.nikolay.r3s.controllers.NetworkManager;
import com.nikolay.r3s.utils.Pair;
import com.nikolay.r3s.utils.RssHelper;

import java.util.ArrayList;

public class SubscribeActivity extends AppCompatActivity implements View.OnClickListener,
        Spinner.OnItemSelectedListener {
    private EditText rssValue;
    private Spinner spinner;
    private String selectedRss = null;
    private ArrayList<String> keys;

    private void goToHome() {
        Intent intent = new Intent(SubscribeActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        getSupportActionBar().setTitle("Subscribe");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.rssValue = (EditText) this.findViewById(R.id.txtRss);
        this.findViewById(R.id.btnSubscribe).setOnClickListener(this);

        this.spinner = (Spinner) this.findViewById(R.id.suggestions_spinner);
        keys = new ArrayList<String>();

        for (Pair pair : Suggestions.list) {
            keys.add((String)pair.getKey());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, keys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        boolean isConnectedToNetwork = NetworkManager.checkNetworkConnection(this.getApplication());
        Message message = new Message(SubscribeActivity.this);

        if (isConnectedToNetwork) {
            switch (v.getId()) {
                case R.id.btnSubscribe:
                    String rssUrl = this.rssValue.getText().toString();

                    if (rssUrl.trim().length() == 0) {
                        if (this.selectedRss != null) {
                            rssUrl = this.selectedRss;
                        } else {
                            message.print("Please select or enter RSS");
                            break;
                        }
                    }

                    DownloadRssTask downloadRssTask = new DownloadRssTask();
                    downloadRssTask.execute(rssUrl);
                    break;
            }
        } else {
            message.print("No internet connection");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            this.selectedRss = Suggestions.list[position].getValue();
        } else {
            this.selectedRss = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.selectedRss = null;
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
