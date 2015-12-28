package com.example.pulltorefreshtest;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 自定义Scroller,增加Scroller完成回调
 * 参考:http://stackoverflow.com/a/24524214/5167340
 * @author tyaathome
 *
 */
public class MyScroller extends Scroller{

	private boolean mIsStarted;
    private OnFinishListener mOnFinishListener;
	
	public MyScroller(Context context) {
		super(context);
	}

	public MyScroller(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}
	
	public MyScroller(Context context, Interpolator interpolator,
			boolean flywheel) {
		super(context, interpolator, flywheel);
	}

    @Override
    public boolean computeScrollOffset() {
        boolean result = super.computeScrollOffset();
        if (!result && mIsStarted) {
            try { // Don't let any exception impact the scroll animation.
                mOnFinishListener.onFinish();
            } catch (Exception e) {}
            mIsStarted = false;
        }
        return result;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
        mIsStarted = true;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, duration);
        mIsStarted = true;
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        super.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        mIsStarted = true;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }

    public static interface OnFinishListener {
        void onFinish();
    }

}
