package com.dungnh8.alarmclock.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dungnh8.alarmclock.AlarmActivity;
import com.dungnh8.alarmclock.database.Alarm;
import com.dungnh8.alarmclock.database.Database;
import com.dungnh8.alarmclock.holder.AbsContentHolder;
import com.dungnh8.alarmclock.holder.AlarmHolder;

public class AlarmListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<Alarm> alarms = new ArrayList<Alarm>();

	public static final String ALARM_FIELDS[] = { Database.COLUMN_ALARM_ACTIVE,
			Database.COLUMN_ALARM_TIME, Database.COLUMN_ALARM_DAYS };

	public AlarmListAdapter(AlarmActivity alarmActivity) {
		mContext = alarmActivity;
		inflater = LayoutInflater.from(this.mContext);
		Database.init(mContext);
		alarms = Database.getAll();
	}

	@Override
	public int getCount() {
		return alarms.size();
	}

	@Override
	public Object getItem(int position) {
		return alarms.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbsContentHolder holder;
		Alarm alarm = (Alarm) getItem(position);
		if (convertView == null) {
			holder = new AlarmHolder(mContext);
			holder.initHolder(parent, convertView, position, inflater);
		} else {
			holder = (AlarmHolder) convertView.getTag();
		}
		holder.setElemnts(alarm);
		convertView = holder.getConvertView();
		return convertView;
	}

	public List<Alarm> getMathAlarms() {
		return alarms;
	}

	public void setMathAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}
}