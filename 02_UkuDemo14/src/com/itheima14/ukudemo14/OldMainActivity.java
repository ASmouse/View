package com.itheima14.ukudemo14;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class OldMainActivity extends Activity {

	private ImageView iv_menu;
	private ImageView iv_home;
	private RelativeLayout rl_level1;
	private RelativeLayout rl_level2;
	private RelativeLayout rl_level3;

	// 记录level3显示或隐藏的状态
	private boolean isLevel3Show = true;

	// 记录level2显示或隐藏的状态
	private boolean isLevel2Show = true;

	// 记录level1显示或隐藏的状态
	private boolean isLevel1Show = true;
	
	
	private int animCount = 0;//记录当前有多少个动画在运行

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();

		initEvent();

	}

	private void initEvent() {

		OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.iv_home:
					// home菜单
					homeClick();
					break;

				case R.id.iv_menu:
					// menu菜单
					menuClick();
					break;

				default:
					break;
				}

			}
		};
		// 初始化事件
		// 1. 给menu菜单添加点击事件
		iv_menu.setOnClickListener(listener);
		// 2. 给home菜单添加事件
		iv_home.setOnClickListener(listener);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 处理手机硬件的事件
//System.out.println("keyCode:" + keyCode);
		if (keyCode == KeyEvent.KEYCODE_MENU){// 82 menu键值
			menuKeyDown();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

	/**
	 * menu键的监听事件处理 
	 */
	private void menuKeyDown() {
		/*
		 *  1. 都关闭，都显示
		 *  2. 都显示，都关闭
		 *  3. 显示那个level 就关闭哪些level
		 */
		
		if (isLevel1Show && isLevel2Show && isLevel3Show) {//都显示
			//都关闭
			closeLevel(rl_level3, 0);
			closeLevel(rl_level2, 100);
			closeLevel(rl_level1, 200);//延迟100毫秒
		} else if (!isLevel1Show && !isLevel2Show && !isLevel3Show) {//都关闭
			//都显示
			openLevel(rl_level1, 0);
			openLevel(rl_level2, 100);
			openLevel(rl_level3, 200);//延迟100毫秒
		} else if (isLevel1Show && isLevel2Show) {
			closeLevel(rl_level2, 0);
			closeLevel(rl_level1, 100);//延迟100毫秒
		} else if (isLevel1Show) {
			closeLevel(rl_level1, 0);
		}
		
	}
	
	/**
	 * 设置组件是否可用
	 * @param rl_level
	 *      RelativeLayout
	 * @param enable
	 * 	   是否可用
	 */
	private void isEnable(RelativeLayout rl_level,boolean enable) {
		rl_level.setEnabled(enable);//设置层容器是否可用
		
		//设置容器中的子View是否可用
		for (int i = 0; i < rl_level.getChildCount(); i++) {
			rl_level.getChildAt(i).setEnabled(enable);
		}
	}

	/**
	 * 关闭层的动画
	 */
	private void closeLevel(RelativeLayout rl_level,long delayTime) {
		if (animCount != 0) {//动画没有播放
			return ;
		}
		
		
		isEnable(rl_level, false);// 设置该层的组件不可用 
		
		// 记录关闭的状态
		switch (rl_level.getId()) {
		case R.id.rl_level1:
			isLevel1Show = false;
			break;
		case R.id.rl_level2:
			isLevel2Show = false;
			break;
		case R.id.rl_level3:
			isLevel3Show = false;
			break;

		default:
			break;
		}
		RotateAnimation ra = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1f);

		ra.setDuration(500);// 动画的时间
		ra.setFillAfter(true);// 动画停留结束的位置
		ra.setStartOffset(delayTime);//动画延迟的时间

		ra.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// 记录动画开始的标记
				animCount++;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// 记录动画播放完毕
				animCount--;
			}
		});
		// iv完成动画
		rl_level.startAnimation(ra);
	}

	/**
	 * 打开层的动画
	 */
	private void openLevel(RelativeLayout rl_level,long delayTime) {
		//判断动画是否播完
		if (animCount != 0) {//动画没有播放
			return ;
		}
		
		isEnable(rl_level, true);
		// 记录打开状态
		switch (rl_level.getId()) {
		case R.id.rl_level1:
			isLevel1Show = true;
			break;
		case R.id.rl_level2:
			isLevel2Show = true;
			break;
		case R.id.rl_level3:
			isLevel3Show = true;
			break;

		default:
			break;
		}
		RotateAnimation ra = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1f);

		ra.setDuration(500);// 动画的时间
		ra.setFillAfter(true);// 动画停留结束的位置
		ra.setStartOffset(delayTime);//动画延迟的时间
		ra.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// 记录动画开始的标记
				animCount++;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// 记录动画播放完毕
				animCount--;
			}
		});
		// iv完成动画
		rl_level.startAnimation(ra);
	}

	/**
	 * menu菜单事件功能
	 */
	protected void menuClick() {
		/**
		 * 让第三层菜单显示或隐藏
		 * 
		 */
		isLevel3Show = !isLevel3Show;

		if (isLevel3Show) {
			// rl_level3.setVisibility(View.VISIBLE);

			// 显示动画
			// 隐藏的动画
			openLevel(rl_level3,0);

		} else {
			// rl_level3.setVisibility(View.GONE);
			// 隐藏的动画
			closeLevel(rl_level3,0);

		}
	}

	/**
	 * home菜单的事件功能
	 */
	protected void homeClick() {
		/*
		 * 控制第二层和第三层的操作 1. 如果都不显示，只打开第二层 2. 如果都显示，都关闭 3. 如果第二层显示，第二层关闭
		 */

		if (!isLevel2Show && !isLevel3Show) {// 1. 如果都不显示

			// 只打开第二层
			openLevel(rl_level2,0);
		} else if (isLevel2Show && isLevel3Show) {// 如果都显示
			// 都关闭
			closeLevel(rl_level2,100);// 延迟100毫秒播放动画
			closeLevel(rl_level3,0);
		} else if (isLevel2Show) {
			// 第二层关闭
			closeLevel(rl_level2,0);
		}

	}

	private void initView() {
		setContentView(R.layout.activity_main);

		// 1. 获取home键
		iv_home = (ImageView) findViewById(R.id.iv_home);

		// 2. 获取menu键
		iv_menu = (ImageView) findViewById(R.id.iv_menu);

		// 3. 获取第一个层
		rl_level1 = (RelativeLayout) findViewById(R.id.rl_level1);

		// 3. 获取第二个层
		rl_level2 = (RelativeLayout) findViewById(R.id.rl_level2);

		// 3. 获取第三个层
		rl_level3 = (RelativeLayout) findViewById(R.id.rl_level3);

	}

}
