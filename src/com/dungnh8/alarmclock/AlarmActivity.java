package com.dungnh8.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dungnh8.alarmclock.adapter.AlarmListAdapter;
import com.dungnh8.alarmclock.database.Alarm;
import com.dungnh8.alarmclock.database.Database;
import com.dungnh8.alarmclock.helper.EmailHelper;
import com.dungnh8.alarmclock.helper.MarketHelper;
import com.dungnh8.alarmclock.helper.MathAlarmServiceHelper;
import com.dungnh8.alarmclock.preferences.AlarmPreferencesActivity;
import com.dungnh8.alarmclock.ui.PopupFragment;

public class AlarmActivity extends FragmentActivity {
	private ImageButton newButton, deleteButton;
	private ListView mathAlarmListView;
	private AlarmListAdapter alarmListAdapter;
	private PopupFragment popupFragment;
	private static final String TAG = "AlarmActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		// hide delete button
		deleteButton.setVisibility(View.GONE);
	}

	private void setListener() {
		setNewButtonListener();
		setMathAlarmListener();
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

	private void setMathAlarmListener() {
		mathAlarmListView.setLongClickable(true);
		mathAlarmListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, int position, long id) {
						view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
						final Alarm alarm = (Alarm) alarmListAdapter
								.getItem(position);
						showPopupDeleteAlarm(alarm);
						return true;
					}
				});
	}

	/**
	 * show popup delete this alarm
	 * 
	 * @author dungnh8
	 * @param alarm
	 */
	private void showPopupDeleteAlarm(final Alarm alarm) {
		View.OnClickListener yesListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					alarmListAdapter.getMathAlarms().remove(alarm);
					alarmListAdapter.notifyDataSetChanged();
					Database.init(AlarmActivity.this);
					Database.deleteEntry(alarm);
					MathAlarmServiceHelper
							.callMathAlarmScheduleService(AlarmActivity.this);
					popupFragment.dismiss();
				} catch (Exception e) {
					Log.e(TAG, "setMathAlarmListener", e);
				}
			}
		};
		View.OnClickListener noListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					popupFragment.dismiss();
				} catch (Exception e) {
					Log.e(TAG, "setMathAlarmListener", e);
				}
			}
		};
		// show popup
		String title = getString(R.string.app_name);
		String message = getString(R.string.delete_this_alarm);
		String yesLabel = getString(R.string.ok);
		String noLabel = getString(R.string.cancel);
		popupFragment = PopupFragment.newInstance(title, message, yesLabel,
				noLabel, yesListener, noListener);
		popupFragment.show(getSupportFragmentManager(), TAG);
	}

	@Override
	protected void onPause() {
		Database.deactivate();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		@SuppressWarnings("deprecation")
		final Object data = getLastNonConfigurationInstance();
		if (data == null) {
			alarmListAdapter = new AlarmListAdapter(this);
		} else {
			alarmListAdapter = (AlarmListAdapter) data;
		}
		mathAlarmListView.setAdapter(alarmListAdapter);
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
}