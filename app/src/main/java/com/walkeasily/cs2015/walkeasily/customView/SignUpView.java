package com.walkeasily.cs2015.walkeasily.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.walkeasily.cs2015.walkeasily.Util;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SignUpView extends HorizontalScrollView {
    private int screenWidth;

    private LinearLayout mPerLayout;
    private LinearLayout mSocialLayout;
    private LinearLayout wapper;

    private boolean once;

    private OnCustomScrollListener onCustomScrollListener;

    public SignUpView(Context context) {
        this(context, null);
    }

    public SignUpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        screenWidth = Util.getScreenWidth(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once) {
            wapper = (LinearLayout) getChildAt(0);
            mPerLayout = (LinearLayout) wapper.getChildAt(0);
            mSocialLayout = (LinearLayout) wapper.getChildAt(1);

            mPerLayout.getLayoutParams().width = screenWidth;
            mSocialLayout.getLayoutParams().width = screenWidth;

            once = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
//            this.smoothScrollTo(screenWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX = getScrollX();

                if (scrollX >= (screenWidth / 2)) {
                    this.smoothScrollTo(screenWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }

                return true;
        }

        return super.onTouchEvent(ev);
    }

    public interface OnCustomScrollListener {
        void customScrollListener(int scrollX);
    }

    public void setOnCustomScrollListener(OnCustomScrollListener onCustomScrollListener) {
        this.onCustomScrollListener = onCustomScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onCustomScrollListener != null) {
            onCustomScrollListener.customScrollListener(l);
        }
    }
}
