package com.dungnh8.alarmclock.holder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dungnh8.alarmclock.R;
import com.dungnh8.alarmclock.database.Alarm;
import com.dungnh8.alarmclock.database.Database;
import com.dungnh8.alarmclock.helper.MathAlarmServiceHelper;
import com.dungnh8.alarmclock.preferences.AlarmPreferencesActivity;
import com.dungnh8.alarmclock.util.Constants;

public class AlarmHolder extends AbsContentHolder {
	private AlarmHolder holder;
	private Alarm alarm;
	private LinearLayout rowView;
	private CheckBox checkBox;
	private TextView timeTextView, daysTextView;
	private Context mContext;
	private static final String TAG = "AlarmHolder";

	public AlarmHolder(Context mContext) {
		this.mContext = mContext;
		holder = this;
	}

	@Override
	public void setElemnts(Object obj) {
		alarm = (Alarm) obj;
		timeTextView.setText(alarm.getAlarmTimeString());
		daysTextView.setText(alarm.getRepeatDaysString());
		checkBox.setChecked(alarm.getAlarmActive());
		setListener();
	}

	private void setListener() {
		setCheckBoxListener();
		setRowListener();
	}

	private void setCheckBoxListener() {
		checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					alarm.setAlarmActive(checkBox.isChecked());
					Database.update(alarm);
					MathAlarmServiceHelper
							.callMathAlarmScheduleService(mContext);
					if (checkBox.isChecked()) {
						Toast.makeText(mContext,
								alarm.getTimeUntilNextAlarmMessage(),
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Log.e(TAG, "setCheckBoxListener", e);
				}
			}
		});
	}

	/**
	 * click this alarm
	 * 
	 * @author dungnh8
	 */
	private void setRowListener() {
		rowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(mContext,
							AlarmPreferencesActivity.class);
					intent.putExtra(Constants.ALARM, alarm);
					mContext.startActivity(intent);
				} catch (Exception e) {
					Log.e(TAG, "setRowListener", e);
				}
			}
		});
	}

	@Override
	public void initHolder(ViewGroup parent, View rowView, int position,
			LayoutInflater layoutInflater) {
		rowView = layoutInflater.inflate(R.layout.alarm_list_element, parent,
				false);
		this.rowView = (LinearLayout) rowView;
		checkBox = (CheckBox) rowView.findViewById(R.id.checkBox_alarm_active);
		timeTextView = (TextView) rowView
				.findViewById(R.id.textView_alarm_time);
		daysTextView = (TextView) rowView
				.findViewById(R.id.textView_alarm_days);
		rowView.setTag(holder);
		setConvertView(rowView);
	}
}