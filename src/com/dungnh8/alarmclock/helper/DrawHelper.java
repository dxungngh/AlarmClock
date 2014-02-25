package com.dungnh8.alarmclock.helper;

import java.util.ArrayList;

import com.dungnh8.alarmclock.listener.OnChangedAlarmsListener;

public class DrawHelper {
	private static ArrayList<OnChangedAlarmsListener> changedAlarmsListeners = new ArrayList<OnChangedAlarmsListener>();

	public static void addOnChangedAlarmsListener(
			OnChangedAlarmsListener listener) {
		changedAlarmsListeners.add(listener);
	}

	public static void removeOnChangedAlarmsListener(
			OnChangedAlarmsListener listener) {
		changedAlarmsListeners.remove(listener);
	}

	public static void changeAlarms() {
		for (OnChangedAlarmsListener listener : changedAlarmsListeners) {
			listener.onChangedAlarms();
		}
	}
}
