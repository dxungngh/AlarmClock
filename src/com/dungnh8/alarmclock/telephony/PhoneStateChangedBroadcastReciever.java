package com.dungnh8.alarmclock.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneStateChangedBroadcastReciever extends BroadcastReceiver {
	private static final String TAG = "PhoneStateChangedBroadcastReciever";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive()");
	}
}
