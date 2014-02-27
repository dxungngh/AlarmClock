package com.dungnh8.alarmclock.helper;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class AdMobHelper {
	/**
	 * load admob view
	 * 
	 * @author Daniel
	 */
	public static void loadAdView(AdView adView) {
		try {
			AdRequest adRequest = new AdRequest();
			// adRequest.setTesting(true);
			// adRequest.addTestDevice(Constants.TEST_DEVICE_ID);
			adView.loadAd(adRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}