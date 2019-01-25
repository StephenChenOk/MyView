package com.chen.fy.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {

    //画笔
    Paint paint;

    // (1) 如果view是在Java代码中new的,则调用这个构造函数
    public DrawView(Context context) {
        super(context);
        //初始化画笔
        initPaint();
    }

    // (2) 如果view是在xml中声明的,调用这个构造函数
    //自定义属性从AttributeSet 中传进来
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {   // canvas 画布
        super.onDraw(canvas);

        //需要手动设置padding熟悉,否则padding不会生效
        //获取传入的padding值
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        //获取控件的长宽
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        //设置圆的半径 宽高最小值的2分之1
        int r = Math.min(width, height) / 2;

        @SuppressLint("DrawAllocation") Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.user_2,null);


        //画圆
       // canvas.drawCircle(paddingLeft + width / 2, paddingRight + height / 2, r, paint);    //圆
        //canvas.drawBitmap(bitmap,0,0,paint);    //图片
//        @SuppressLint("DrawAllocation") Rect rect = new Rect(100,100,500,500);  // 创建一个矩形  分别是距离左边上边右边下边的距离
//        canvas.drawRect(rect,paint);
        canvas.drawText("陈一生",100,300,paint);
    }

    //初始化画笔
    private void initPaint() {
        //创建画笔
        paint = new Paint();
        //画笔颜色设置
        paint.setColor(Color.RED);
        //画笔宽度设置 10px
        paint.setStrokeWidth(1);
        //画笔字体大小
        paint.setTextSize(50f);
        //画笔模式设置为填充
        paint.setStyle(Paint.Style.STROKE);
    }
}
