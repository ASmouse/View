package com.itheima14.eventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ViewGroupOne extends FrameLayout {

	public ViewGroupOne(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ViewGroupOne(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		PringLog.print("One:dispatchTouchEvent>", ev);
		return false;//super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		PringLog.print("One:onInterceptTouchEvent>", ev);
		return false;//super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		PringLog.print("One:onTouchEvent>", event);
		return super.onTouchEvent(event);
	}
	
	
}
