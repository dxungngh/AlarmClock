package com.dungnh8.alarmclock.helper;

import com.dungnh8.alarmclock.R;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class MarketHelper {
	private static final String TAG = "MarketHelper";

	/**
	 * open my application on Google Play
	 * 
	 * @author dungnh8
	 * @param mContext
	 */
	public static final void openMyApplication(Context mContext) {
		try {
			try {
				Uri uri = Uri.parse("market://details?id="
						+ mContext.getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				mContext.startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(
						mContext,
						mContext.getString(R.string.could_not_launch_the_market),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "openMyApplication", e);
		}
	}

	/**
	 * open my store on Google Play
	 * 
	 * @author dungnh8
	 * @param mContext
	 */
	public static final void openMyStore(Context mContext) {
		try {
			try {
				Uri uri = Uri.parse(mContext.getString(R.string.my_store));
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				mContext.startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(
						mContext,
						mContext.getString(R.string.could_not_launch_the_market),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "openMyApplication", e);
		}
	}
}