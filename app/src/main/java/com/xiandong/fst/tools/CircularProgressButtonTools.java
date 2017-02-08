package com.xiandong.fst.tools;

import android.os.Handler;

import com.dd.CircularProgressButton;
import com.xiandong.fst.application.Constant;


/**
 * Created by dell on 2017/1/4.
 */

public class CircularProgressButtonTools {

    public static void showErr(final CircularProgressButton btn){
        btn.setProgress(-1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setProgress(0);
            }
        }, Constant.EXECUTIONTIME * 4);
    }

    public static void showTrue(CircularProgressButton btn){
        btn.setProgress(100);
    }

    public static void reduction(CircularProgressButton btn){
        btn.setProgress(0);
    }

    public static void showLoding(CircularProgressButton btn){
        btn.setIndeterminateProgressMode(true);
        btn.setProgress(0);
        btn.setProgress(1);
    }
}
