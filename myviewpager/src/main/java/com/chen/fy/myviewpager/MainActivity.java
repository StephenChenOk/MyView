package com.chen.fy.myviewpager;
/**
 * 自定义viewPager
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity  implements MyViewPager.onPagerChangeListener {

    private MyViewPager myViewPager;
    private RadioGroup radioGroup;
    private int imgId[]={
            R.drawable.user_4,
            R.drawable.user_9,
            R.drawable.user_10,
            R.drawable.user_12,
            R.drawable.user_mine,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        myViewPager = findViewById(R.id.viewpager);
        radioGroup = findViewById(R.id.radioGroup);
        //添加页面
        for(int i=0;i<imgId.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imgId[i]);
            //将图片添加到viewGroup中
            myViewPager.addView(imageView);
        }
        //添加测试页面
        View testView = View.inflate(this,R.layout.test,null);
        myViewPager.addView(testView,2);
        //动态添加radioButton
        for(int i=0;i<myViewPager.getChildCount();i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            if(i==0){
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPager.scrollToPager(checkedId);    //跳到指定页面
            }
        });
        myViewPager.setmOnPagerChangeListener(this);
    }

    @Override
    public void onScrollToPager(int index) {
        radioGroup.check(index);
    }
}
