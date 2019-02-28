package com.chen.fy.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     * 1.定义出俩
     * 2.进行实例化,并将索要用到的方法进行重写
     * 3.在onTouchEvent()方法中把事件回传给手势识别器
     */
    private GestureDetector gestureDetector;
    /**
     * 当前页面下标位置
     */
    private int currentIndex;

    private MyScroller scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);  //重写手势识别器的部分方法
        scroller = new MyScroller();
    }

    private void initView(final Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                //长按
                Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
                super.onLongPress(e);
            }

            /**
             *
             * @param e1  滑动前的事件
             * @param e2  滑动后的事件
             * @param distanceX   滑动的水平距离
             * @param distanceY   滑动的竖直距离
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //滑动
                scrollBy((int) distanceX, 0);   //屏幕内的子孩子进行滑动,并保持y坐标值不变

                return true;   //表示已经处理事件
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //双击
                Toast.makeText(context, "双击", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子,指定孩子在屏幕的位置
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            //对一级子View(即ImageView)进行指定位置
            //并没有对一级子View的子View进行Measure,则此时子View不能显示出来
            view.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }

    }

    /**
     * 测量的时候要测量多次,即要回调多次在能确定
     *
     * @param widthMeasureSpec  父层视图给当前视图的宽和模式
     * @param heightMeasureSpec 父层视图给当前视图的高和模式
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private float startX;
    private float downX;
    private float downY;

    /**
     * 拦截事件
     *
     * @param ev 当前事件
     * @return 返回true表示拦截事件, 不再对事件进行向下分发, 交给当前View的onTouchEvent()进行事件处理
     * 返回false表示不拦截事件,事件继续向下分发
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        boolean result = false;   //模式不对事件进行拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float tempX = Math.abs(endX - downX);
                float tempY = Math.abs(endY - downY);
                if (tempX > tempY && tempX > 5) {
                    result = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        gestureDetector.onTouchEvent(event); //把事件传到手势识别器
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1 记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //2 来到新坐标
                float endX = event.getX();
                //3 计算偏移量
                //下标位置
                int tempIndex = currentIndex;
                if ((startX - endX) > getWidth() / 2) {
                    //显示下一个页面
                    tempIndex = tempIndex + 1;
                } else if ((endX - startX) > getWidth() / 2) {
                    //显示上一个页面
                    tempIndex = tempIndex - 1;
                }
                //根据下标位置移动到指定页面
                scrollToPager(tempIndex);
                break;
        }

        return true;   //表示已经消化事件
    }

    /**
     * 屏蔽非法值(数组越界之类),根据位置移动到指定页面
     */
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }

        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //获取当前页面的下标
        currentIndex = tempIndex;
        if (mOnPagerChangeListener != null) {
            mOnPagerChangeListener.onScrollToPager(currentIndex);
        }
        //得到滑动的距离
        float distanceX = currentIndex * getWidth() - getScrollX();
        //滑动到某一个点的坐标
        //scrollTo(currentIndex * getWidth(), 0);   //瞬间移动完成,不美观
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        invalidate();  //导致onDraw()执行,并导致computeScroll()执行
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (scroller.computeScroll()) {
            float x = scroller.getCurrX();
            scrollTo((int) x, 0);
            invalidate();
        }
    }

    /**
     * 定义当页面改变时后的接口
     */
    private onPagerChangeListener mOnPagerChangeListener;

    public interface onPagerChangeListener {
        void onScrollToPager(int index);
    }

    public void setmOnPagerChangeListener(onPagerChangeListener mOnPagerChangeListener) {
        this.mOnPagerChangeListener = mOnPagerChangeListener;
    }

}
