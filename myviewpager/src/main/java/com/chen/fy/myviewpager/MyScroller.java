package com.chen.fy.myviewpager;

public class MyScroller {

    private float startX;
    private float startY;
    private float distanceX;
    private float distanceY;
    private long startTime;    //开始时间
    private boolean isFinish;  //是否滑动完成
    private long totalTime = 500; //设置总的移动时间为500毫秒
    private float currX;

    /**
     * @param startX    开始的横坐标
     * @param startY    开始的纵坐标
     * @param distanceX 在X轴移动的距离
     * @param distanceY 在Y轴移动的距离
     */
    public void startScroll(float startX, float startY, float distanceX, float distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        startTime = System.currentTimeMillis();
        isFinish = false;
    }

    /**
     * 计算速度
     * 计算移动一小段的距离
     * 计算移动一小段的坐标
     * 计算移动一小段的时间
     *
     * @return true  正在移动
     * false 移动完成
     */
    public boolean computeScroll() {
        if (isFinish) {
            return false;
        }
        long endTime = System.currentTimeMillis();
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            //正在移动
            //计算速度
            float speed = distanceX / totalTime;
            float x = speed * passTime;
            currX = startX + x;
        } else {
            //移动完成
            isFinish = true;
            currX = startX + distanceX;
        }
        return true;
    }

    public float getCurrX() {
        return currX;
    }
}
