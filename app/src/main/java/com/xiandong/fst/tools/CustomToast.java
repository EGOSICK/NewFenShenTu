package com.xiandong.fst.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiandong.fst.R;

/**
 * 自定义Toast
 */
public class CustomToast {
    /***
     * 自定义Toast
     *
     * @param is 成功失败
     * @param tm 提示消息
     */
    static Toast toast;

    public static void customToast(boolean is, String tm, Context c) {
        int res;

        if (is) {
            res = R.mipmap.toast_ture;
        } else {
            res = R.mipmap.toast_error;
        }
        View tv = LayoutInflater.from(c).inflate(R.layout.toast_custom, null);
        ImageView img = (ImageView) tv.findViewById(R.id.toastImg);
        img.setImageResource(res);
        TextView msg = (TextView) tv.findViewById(R.id.toastMsg);
        msg.setText(tm);

        if (toast == null) {
            toast = new Toast(c);
            msg.setText(tm);
        } else {
            msg.setText(tm);
        }
        toast.setView(tv);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
