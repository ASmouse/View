package com.itheima14.eventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ViewGroupTwo extends FrameLayout {

	public ViewGroupTwo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ViewGroupTwo(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		PringLog.print("Two:dispatchTouchEvent", ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		PringLog.print("Two:onInterceptTouchEvent", ev);
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		PringLog.print("Two:onTouchEvent", event);
		return true;//super.onTouchEvent(event);
	}

	

}
