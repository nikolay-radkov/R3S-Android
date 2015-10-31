package com.nikolay.r3s.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.models.Subscription;
import com.nikolay.r3s.utils.SubscriptionItemAdapter;

public class MainActivity extends AppCompatActivity {
//    private String[] listData = new String[]{"Post 1", "Post 2", "Post 3", "Post 4", "Post 5", "Post 6"};
    private Subscription[] listData = new Subscription[]{
            new Subscription("Games", "games.com", "favicon.com", "2 April 2015"),
            new Subscription("PHones", "games.com", "favicon.com", "2 April 2015"),
            new Subscription("Cars", "games.com", "favicon.com", "2 April 2015")
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ListView listView = (ListView)this.findViewById(R.id.subscriptionsListView);
        SubscriptionItemAdapter itemAdapter = new SubscriptionItemAdapter(this, R.layout.subscription_item, listData);
//        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this, R.layout.subscription_item, listData);
        listView.setAdapter(itemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
