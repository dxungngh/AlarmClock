package com.dungnh8.alarmclock.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbsContentHolder {
	private View convertView;
	private boolean isSameSenderWithPrevious =false;
	private int threadType = -1;
	public View getConvertView() {
		return convertView; 
	}
	
	public boolean isSameSenderWithPrevious() {
		return isSameSenderWithPrevious;
	}

	public void setSameSenderWithPrevious(boolean isSameWithPrevious) {
		this.isSameSenderWithPrevious = isSameWithPrevious;
	}

	public void setConvertView(View convertView) {
		this.convertView = convertView;
	}
	public int getThreadType() {
		return threadType;
	}

	public void setThreadType(int threadType) {
		this.threadType = threadType;
	}	

	/**
	 * set all element in holder
	 * @param mes
	 */
	public abstract void setElemnts(Object obj);
	/**
	 * 
	 * @param parent
	 * @param rowView
	 * @param type
	 * @param layoutInflater
	 */
	public abstract void initHolder(ViewGroup parent,  View rowView, int position, LayoutInflater layoutInflater);
}
