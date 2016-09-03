package com.itheima14.slidingremovedemo14;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SlidingRemoveView extends ViewGroup {

	private View content_tv;
	private View delete_tv;
	private int mcontent_height;
	private int mdelete_width;
	private int mdelete_height;
	private ViewDragHelper mViewDragHelper;
	private boolean isDeleteViewShow;//删除的view是否是显示的

	public SlidingRemoveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		mViewDragHelper = ViewDragHelper.create(this, new MyCallBack());

	}

	private class MyCallBack extends Callback {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			// 分享子控件 down事件
			System.out.println(child);

			return child == content_tv || child == delete_tv;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// left child组件的左上点的横坐标
			// 判断拖动的子组件是哪个
			// move
			if (child == content_tv) {
				// 是内容的子控件
				// 判断x坐标
				// 从左往右拖动的越界处理
				if (left > 0) {
					return 0;// 返回值就是child组件的x坐标
				} else if (left < -mdelete_width) {
					// 从右往左拖动，
					return -mdelete_width;
				}
			} else if (child == delete_tv) {
				// 子控件是删除的tv
				if (left > content_tv.getMeasuredWidth()) {
					// 最右侧的坐标
					return content_tv.getMeasuredWidth();
				} else if (left < content_tv.getMeasuredWidth()
						- delete_tv.getMeasuredWidth()) {
					// 最左侧的坐标
					return content_tv.getMeasuredWidth()
							- delete_tv.getMeasuredWidth();
				}
			}

			return left;// child 的x坐标
		}

		// child位置变化的回调
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// TODO Auto-generated method stub

			if (changedView == content_tv) {
				// 改变删除组件的位置
				// left 是content_tv的x坐标
				int d_left = content_tv.getMeasuredWidth() + left;
				int d_top = 0;
				int d_right = d_left + delete_tv.getMeasuredWidth();
				int d_bottom = delete_tv.getMeasuredHeight();
				// 改变删除tv的位置
				delete_tv.layout(d_left, d_top, d_right, d_bottom);
			} else if (changedView == delete_tv) {
				// 改变内容tv的位置
				int c_left = left - content_tv.getMeasuredWidth();
				int c_top = 0;
				int c_right = c_left + content_tv.getMeasuredWidth();
				int c_bottom = content_tv.getMeasuredHeight();
				// 改变删除tv的位置
				content_tv.layout(c_left, c_top, c_right, c_bottom);
			}
			super.onViewPositionChanged(changedView, left, top, dx, dy);
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			System.out.println("松开");
			// 坐标位置
			// 获取content_tv的x坐标

			float content_left = content_tv.getLeft();
			if (content_left < -delete_tv.getMeasuredWidth() / 2) {
				// showLocation();
				openDeleteViewAnim();
			} else {
				// System.out.println("不显示删除");
				// initLocation();
				closeDeleteViewAnim();
			}
			super.onViewReleased(releasedChild, xvel, yvel);
		}

		/*
		 * //水平方向的移动
		 * 
		 * @Override public int clampViewPositionHorizontal(View child, int
		 * left, int dx) { //child 移动子控件 //left 》 child 的left的坐标 // dx 相对移动值
		 * System.out.println("clampViewPositionHorizontal" + child + ":" +
		 * left); return left; }
		 */

	}
	@Override
	public void computeScroll() {
		if (mViewDragHelper.continueSettling(true)) {
			invalidate();
		}
		super.computeScroll();
	}

	public SlidingRemoveView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub

	}

	public void closeDeleteViewAnim() {
		isDeleteViewShow = false;
		// TODO Auto-generated method stub
		// 移动的是删除view
		mViewDragHelper
				.smoothSlideViewTo(delete_tv, content_tv.getMeasuredWidth()
						, 0);
		// 移动的是内容的view
		mViewDragHelper.smoothSlideViewTo(content_tv,
				0, 0);
		
		//更新
		invalidate();
	}
	

	public boolean isDeleteViewShow(){
		return isDeleteViewShow;
	}

	/**
	 * 打开删除view的动画
	 */
	public void openDeleteViewAnim() {
		isDeleteViewShow = true;
		// 移动的是删除view
		mViewDragHelper
				.smoothSlideViewTo(delete_tv, content_tv.getMeasuredWidth()
						- delete_tv.getMeasuredWidth(), 0);
		// 移动的是内容的view
		mViewDragHelper.smoothSlideViewTo(content_tv,
				-delete_tv.getMeasuredWidth(), 0);
		invalidate();
	}

	public void showLocation() {
		// 1. 布局内容
		int dx = delete_tv.getMeasuredWidth();
		// 1. content
		content_tv.layout(0 - dx, 0, content_tv.getMeasuredWidth() - dx,
				content_tv.getMeasuredHeight());
		// 2. delete
		delete_tv.layout(content_tv.getMeasuredWidth() - dx, 0,
				content_tv.getMeasuredWidth() + delete_tv.getMeasuredWidth()
						- dx, delete_tv.getMeasuredHeight());

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// 把touch事件绑定到ViewDragHelper
		mViewDragHelper.processTouchEvent(event);
		return true;// 自己处理事件 move up
	}

	@Override
	protected void onFinishInflate() {
		// 布局解析完成的回调

		content_tv = getChildAt(0);
		delete_tv = getChildAt(1);

		System.out.println("content_tv:" + content_tv);
		System.out.println("delete_tv:" + delete_tv);

		mcontent_height = content_tv.getLayoutParams().height;
		mdelete_width = delete_tv.getLayoutParams().width;
		mdelete_height = delete_tv.getLayoutParams().height;

		super.onFinishInflate();
	}

	// 1.messure
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 1. 测量content
		// 模式 +　大小
		int content_heightMeasureSpec = MeasureSpec.makeMeasureSpec(
				mcontent_height, MeasureSpec.EXACTLY);
		content_tv.measure(widthMeasureSpec, content_heightMeasureSpec);

		// 2. 测量delete
		int delete_widthMeasureSpec = MeasureSpec.makeMeasureSpec(
				mdelete_width, MeasureSpec.EXACTLY);
		int delete_heightMeasureSpec = MeasureSpec.makeMeasureSpec(
				mdelete_height, MeasureSpec.EXACTLY);

		delete_tv.measure(delete_widthMeasureSpec, delete_heightMeasureSpec);

		// 3. 设置大小
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				mcontent_height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 完全显示内容 不显示删除
		initLocation();

	}

	private void initLocation() {
		// 1. 布局内容
		int dx = 0;
		// 1. content
		content_tv.layout(0 - dx, 0, content_tv.getMeasuredWidth() - dx,
				content_tv.getMeasuredHeight());
		// 2. delete
		delete_tv.layout(content_tv.getMeasuredWidth() - dx, 0,
				content_tv.getMeasuredWidth() + delete_tv.getMeasuredWidth()
						- dx, delete_tv.getMeasuredHeight());
	}

}
