package com.itheima14.slidingmenudemo14;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_content;
	private SlidingMenuView smv_test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sldingmenu_layout);
		tv_content = (TextView) findViewById(R.id.tv_content);
		smv_test = (SlidingMenuView) findViewById(R.id.smv_test);
	}
	
	public void back(View v){
		//打开左侧菜单
		smv_test.leftMenuToggle();
	}
	
	public void showContent(View v){
		TextView tv = (TextView) v;
		//System.out.println("单击事件" + tv.getText());
		tv_content.setText(tv.getText() + "的内容");
		
		//关闭左侧菜单
		smv_test.closeLeftMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
