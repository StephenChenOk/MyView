package com.chen.fy.myswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SwitchView extends View implements View.OnClickListener {

    private Paint paint;
    private Bitmap switch1;
    private Bitmap switch2;

    private boolean flag = true;

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initBitmap();
        this.setOnClickListener(this);    //当前View设置点击事件
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switch1.getWidth(), switch1.getHeight());  //测量图片的宽高
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (flag) {
            canvas.drawBitmap(switch1, 0, 0, paint);
        } else {
            canvas.drawBitmap(switch2, 0, 0, paint);
        }
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿,即图片圆角没有凹凸
    }

    private void initBitmap() {
        switch1 = BitmapFactory.decodeResource(getResources(), R.drawable.btn1);
        switch2 = BitmapFactory.decodeResource(getResources(), R.drawable.btn2);
    }

    @Override
    public void onClick(View v) {
        flag = !flag;
        invalidate();   //强制执行onDraw()方法
    }
}
