package com.chen.fy.mybanner;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static ViewPager viewPager;
    private TextView title;
    private LinearLayout box;

    //图片id
    private int[] imagesId = {
            R.drawable.jiacai_1,
            R.drawable.jiacai_2,
            R.drawable.jiacai_3,
            R.drawable.jiacai_4,
            R.drawable.jiacai_5,
    };
    //图片标题
    public static String[] imagesTitle = {
            "图片1",
            "图片2",
            "图片3",
            "图片4",
            "图片5",
    };

    //图片集合
    public static ArrayList<ImageView> images;
    //上一个被选中图片的位置
    private int prePosition = 0;
    //判断当前页面是否是滑动状态,解决当页面手动滑动后不能继续进行自动滑动
    private boolean isScroll = false;

    /**
     * 让图片自己动起来,采用异步handel,因为在Thread中不可以进行UI操作,所有可以用handel实行异步UI操作
     */
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem()+1;
            viewPager.setCurrentItem(item);

            //延迟发消息
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        title = findViewById(R.id.title_viewpager);
        box = findViewById(R.id.box);

        initData();

        viewPager.setAdapter(new MyPagerAdapter(this));

        viewPager.addOnPageChangeListener(this);

        //设置当前页面在中间位置,保证可以实现左右滑动的效果
        viewPager.setCurrentItem(100);           //要保证是实际图片数量的整数倍,也就是保证每次进入都是先显示的第一张图片

        title.setText(imagesTitle[prePosition]);

        //第一次进入时延迟发消息
        handler.sendEmptyMessageDelayed(0,3000);
    }

    //准备数据
    private void initData() {
        images = new ArrayList<>();
        for (int i = 0; i < imagesId.length; i++) {
            //添加图片
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imagesId[i]);
            images.add(imageView);
            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selctor);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12); //自定义一个布局
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = 12;    //距离左边的间距
            }
            point.setLayoutParams(params);   //设置格式,间距等
            box.addView(point);
        }
    }

    /**
     * 当页面滚动时回调这个方法
     *
     * @param i  当前页面的位置
     * @param v  滑动页面的百分比
     * @param i1 在屏幕上滑动的像素
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /**
     * 当前页面被选中时回调
     *
     * @param i 被选中页面的位置
     */
    @Override
    public void onPageSelected(int i) {
        //设置位置
        title.setText(imagesTitle[i % images.size()]);
        //把上一个高亮位置设置为灰色
        box.getChildAt(prePosition).setEnabled(false);
        //当前位置设置为高亮
        box.getChildAt(i % images.size()).setEnabled(true);
        prePosition = i % images.size();
    }

    /**
     * 当页面状态改变时回调
     * 静止-滚动
     * 滚动-静止
     * 静止-拖拽
     *
     * @param i 当前状态
     */
    @Override
    public void onPageScrollStateChanged(int i) {
        if( i == ViewPager.SCROLL_STATE_DRAGGING){   //拖拽状态
            isScroll = true;
        }else if(i == ViewPager.SCROLL_STATE_SETTLING){  //滑动状态

        }else if(i== ViewPager.SCROLL_STATE_IDLE){   //静止状态
            isScroll = false;
            handler.removeCallbacksAndMessages(null);   //清除消息
            handler.sendEmptyMessageDelayed(0,3000);
        }
    }
}
