package com.dungnh8.alarmclock.alert;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class StaticWakeLock {
	private static PowerManager.WakeLock wl = null;
	private static final String TAG = "StaticWakeLock";

	public static void lockOn(Context context) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// Object flags;
		if (wl == null)
			wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
					| PowerManager.ACQUIRE_CAUSES_WAKEUP, "MATH_ALARM");
		wl.acquire();
	}

	public static void lockOff(Context context) {
		// PowerManager pm = (PowerManager)
		// context.getSystemService(Context.POWER_SERVICE);
		try {
			if (wl != null)
				wl.release();
		} catch (Exception e) {
			Log.e(TAG, "lockOff", e);
		}
	}
}