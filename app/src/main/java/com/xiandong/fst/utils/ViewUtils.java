package com.xiandong.fst.utils;

import android.view.View;

/**
 * Created by dell on 2017/1/22.
 */

public class ViewUtils {

    public static int[] getViewWAndH(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return new int[]{view.getMeasuredWidth(),view.getMeasuredHeight()};
    }
}
