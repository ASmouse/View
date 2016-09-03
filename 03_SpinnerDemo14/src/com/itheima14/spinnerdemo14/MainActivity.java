package com.itheima14.spinnerdemo14;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText et_number;
	private ImageView iv_arrow;
	private PopupWindow pw_numbers;
	
	private List<SpinnerBean> mDatas ;
	
	/**
	 * 数据的bean
	 * @author Administrator
	 *
	 */
	public class SpinnerBean{
		public int icon_id;
		public Drawable icon_drawable;
		public String number;
	}
	
	public interface OnSetDataListener{
		List<SpinnerBean> getDatas();
	}
	
	private OnSetDataListener onSetDataListener;
	private MyAdapter myAdapter;
	private ListView contentView;
	
	public void setOnSetDataListener(OnSetDataListener listener) {
		this.onSetDataListener = listener;
		//监听回调 让使用者设置适配的数据
		mDatas = listener.getDatas();
	}
	
	public void setDatas(List<SpinnerBean> datas){
		this.mDatas = datas;
		myAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		
		initPopupWindow();
		initEvent();
		
		/*
		 * 测试使用的代码
		 * 
		 */
		
		setOnSetDataListener(new OnSetDataListener() {
			
			@Override
			public List<SpinnerBean> getDatas() {
				List<SpinnerBean> beans = new ArrayList<MainActivity.SpinnerBean>();
				for (int i = 0; i < 100 ; i++) {
					SpinnerBean bean = new SpinnerBean();
					
					//号码
					bean.number = "30000000" + i;
					
					//动态设置头像
					if (i % 2 == 0)
						bean.icon_id = R.drawable.ic_launcher;
					else {
						bean.icon_drawable = getResources().getDrawable(R.drawable.user);
					}
					beans.add(bean);
				}
				
				return beans;
			}
		});
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mDatas == null) {
				return 0;
			}
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
			ViewHolder viewHolder = null;
			if (convertView == null) {
				//没有缓存复用
				convertView = View.inflate(getApplicationContext(), R.layout.item_lv, null);
				viewHolder = new ViewHolder();
				
				viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
				viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
				viewHolder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
				
				convertView.setTag(viewHolder);
				
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			final SpinnerBean bean = mDatas.get(position);
			//组件赋值
			//号码
			viewHolder.tv_number.setText(bean.number);
			
			//头像
			if (bean.icon_id != 0) {
				//id设置头像
				viewHolder.iv_user.setImageResource(bean.icon_id);
			} else if (bean.icon_drawable != null) {
				//drawable
				viewHolder.iv_user.setImageDrawable(bean.icon_drawable);
			}
			
			viewHolder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//容器中删除数据
					mDatas.remove(bean);
					//更新界面
					myAdapter.notifyDataSetChanged();
					
				}
			});
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		ImageView iv_user;
		TextView tv_number;
		ImageView iv_delete;
	}
	/**
	 * 初始化弹出窗体
	 */
	private void initPopupWindow() {
		
		//listView来显示号码 
		
		contentView = new ListView(this);
		
		//添加lv的背景
		contentView.setBackgroundResource(R.drawable.listview_background);
		myAdapter = new MyAdapter();
		contentView.setAdapter(myAdapter);
		
		//-2包裹内容
		
		pw_numbers = new PopupWindow(contentView , -2, 180);
		
		pw_numbers.setFocusable(true);//获取焦点
		//设置透明背景
		pw_numbers.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		pw_numbers.setOutsideTouchable(true);//pw 外部点击关闭
		
		
		
		
	}
	
	private void showPW(){
		if (pw_numbers != null && pw_numbers.isShowing()) {
			//显示， 关闭
			pw_numbers.dismiss();
		} else {
			//关闭， 显示
			//弹出窗体显示在et下方
			//设置宽度
			//获取et宽度
			// 测量
	
			//et_number.measure(0, 0);



			//获取测量后的宽度
			pw_numbers.setWidth(et_number.getWidth());
			pw_numbers.showAsDropDown(et_number);
			
		}
	}

	private void initEvent() {
		//给监听添加单击事件
		iv_arrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//弹出窗体显示号码列表
				showPW();
			}
		});
		
		//给listview添加item点击事件
		contentView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击条目
				//1. 让et显示选中的数据
				SpinnerBean spinnerBean = mDatas.get(position);
				et_number.setText(spinnerBean.number);
				//2. 关闭pw
				showPW();
				
			}
		});
		
	}

	private void initView() {
		setContentView(R.layout.activity_main);
		
		et_number = (EditText) findViewById(R.id.et_number);
		
		iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
