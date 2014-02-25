package com.dungnh8.alarmclock.helper;

import com.dungnh8.alarmclock.R;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class EmailHelper {
	private static final String TAG = "EmailHelper";

	/**
	 * send email to author
	 * 
	 * @author dungnh8
	 * @param mContext
	 */
	public static final void sendEmailToAuthor(Context mContext) {
		try {
			String to = mContext.getString(R.string.email);
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
			// need this to prompts email client only
			email.setType("message/rfc822");
			mContext.startActivity(Intent.createChooser(email,
					mContext.getString(R.string.send_email)));
		} catch (Exception e) {
			Log.e(TAG, "sendEmailToAuthor", e);
		}
	}
}