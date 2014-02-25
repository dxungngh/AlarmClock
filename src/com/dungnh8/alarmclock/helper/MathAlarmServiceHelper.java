package com.dungnh8.alarmclock.helper;

import android.content.Context;
import android.content.Intent;

import com.dungnh8.alarmclock.service.AlarmServiceBroadcastReciever;

public class MathAlarmServiceHelper {
	/**
	 * call math alarm
	 * 
	 * @author dungnh8
	 * @param mContext
	 */
	public static void callMathAlarmScheduleService(Context mContext) {
		Intent mathAlarmServiceIntent = new Intent(mContext,
				AlarmServiceBroadcastReciever.class);
		mContext.sendBroadcast(mathAlarmServiceIntent, null);
	}
}