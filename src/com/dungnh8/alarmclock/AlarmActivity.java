package com.dungnh8.alarmclock;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.dungnh8.alarmclock.database.Database;
import com.dungnh8.alarmclock.preferences.AlarmPreferencesActivity;
import com.dungnh8.alarmclock.service.AlarmServiceBroadcastReciever;

public class AlarmActivity extends ListActivity implements
		android.view.View.OnClickListener {
	private ImageButton newButton;
	private ListView mathAlarmListView;
	private AlarmListAdapter alarmListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm_activity);
		setComponentView();
		setListener();
		callMathAlarmScheduleService();
	}

	private void setComponentView() {
		newButton = (ImageButton) findViewById(com.dungnh8.alarmclock.R.id.button_new);
		mathAlarmListView = (ListView) findViewById(android.R.id.list);
	}

	private void setListener() {
		setNewButtonListener();
		setMathAlarmListener();
	}

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
						Builder dialog = new AlertDialog.Builder(
								AlarmActivity.this);
						dialog.setTitle(AlarmActivity.this
								.getString(R.string.delete));
						dialog.setMessage(AlarmActivity.this
								.getString(R.string.delete_this_alarm));
						dialog.setPositiveButton(
								AlarmActivity.this.getString(R.string.ok),
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										alarmListAdapter.getMathAlarms()
												.remove(alarm);
										alarmListAdapter.notifyDataSetChanged();
										Database.init(AlarmActivity.this);
										Database.deleteEntry(alarm);
										AlarmActivity.this
												.callMathAlarmScheduleService();
									}
								});
						dialog.setNegativeButton(getString(R.string.cancel),
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});
						dialog.show();
						return true;
					}
				});
	}

	private void callMathAlarmScheduleService() {
		Intent mathAlarmServiceIntent = new Intent(AlarmActivity.this,
				AlarmServiceBroadcastReciever.class);
		sendBroadcast(mathAlarmServiceIntent, null);
	}

	@Override
	protected void onPause() {
		// setListAdapter(null);
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
		this.setListAdapter(alarmListAdapter);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return alarmListAdapter;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
		Intent intent = new Intent(AlarmActivity.this,
				AlarmPreferencesActivity.class);
		intent.putExtra("alarm", alarm);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.checkBox_alarm_active) {
			CheckBox checkBox = (CheckBox) v;
			Alarm alarm = (Alarm) alarmListAdapter.getItem((Integer) checkBox
					.getTag());
			alarm.setAlarmActive(checkBox.isChecked());
			Database.update(alarm);
			AlarmActivity.this.callMathAlarmScheduleService();
			if (checkBox.isChecked()) {
				Toast.makeText(AlarmActivity.this,
						alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG)
						.show();
			}
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
			Uri uri = Uri.parse("market://details?id=" + getPackageName());
			Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
			try {
				startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, "Couldn't launch the market",
						Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.menu_item_website:
			String url = "http://www.neilson.co.za";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
			break;
		case R.id.menu_item_report:
			Intent send = new Intent(Intent.ACTION_SENDTO);
			String uriText;

			String emailAddress = "bugs@neilson.co.za";
			String subject = R.string.app_name + " Bug Report";
			String body = "Debug:";
			body += "\n OS Version: " + System.getProperty("os.version") + "("
					+ android.os.Build.VERSION.INCREMENTAL + ")";
			body += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
			body += "\n Device: " + android.os.Build.DEVICE;
			body += "\n Model (and Product): " + android.os.Build.MODEL + " ("
					+ android.os.Build.PRODUCT + ")";
			body += "\n Screen Width: "
					+ getWindow().getWindowManager().getDefaultDisplay()
							.getWidth();
			body += "\n Screen Height: "
					+ getWindow().getWindowManager().getDefaultDisplay()
							.getHeight();
			body += "\n Hardware Keyboard Present: "
					+ (getResources().getConfiguration().keyboard != Configuration.KEYBOARD_NOKEYS);

			uriText = "mailto:" + emailAddress + "?subject=" + subject
					+ "&body=" + body;

			uriText = uriText.replace(" ", "%20");
			Uri emalUri = Uri.parse(uriText);

			send.setData(emalUri);
			startActivity(Intent.createChooser(send, "Send mail..."));
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}