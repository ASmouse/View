package com.itheima14.slidingmenudemo14;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlidingMenuView extends ViewGroup {

	private View left_View;
	private View content_View;
	private int mLeft_View_Width;
	private float mDownX;

	private boolean isLeftMenuOpen = false;// 记录左侧菜单是否打开
	private Scroller mScroller;
	private float downX;
	private float downY;
	private long downTime;

	public SlidingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 代码

		// 该对象 可以完成屏幕移动的动画效果
		mScroller = new Scroller(getContext());
	}

	public SlidingMenuView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
	public void leftMenuToggle(){
		isLeftMenuOpen = !isLeftMenuOpen;
		actionUpProcess();
	}
	
	public void openLeftMenu(){
		isLeftMenuOpen = true;
		actionUpProcess();
	}
	
	
	/**
	 * 关闭左侧菜单
	 */
	public void closeLeftMenu(){
		isLeftMenuOpen = false;
		actionUpProcess();
	}

	// 1. 布局完成获取子控件
	@Override
	protected void onFinishInflate() {
		// 布局文件解析完成的回调

		// 获取左侧菜单的view
		left_View = getChildAt(0);
		// 获取内容的view
		content_View = getChildAt(1);

		// 获取左侧菜单宽度

		mLeft_View_Width = left_View.getLayoutParams().width;

		super.onFinishInflate();
	}

	// 2. 测量
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 1. 测量左侧菜单
		// int 32 位
		// 31 32位测量模式
		// UNSPECIFIED 随意 00 30个0
		// EXACTLY 精确 01 30个0
		// AT_MOST 指定最大值 10 30个0

		// 后30位 大小
		// 精确测量 测量出宽度
		// 模式= 模式(前2位) +　宽度(30)
		// 模式 EXACTLY 宽度：mLeft_View_Width
		int leftView_widthMeasureSpec = MeasureSpec.makeMeasureSpec(
				mLeft_View_Width, MeasureSpec.EXACTLY);// 带模式的值
		left_View.measure(leftView_widthMeasureSpec, heightMeasureSpec);

		// 2. 测量内容
		content_View.measure(widthMeasureSpec, heightMeasureSpec);

		// 3.设置大小
		// 把widthMeasureSpec 的模式值去掉

		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
	}

	// 3 布局
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 摆放content的位置

		int dis = 0;
		int left = 0 + dis;
		int top = 0;
		int right = content_View.getMeasuredWidth() + dis;
		int bottom = content_View.getMeasuredHeight();
		content_View.layout(left, top, right, bottom);

		// 摆放左侧菜单位置
		int lv_left = -left_View.getMeasuredWidth() + dis;
		int lv_top = 0;
		int lv_right = 0 + dis;
		int lv_bottom = left_View.getMeasuredHeight();

		left_View.layout(lv_left, lv_top, lv_right, lv_bottom);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 事件的分发 单击的效果

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			downX = ev.getX();
			downY = ev.getY();
			downTime = System.currentTimeMillis();
			break;
		case MotionEvent.ACTION_UP:
			// 松开
			// 单击 1. 同一个点位置 2，时间短
			float upX = ev.getX();
			float upY = ev.getY();

			long upTime = System.currentTimeMillis();
			if (Math.abs(downX - upX) < 5 && Math.abs(downY - upY) < 5
					&& upTime - downTime < 500) {
				// 单击
				// 判断点击的位置
				if (upX > left_View.getMeasuredWidth() && upY > 100) {
					// 如果左侧菜单打开 关闭
					if (isLeftMenuOpen) {
						isLeftMenuOpen = false;
						actionUpProcess();
						return true;
					}
				}
			}

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 事件的拦截
		// 横向滑动 事件拦截
		// 纵向滑动 事件放行 传递给子控件
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// down

			downX = ev.getX();
			downY = ev.getY();
			mDownX = downX;
			break;
		case MotionEvent.ACTION_MOVE:
			// move

			float moveX = ev.getX();
			float moveY = ev.getY();

			float dx = Math.abs(moveX - downX);
			float dy = Math.abs(moveY - downY);

			// 判断方向
			if (dx > dy) {
				// 横向滑动
				// 事件拦截
				return true;
			}
			break;
		default:
			break;
		}
		// return true;
		return super.onInterceptTouchEvent(ev);
	}

	// 处理触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 处理拖动事件
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下

			mDownX = event.getX();

			break;
		case MotionEvent.ACTION_MOVE:
			// 移动的事件处理
			touchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			// 松开
			// 判断屏幕的坐标
			// 1. 获取屏幕的x坐标
			int scrollX = getScrollX();

			// 2. 判断位置
			// 如果scrollX坐标小于左侧菜单宽度的一半(负数)
			if (scrollX < -left_View.getMeasuredWidth() / 2) {
				isLeftMenuOpen = true;// 左侧菜单打开
			} else {
				isLeftMenuOpen = false;// 左侧菜单关闭
			}

			// 处理触摸松开状态的事件
			actionUpProcess();
			break;
		default:
			break;
		}

		return true;
	}

	private void actionUpProcess() {
		// scrollTo(-left_View.getMeasuredWidth(), 0);
		// 动画的效果
		// startX x起始位置
		// startY y起始位置
		// dx x的移动的值
		// dy y的移动的值
		// duration 移动的时间

		int startX = getScrollX();// 当前屏幕位置
		int endX = -1;// 记录目标的位置
		if (isLeftMenuOpen) {
			// 打开
			endX = -left_View.getMeasuredWidth();
		} else {
			// 关闭
			endX = 0;
		}

		int dx = endX - startX;
		int startY = 0;
		int dy = 0;

		int duration = Math.abs(dx) * 5;// 500;//半秒

		// 移动动画的方法
		mScroller.startScroll(startX, startY, dx, dy, duration);

		invalidate();// 绘制 draw dispatchDraw child.draw(,,) computeScroll
	}

	@Override
	public void computeScroll() {
		// 处理坐标的变化
		if (mScroller.computeScrollOffset()) {
			// 计算中
			int currX = mScroller.getCurrX();

			// 把屏幕移动的到currX位置
			scrollTo(currX, 0);
			invalidate();// 触发draw方法
		}
		super.computeScroll();
	}

	private void touchMove(MotionEvent event) {
		// 拖动
		float moveX = event.getX();

		// 取反方向
		int dx = Math.round(mDownX - moveX);// 四舍五入
		// 移动屏幕

		// scrollTo(20,0) 移动那个位置 绝对坐标 移动到20（横坐标）的位置
		// scrollBy(20, 0) 移动的相对位置 往右移动20个像素

		// 判断 拖动的位置是否越界
		int scrollX = getScrollX();// 获取屏幕的x坐标

		if (scrollX + dx < -left_View.getMeasuredWidth()) {
			// 左边界
			scrollTo(-left_View.getMeasuredWidth(), 0);
		} else if (scrollX + dx > 0) {
			// 右边界
			scrollTo(0, 0);
		} else {
			scrollBy(dx, 0);
		}

		// 变成起点坐标
		mDownX = moveX;
	}

}
