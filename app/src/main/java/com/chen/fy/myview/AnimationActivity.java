package com.chen.fy.myview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_demo);

        imageView = findViewById(R.id.animationDemo);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);   //图片开始的透明度  结束的透明度
        alphaAnimation.setDuration(0);     //设置动画时间
        imageView.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setImageResource(R.drawable.user_1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(AnimationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
