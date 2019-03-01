package com.chen.fy.contactinterface;
/**
 * 自定义索引View
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndexView extends View {

    private int itemWidth;
    private int itemHeight;

    private Paint paint;

    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);    //设置抗锯齿
        paint.setTypeface(Typeface.DEFAULT_BOLD);  //设置粗体字
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight() / 26;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {
            if (trueIndex == i) {
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.WHITE);
            }
            String word = words[i];
            //矩阵,用矩阵分别把字母ABC等等框起来,从而求出字母所占的宽高
            Rect rect = new Rect();
            paint.getTextBounds(word, 0, 1, rect);
            //求出字母的宽高
            int wordWidth = rect.width();
            int wordHeight = rect.height();
            //求出字母左下角的坐标
            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = itemHeight / 2 + wordHeight / 2 + i * itemHeight;
            canvas.drawText(word, wordX, wordY, paint);
        }
    }

    private int trueIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int tempIndex = (int) (y / itemHeight);  //点击字母索引
                if (tempIndex != trueIndex) {
                    trueIndex = tempIndex;
                    invalidate();
                }
                onIndexChangeListen.onIndexChange(words[tempIndex]);
                break;
            case MotionEvent.ACTION_UP:
                trueIndex = -1;
                invalidate();
        }

        return true;
    }

    private OnIndexChangeListen onIndexChangeListen;

    /**
     * 下标变化接口
     */
    public interface OnIndexChangeListen{
        void onIndexChange(String word);
    }
    public void setOnIndexChangeListen(OnIndexChangeListen onIndexChangeListen){
        this.onIndexChangeListen = onIndexChangeListen;
    }
}
