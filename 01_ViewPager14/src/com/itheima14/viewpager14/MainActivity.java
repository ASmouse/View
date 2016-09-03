package com.itheima14.viewpager14;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager vp_test;
	// ViewPager的数据容器
	private List<ImageView> datas = new ArrayList<ImageView>();
	private TextView tv_desc;
	private LinearLayout ll_points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化界面
		initView();

		// 初始化数据
		initData();
		
		//初始化事件
		initEvent();
	}

	private void initEvent() {
		// ViewPager的滑动事件
		vp_test.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// position 页面选中的位置的回调 
System.out.println("onPageSelected:" + position);				
				// 设置点的显示 和 图片的描述
				selectPosition(position);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				//页面滑动的事件 
				// positionOffset  滑动页面的百分比
				// positionOffsetPixels 移动的像素
//System.out.println("onPageScrolled:" + position);				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// 页面状态改变的回调
System.out.println("onPageScrollStateChanged:" + state);				
			}
		});
		
	}

	/**
	 * 根据ViewPager显示的位置来设置描述信息和红点的显示
	 */
	private void selectPosition(int index) {
		//求模运算 
		index = index % datas.size();
		// 设置描述信息
		tv_desc.setText("图片" + index + "的描述信息");

		// 点的显示为红点
		for (int i = 0; i < 5 ; i ++) {
			//改变点的状态选择器
			ll_points.getChildAt(i).setEnabled(index == i);
		}
		// 点的显示
		// 添加点
		/*ll_points.removeAllViews();// 清空点
		for (int i = 0; i < 5; i++) {

			View v_point = new View(this);
			// 默认是白点

			if (i == index) {
				// 选中的红点
				v_point.setBackgroundResource(R.drawable.red_point);
			} else {
				v_point.setBackgroundResource(R.drawable.gray_point);
			}
			// 设置大小 10pix dip
			LayoutParams lp = new LayoutParams(10, 10);
			// 设置点之间的间距
			lp.leftMargin = 7;

			v_point.setLayoutParams(lp);

			// 添加到点的容器中
			ll_points.addView(v_point);
		}*/
	}

	private void initData() {
		// 1. 创建数据
		for (int i = 0; i < 5; i++) {
			// 1. 创建ImageView
			ImageView iv = new ImageView(this);

			// 2. 给iv设置数据
			iv.setBackgroundResource(R.drawable.a + i);

			// 3. 添加到容器中
			datas.add(iv);

			// 添加点
			View v_point = new View(this);
			// 默认是白点
			v_point.setBackgroundResource(R.drawable.point_selector);
			// 设置大小 10pix dip
			LayoutParams lp = new LayoutParams(10, 10);
			// 设置点之间的间距
			lp.leftMargin = 7;

			v_point.setLayoutParams(lp);
			v_point.setEnabled(false);//状态选择器显示白点

			// 添加到点的容器中
			ll_points.addView(v_point);

		}

		// 2. 设置适配器给ViewPager
		MyAdapter adapter = new MyAdapter();
		vp_test.setAdapter(adapter);

		//3. 设置初始化位置
		int startPosition = 10003;//1000000bug
		//默认选择的位置 应该0
		selectPosition(startPosition - startPosition % datas.size());
		
		//设置ViewPager选择的位置
		vp_test.setCurrentItem(startPosition - startPosition % datas.size());
	}

	private void initView() {
		// 界面的组件的获取
		setContentView(R.layout.activity_main);

		// 1. 获取ViewPager组件

		vp_test = (ViewPager) findViewById(R.id.vp_test);

		// 2. 点的描述信息
		tv_desc = (TextView) findViewById(R.id.tv_desc);

		// 3. 点的容器
		ll_points = (LinearLayout) findViewById(R.id.ll_points);

	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// BaseAdapter getCount();

			//System.out.println("getCount()");
			return Integer.MAX_VALUE;
		}

		/*
		 * view 缓存的View object View的标记
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			//System.out.println("isViewFromObject" + ":view:" + view
			//		+ ":object:" + object);
			// 判断view是否展示
			return view == object;
		}

		/**
		 * container 容器 ViewPager 就是vp_test position 显示View的位置 BaseAdapter
		 * getView
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//System.out.println("instantiateItem:" + position);
			// 初始化数据 View

			// 1. 从容器中获取ImageView
			ImageView iv = datas.get(position % datas.size());
			// 2. 把iv 添加到ViewPager中 (ViewGroup的子类)

			vp_test.addView(iv);
			// 3. 返回View的标记

			return iv;
		}

		/**
		 * container 容器 ViewPager 就是vp_test position 显示View的位置 object 标记
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//System.out.println("destroyItem" + position);
			// 回收view的回调
			// 回收的清理
			/*
			 * ImageView iv = datas.get(position); vp_test.removeView(iv);
			 */

			container.removeView((View) object);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
