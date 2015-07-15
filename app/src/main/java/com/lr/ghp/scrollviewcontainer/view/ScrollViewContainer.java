package com.lr.ghp.scrollviewcontainer.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.lr.ghp.scrollviewcontainer.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 包含两个ScrollView的容器
 *
 *
 */
public class ScrollViewContainer extends RelativeLayout {

    /**
     * 自动上滑
     */
    public static final int AUTO_UP = 0;
    /**
     * 自动下滑
     */
    public static final int AUTO_DOWN = 1;
    /**
     * 动画完成
     */
    public static final int DONE = 2;
    /**
     * 动画速度
     */
    public static final float SPEED = 6.5f;

    private boolean isMeasured = false;

    /**
     * 用于计算手滑动的速度
     */
    private VelocityTracker vt;

    private int mViewHeight;
    private int mViewWidth;

    private View topView;
    private MyScrollView bottomView;

    private boolean canPullDown;
    private boolean canPullUp=true;
    private int state = DONE;

    /**
     * 记录当前展示的是哪个view，0是topView，1是bottomView
     */
    private int mCurrentViewIndex = 0;
    /**
     * 手滑动距离，这个是控制布局的主要变量
     */
    private float mMoveLenY;
    private float mMoveLenX;
    private MyTimer mTimer;
    private float mLastY;
    private float mLastX;
    /**
     * 用于控制是否变动布局的另一个条件，mEvents==0时布局可以拖拽了，mEvents==-1时可以舍弃将要到来的第一个move事件，
     * 这点是去除多点拖动剧变的关键
     */
    private int mEvents;
    private boolean scrollViewSlip;
    private boolean topScrollViewSlip;
    private int viewHight=0;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
//            Log.v("test",mMoveLenY+"/"+mViewWidth);
            if (mMoveLenY != 0) {
                if (state == AUTO_UP) {
                    mMoveLenY -= SPEED;
                    if (mMoveLenY <= -mViewHeight) {
                        mMoveLenY = -mViewHeight;
                        state = DONE;
                        mCurrentViewIndex = 1;
                    }
                } else if (state == AUTO_DOWN) {
                    mMoveLenY += SPEED;
                    if (mMoveLenY >= 0) {
                        mMoveLenY = 0;
                        state = DONE;
                        mCurrentViewIndex = 0;
                    }
                } else {
                    mTimer.cancel();
                }
            }else{
                mTimer.cancel();
            }
            requestLayout();
        }

    };

    public ScrollViewContainer(Context context) {
        super(context);
        init();
    }

    public ScrollViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollViewContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mTimer = new MyTimer(handler);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.v("MotionEvent", "MotionEvent.ACTION_DOWN");
                if (vt == null)
                    vt = VelocityTracker.obtain();
                else
                    vt.clear();
                mLastY = ev.getY();
                Log.v("MotionEvent", "MotionEvent.ACTION_DOWN" + mLastY);
                mLastX=ev.getX();
                vt.addMovement(ev);
                mEvents = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 多一只手指按下或抬起时舍弃将要到来的第一个事件move，防止多点拖拽的bug
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v("MotionEvent", "MotionEvent.ACTION_MOVE");
                vt.addMovement(ev);
                Log.v("test", canPullUp + "/" + " canPullUp dispatchTouchEvent");
                Log.v("test", canPullDown + "/" + "canPullDown dispatchTouchEvent");
                Log.v("test", mCurrentViewIndex + "/" + "dispatchTouchEvent");
                Log.v("test", mEvents + "/" + "dispatchTouchEvent");
                if (canPullUp && mCurrentViewIndex == 0 && mEvents == 0) {
                    mMoveLenY += (ev.getY() - mLastY);
                    mMoveLenX+=(ev.getX()-mLastX);
                    Log.v("MotionEvent", "MotionEvent.ACTION_MOVE" + "/" + mMoveLenY);
                    Log.v("MotionEvent", mLastY + "///" + mLastX);
                    Log.v("MotionEvent", ev.getY() + "///" + ev.getX());
                    // 防止上下越界
                    if (mMoveLenY > 0) {
                        mMoveLenY = 0;
                        mCurrentViewIndex = 0;
                    } else if (mMoveLenY < -mViewHeight) {
                        mMoveLenY = -mViewHeight;
                        mCurrentViewIndex = 1;

                    }
                    if (mMoveLenY < -8) {
                        // 防止事件冲突
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    }
                } else if (isScrollViewSlip()&& mCurrentViewIndex == 1 && mEvents == 0) {
                    if(Math.abs(ev.getY() - mLastY)>(Math.abs(ev.getX() - mLastX)+10)){
                        mMoveLenY += (ev.getY() - mLastY);
                        mMoveLenX+=(ev.getX()-mLastX);
                    }

                    Log.v("test", mMoveLenY + "/" + ev.getY() + "/" + mLastY);
                    // 防止上下越界
                    if (mMoveLenY < -mViewHeight) {
                        mMoveLenY = -mViewHeight;
                        mCurrentViewIndex = 1;
                    } else if (mMoveLenY > 0) {
                        mMoveLenY = 0;
                        mCurrentViewIndex = 0;
                    }
                    if (mMoveLenY > 8 - mViewHeight) {
                        // 防止事件冲突
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    }
                } else
                    mEvents++;
                Log.v("test", mEvents + "///////");
                mLastY = ev.getY();
                mLastX=ev.getX();
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                Log.v("MotionEvent", "MotionEvent.ACTION_UP");
                mLastY = ev.getY();
                mLastX=ev.getX();
//                Log.v("MotionEvent",mLastY+"///"+mLastX);
                vt.addMovement(ev);
                vt.computeCurrentVelocity(700);
                // 获取Y方向的速度
                float mYV = vt.getYVelocity();
                Log.v("test", mMoveLenY + "/" + "dispatchTouchEvent");
                if (mMoveLenY == 0 || mMoveLenY == -mViewHeight)
                    break;
                if (Math.abs(mYV) < 500) {
                    /** 速度小于一定值的时候当作静止释放，这时候两个View往哪移动取决于滑动的距离 */
                    if (mMoveLenY <= -mViewHeight / 2) {
                        state = AUTO_UP;
                    } else if (mMoveLenY > -mViewHeight / 2) {
                        state = AUTO_DOWN;
                    }
                } else {
                    // 抬起手指时速度方向决定两个View往哪移动
                    if (mYV < 0)
                        state = AUTO_UP;
                    else
                        state = AUTO_DOWN;
                }
                mTimer.schedule(2);
                try {
                    vt.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
        try{
            super.dispatchTouchEvent(ev);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v("test", "onLayout");
        Log.v("test", changed + "" + l + "/" + t + "/" + r + "/" + b);
        Log.v("test", mMoveLenY + "/" + mViewWidth);

        topView.layout(0, (int) mMoveLenY, mViewWidth,
                topView.getMeasuredHeight() + (int) mMoveLenY);
        if(changed){
            if(mMoveLenY<0){
                bottomView.layout(0, topView.getMeasuredHeight() - b,
                        mViewWidth, topView.getMeasuredHeight() - b
                                + bottomView.getMeasuredHeight());

            }
        }else{
            bottomView.layout(0, topView.getMeasuredHeight() + (int) mMoveLenY,
                    mViewWidth, topView.getMeasuredHeight() + (int) mMoveLenY
                            + bottomView.getMeasuredHeight());
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v("test", "onMeasure");

        if (!isMeasured) {
            isMeasured = true;
            //mViewHeight = getMeasuredHeight();
            mViewWidth = getMeasuredWidth();
            topView = getChildAt(0);
            bottomView= (MyScrollView) getChildAt(1);
            bottomView.setOnCustomScroolChangeListener(new ScrollInterface() {
                @Override
                public void onSChanged(int l, int t, int oldl, int oldt) {
                    //已经处于顶端
                    if (bottomView.getScaleY() == 0&& mCurrentViewIndex == 1) {
                        canPullDown = true;
                    }else{
                        canPullDown = false;
                    }
                }
            });
            bottomView.setOnTouchListener(bottomViewTouchListener);
            topView.setOnTouchListener(topViewTouchListener);
        }
        mViewHeight = getMeasuredHeight();
    }

    //topview的处理
    private OnTouchListener topViewTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            ScrollView sv;
            if (mCurrentViewIndex == 0) {
                sv=(ScrollView)v.findViewById(R.id.topView);
            }else {
                sv = (ScrollView) v;
            }

            if (sv.getScrollY() == (sv.getChildAt(0).getMeasuredHeight() - sv
                    .getHeight()) && mCurrentViewIndex == 0)
                canPullUp = true;
            else
                canPullUp = false;
            return false;
        }
    };
    private OnTouchListener bottomViewTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            MyScrollView sv = (MyScrollView) v;
            Log.v("test", sv.getScrollY() + "/" + mCurrentViewIndex + "bottomViewTouchListener");
            if (sv.getScrollY() == 0 && mCurrentViewIndex == 1)
                canPullDown = true;
            else
                canPullDown = false;
            Log.v("test", canPullDown + "////" + "bottomViewTouchListener");
            return false;
        }
    };

    class MyTimer {
        private Handler handler;
        private Timer timer;
        private MyTask mTask;

        public MyTimer(Handler handler) {
            this.handler = handler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
            mTask = new MyTask(handler);
            timer.schedule(mTask, 0, period);
        }

        public void cancel() {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
        }

        class MyTask extends TimerTask {
            private Handler handler;

            public MyTask(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }

        }
    }
    //设置bottomView能否上下滑动
    public boolean isScrollViewSlip() {
        return scrollViewSlip;
    }

    public void setScrollViewSlip(boolean scrollViewSlip) {
        this.scrollViewSlip = scrollViewSlip;
    }
    //设置topView能否上下滑动
    public boolean isTopScrollViewSlip() {
        return topScrollViewSlip;
    }

    public void setTopScrollViewSlip(boolean topScrollViewSlip) {
        this.topScrollViewSlip = topScrollViewSlip;
    }
}