package com.itheima14.eventdemo;

import android.util.Log;
import android.view.MotionEvent;

public class PringLog {
	public static void print(String tag,MotionEvent event){
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d(tag, "ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e(tag, "ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.w(tag, "ACTION_UP");
			break;

		default:
			break;
		}
	}
}
