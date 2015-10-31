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

public class SubscriptionItemAdapter extends ArrayAdapter<Subscription> {
    private Activity myContext;
    private Subscription[] datas;

    public SubscriptionItemAdapter(Context context, int textViewResourceId, Subscription[] objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.subscription_item, null);
        ImageView thumbImageView = (ImageView) rowView.findViewById(R.id.postThumb);
        if (datas[position].getFavicon() == null) {
            thumbImageView.setImageResource(R.drawable.error);
        }

        TextView postTitleView = (TextView) rowView
                .findViewById(R.id.postTitleLabel);
        postTitleView.setText(datas[position].getName());

        TextView postDateView = (TextView) rowView
                .findViewById(R.id.postDateLabel);
        postDateView.setText(datas[position].getUpdatedAt());

        return rowView;
    }
}

