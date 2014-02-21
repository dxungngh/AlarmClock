package com.dungnh8.alarmclock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmServiceBroadcastReciever extends BroadcastReceiver {
	private static final String TAG = "AlarmServiceBroadcastReciever";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive()");
		Intent serviceIntent = new Intent(context, AlarmService.class);
		context.startService(serviceIntent);
	}
}