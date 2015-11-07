package com.nikolay.r3s.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.constants.RepositoryTypes;
import com.nikolay.r3s.data.repositories.EntrySpecificationBySubscriptionId;
import com.nikolay.r3s.data.repositories.GenericRepository;
import com.nikolay.r3s.models.Entry;
import com.nikolay.r3s.utils.EntryItemAdapter;

import java.util.ArrayList;

public class EntriesActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    private GenericRepository<Entry> repository = new GenericRepository<Entry>(RepositoryTypes.ENTRY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        String subscriptionId = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                subscriptionId = null;
            } else {
                subscriptionId = extras.getString("SUBSCRIPTION_ID");
            }
        } else {
            subscriptionId = (String) savedInstanceState.getSerializable("SUBSCRIPTION_ID");
        }

        ArrayList<Entry> listData = repository.query(new EntrySpecificationBySubscriptionId(subscriptionId));

        ListView listView = (ListView) this.findViewById(R.id.entriesListView);
        listView.setOnItemClickListener(this);
        EntryItemAdapter itemAdapter = new EntryItemAdapter(this, R.layout.item_entry, listData);
        listView.setAdapter(itemAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(EntriesActivity.this, EntryInfoActivity.class);
        String entryId = (String) view.findViewById(R.id.lblEntryTitle).getTag();
        intent.putExtra("ENTRY_ID", entryId);
        startActivity(intent);
    }
}