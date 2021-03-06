package com.dungnh8.alarmclock.alert;

import com.dungnh8.alarmclock.AlarmAlertActivity;
import com.dungnh8.alarmclock.database.Alarm;
import com.dungnh8.alarmclock.service.AlarmServiceBroadcastReciever;
import com.dungnh8.alarmclock.util.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent mathAlarmServiceIntent = new Intent(context,
				AlarmServiceBroadcastReciever.class);
		context.sendBroadcast(mathAlarmServiceIntent, null);

		StaticWakeLock.lockOn(context);
		Bundle bundle = intent.getExtras();
		final Alarm alarm = (Alarm) bundle.getSerializable(Constants.ALARM);

		Intent mathAlarmAlertActivityIntent;
		mathAlarmAlertActivityIntent = new Intent(context,
				AlarmAlertActivity.class);
		mathAlarmAlertActivityIntent.putExtra(Constants.ALARM, alarm);
		mathAlarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mathAlarmAlertActivityIntent);
	}
}