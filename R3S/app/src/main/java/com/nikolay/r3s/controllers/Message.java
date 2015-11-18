package com.nikolay.r3s.controllers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import android.os.Vibrator;

public class Message {

	private Context context;

	public Message(Context context) {
		this.context = context;
	}

	public void print(final String message) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				((Activity) context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(context, message, Toast.LENGTH_LONG)
								.show();
						Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
						v.vibrate(500);
					}
				});
			}
		}).start();
	}
}