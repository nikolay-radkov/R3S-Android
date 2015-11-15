package com.nikolay.r3s.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolay.r3s.R;
import com.nikolay.r3s.models.Subscription;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
            convertView = inflater.inflate(R.layout.item_subscription, null);

            viewHolder = new ViewHolder();
            viewHolder.postThumbView = (ImageView) convertView.findViewById(R.id.postThumb);
            viewHolder.postTitleView = (TextView) convertView.findViewById(R.id.postTitleLabel);
            viewHolder.postDateView = (TextView) convertView.findViewById(R.id.postDateLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        if (data.get(position).getFavicon() == null) {
//            viewHolder.postThumbView.setImageResource(R.drawable.error);
//        }

        viewHolder.postTitleView.setTag(data.get(position).getId());

        viewHolder.id = data.get(position).getId();
        viewHolder.postTitleView.setText(data.get(position).getName());
        viewHolder.postDateView.setText(data.get(position).getUpdatedAt());

        viewHolder.imageURL = data.get(position).getFavicon();
        new DownloadImageTask(viewHolder.postThumbView).execute(data.get(position).getFavicon());

        return convertView;
    }

    static class ViewHolder {
        int id;
        String imageURL;
        TextView postTitleView;
        TextView postDateView;
        ImageView postThumbView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bmImage.setImageBitmap(result);
            } else {
                bmImage.setImageResource(R.drawable.error);
            }
        }
    }
}

