package com.dungnh8.alarmclock.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dungnh8.alarmclock.R;

public class PopupFragment extends DialogFragment {
	private static final String TAG = "PopupFragment";
	private Button yesButton, noButton;
	private TextView title, content;
	protected OnClickListener yesListener, noListener;
	protected String titleString, contentString, yesLabel, noLabel;

	public static PopupFragment newInstance(String title, String message,
			String yesLabel, String noLabel, OnClickListener yesListener,
			OnClickListener noListener) {
		PopupFragment f = new PopupFragment();
		f.titleString = title;
		f.contentString = message;
		f.yesLabel = yesLabel;
		f.noLabel = noLabel;
		f.yesListener = yesListener;
		f.noListener = noListener;
		f.setCancelable(true);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyTheme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View v = initControl(inflater, container);
		initEvent();
		return v;
	}

	/**
	 * init controller
	 * 
	 * @param: n/a
	 * @return: n/a
	 * @throws: n/a
	 */
	private View initControl(LayoutInflater inflater, ViewGroup container) {
		if (noListener != null) { // popup two button
			View v = inflater.inflate(R.layout.popup_fragment_two_button,
					container, false);
			title = (TextView) v
					.findViewById(R.id.popup_fragment_two_button_title);
			content = (TextView) v
					.findViewById(R.id.popup_fragment_two_button_content);
			yesButton = (Button) v
					.findViewById(R.id.popup_fragment_two_button_yes_button);
			noButton = (Button) v
					.findViewById(R.id.popup_fragment_two_button_no_button);
			return v;
		} else { // popup one button
			View v = inflater.inflate(R.layout.popup_fragment_one_button,
					container, false);
			title = (TextView) v
					.findViewById(R.id.popup_fragment_one_button_title);
			content = (TextView) v
					.findViewById(R.id.popup_fragment_one_button_content);
			yesButton = (Button) v
					.findViewById(R.id.popup_fragment_one_button_yes_button);
			return v;
		}
	}

	/**
	 * init event for controller
	 * 
	 * @param: n/a
	 * @return: n/a
	 * @throws: n/a
	 */
	private void initEvent() {
		try {
			if (title != null) {
				title.setText(titleString);
			}
			if (content != null) {
				content.setText(contentString);
			}
			if (yesButton != null) {
				yesButton.setText(yesLabel);
				yesButton.setOnClickListener(yesListener);
			}
			if (noButton != null) {
				noButton.setText(noLabel);
				noButton.setOnClickListener(noListener);
			}
		} catch (Exception e) {
			Log.e(TAG, "initEvent", e);
		}
	}
}