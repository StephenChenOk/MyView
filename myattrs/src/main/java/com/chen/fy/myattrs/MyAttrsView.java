package com.chen.fy.myattrs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyAttrsView extends View {

    private int age;
    private String name;
    private Bitmap bg;

    public MyAttrsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //使用系统工具获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAttrsView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.MyAttrsView_my_age:
                    age = typedArray.getInt(index, 0);
                    break;
                case R.styleable.MyAttrsView_my_name:
                    name = typedArray.getString(index);
                    break;
                case R.styleable.MyAttrsView_my_bg:
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bg = bitmapDrawable.getBitmap();
                    break;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(30);
        canvas.drawText(name+"----->"+age,200,40,paint);
        canvas.drawBitmap(bg,100,100,paint);

    }
}
