package com.chen.fy.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    protected void onDraw(Canvas canvas) {
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

        //画圆
        canvas.drawCircle(paddingLeft + width / 2, paddingRight + height / 2, r, paint);
    }

    //初始化画笔
    private void initPaint() {
        //创建画笔
        paint = new Paint();
        //画笔颜色设置
        paint.setColor(Color.RED);
        //画笔宽度设置 10px
        paint.setStrokeWidth(10);
        //画笔模式设置为填充
        paint.setStyle(Paint.Style.FILL);
    }
}
