package com.dungnh8.alarmclock;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dungnh8.alarmclock.adapter.AlarmListAdapter;
import com.dungnh8.alarmclock.database.Alarm;
import com.dungnh8.alarmclock.database.Database;
import com.dungnh8.alarmclock.helper.DrawHelper;
import com.dungnh8.alarmclock.helper.EmailHelper;
import com.dungnh8.alarmclock.helper.MarketHelper;
import com.dungnh8.alarmclock.helper.MathAlarmServiceHelper;
import com.dungnh8.alarmclock.listener.OnChangedAlarmsListener;

public class AlarmActivity extends FragmentActivity implements
		OnChangedAlarmsListener {
	private List<Alarm> alarms;
	private ImageButton newButton, deleteButton;
	private ListView mathAlarmListView;
	private TextView emptyWarningTextView;
	private AlarmListAdapter alarmListAdapter;
	private Handler mHandler;
	private static final String TAG = "AlarmActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_layout);
		setComponentView();
		setListener();
		MathAlarmServiceHelper.callMathAlarmScheduleService(this);
	}

	private void setComponentView() {
		newButton = (ImageButton) findViewById(R.id.button_new);
		deleteButton = (ImageButton) findViewById(R.id.button_delete);
		mathAlarmListView = (ListView) findViewById(R.id.alarm_list_of_alarm);
		emptyWarningTextView = (TextView) findViewById(R.id.alarm_empty_warning);
		// hide delete button
		deleteButton.setVisibility(View.GONE);
	}

	private void setListener() {
		setNewButtonListener();
	}

	/**
	 * new button listener
	 * 
	 * @author dungnh8
	 */
	private void setNewButtonListener() {
		newButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				Intent newAlarmIntent = new Intent(AlarmActivity.this,
						AlarmPreferencesActivity.class);
				startActivity(newAlarmIntent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		DrawHelper.addOnChangedAlarmsListener(this);
		drawContent();
	}

	@Override
	protected void onDestroy() {
		DrawHelper.removeOnChangedAlarmsListener(this);
		Database.deactivate();
		super.onDestroy();
	}

	/**
	 * draw content
	 * 
	 * @author dungnh8
	 */
	private void drawContent() {
		try {
			Database.init(this);
			alarms = Database.getAll();
			if (alarms == null || alarms.size() <= 0) {
				emptyWarningTextView.setVisibility(View.VISIBLE);
			} else {
				emptyWarningTextView.setVisibility(View.GONE);
				alarmListAdapter = new AlarmListAdapter(this, alarms,
						getSupportFragmentManager());
				mathAlarmListView.setAdapter(alarmListAdapter);
			}
		} catch (Exception e) {
			Log.e(TAG, "drawContent", e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_rate:
			MarketHelper.openMyApplication(this);
			break;
		case R.id.menu_item_other_apps:
			MarketHelper.openMyStore(this);
			break;
		case R.id.menu_item_contact_author:
			EmailHelper.sendEmailToAuthor(this);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onChangedAlarms() {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					Database.init(AlarmActivity.this);
					alarms = Database.getAll();
					alarmListAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					Log.e(TAG, "onChangedAlarms", e);
				}
			}
		});
	}
}