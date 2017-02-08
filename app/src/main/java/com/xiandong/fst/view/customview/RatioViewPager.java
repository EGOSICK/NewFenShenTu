package com.xiandong.fst.view.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by dell on 2016/12/20.
 */

public class RatioViewPager extends ViewPager {
    public RatioViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioViewPager(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (int) (MeasureSpec.getSize(widthMeasureSpec) / 1.2);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(size, mode);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
