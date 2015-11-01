package com.nikolay.r3s.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.models.Subscription;

import java.util.ArrayList;

public class SubscriptionItemAdapter extends ArrayAdapter<Subscription> {
    private Activity myContext;
    private ArrayList<Subscription> data;

    public SubscriptionItemAdapter(Context context, int textViewResourceId, ArrayList<Subscription> objects) {
        super(context, textViewResourceId, objects);
        myContext = (Activity) context;
        this.data = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.subscription_item, null);

            viewHolder = new ViewHolder();
            viewHolder.postThumbView = (ImageView) convertView.findViewById(R.id.postThumb);
            viewHolder.postTitleView = (TextView) convertView.findViewById(R.id.postTitleLabel);
            viewHolder.postDateView = (TextView) convertView.findViewById(R.id.postDateLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (data.get(position).getFavicon() == null) {
            viewHolder.postThumbView.setImageResource(R.drawable.error);
        }

        viewHolder.postTitleView.setText(data.get(position).getName());
        viewHolder.postDateView.setText(data.get(position).getUpdatedAt());

        return convertView;
    }

    static class ViewHolder {
        TextView postTitleView;
        TextView postDateView;
        ImageView postThumbView;
    }
}

