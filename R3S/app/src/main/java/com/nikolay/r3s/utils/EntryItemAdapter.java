package com.nikolay.r3s.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.models.Entry;

import java.util.ArrayList;

public class EntryItemAdapter extends ArrayAdapter<Entry> {
    private Activity myContext;
    private ArrayList<Entry> data;

    public EntryItemAdapter(Context context, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, textViewResourceId, objects);
        myContext = (Activity) context;
        this.data = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_entry, null);

            viewHolder = new ViewHolder();
            viewHolder.entryTitleView = (TextView) convertView.findViewById(R.id.lblEntryTitle);
            viewHolder.entryDateView = (TextView) convertView.findViewById(R.id.lblEntryDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int background = R.color.colorOdd;

        if (position % 2 == 0) {
            background = R.color.colorEven;
        }

        convertView.setBackgroundColor(ContextCompat.getColor(myContext, background));

        viewHolder.entryTitleView.setText(data.get(position).getTitle());
        viewHolder.entryDateView.setText(data.get(position).getCreatedAt());

        return convertView;
    }

    static class ViewHolder {
        TextView entryTitleView;
        TextView entryDateView;
    }
}
