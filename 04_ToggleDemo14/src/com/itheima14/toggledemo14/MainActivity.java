package com.itheima14.toggledemo14;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.itheima14.toggledemo14.ToggleView.OnToggleChangeListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ToggleView tv_test = (ToggleView) findViewById(R.id.tv_test);
		boolean toggleOn = tv_test.isToggleOn();
		//处理事件
		tv_test.setOnToggleChangeListener(new OnToggleChangeListener() {
			
			@Override
			public void onToggleChanged(ToggleView toggleView, boolean isToggleOn) {
				// TODO Auto-generated method stub
				//自己处理
				if (isToggleOn) {
					Toast.makeText(getApplicationContext(), "开关打开了", 0).show();
				} else {
					Toast.makeText(getApplicationContext(), "开关关闭了", 0).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
