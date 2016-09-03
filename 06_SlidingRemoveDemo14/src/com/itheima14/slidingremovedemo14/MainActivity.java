package com.itheima14.slidingremovedemo14;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private List<String> mDatas = new ArrayList<String>();
	private MyAdapter mAdapter;
	private ListView lv_test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lv_test = getListView();
		
		
		initData();
	}
	private void initData() {
		for (int i = 0;i < 100; i++) {
			mDatas.add("andy" + i);
		}
		
		mAdapter = new MyAdapter();
		lv_test.setAdapter(mAdapter);
		
		
		
	}
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHoler mViewHoler = null;
			if (convertView == null) {
				//没有缓存
				convertView = View.inflate(getApplicationContext(), R.layout.item_lv, null);
				
				mViewHoler = new ViewHoler();
				
				mViewHoler.srv_test = (SlidingRemoveView) convertView.findViewById(R.id.srv_test);
				mViewHoler.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				mViewHoler.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
				convertView.setTag(mViewHoler);
			} else {
				//界面的view复用
				mViewHoler = (ViewHoler) convertView.getTag();
			}
			
			//数据
			final String data = mDatas.get(position);
			mViewHoler.tv_content.setText(data);
			
			final ViewHoler viewHoler = mViewHoler;
			//添加事件
			mViewHoler.tv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//删除数据
					mDatas.remove(data);
					//关闭删除view的显示
					viewHoler.srv_test.closeDeleteViewAnim();
					//更新界面
					mAdapter.notifyDataSetChanged();
					
				}
			});
			return convertView;
		}
		
	}
	
	private class ViewHoler{
		SlidingRemoveView srv_test;
		TextView tv_content;
		TextView tv_delete;
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
