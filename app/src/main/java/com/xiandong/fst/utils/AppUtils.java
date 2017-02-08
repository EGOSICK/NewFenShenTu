package com.xiandong.fst.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

import java.util.ArrayList;
import java.util.List;

/**
 * app 需要的工具类
 */

public class AppUtils {

    /**
     * 判断是否打开了允许虚拟位置,如果打开了 则弹窗让他去关闭
     */
    public static boolean isAllowMockLocation(final Activity context) {
        /**
         * 判断用户是否开启了模拟位置功能
         */
        boolean isOpen = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
        if (!isOpen)
            return false;

        StyledDialog.buildIosAlert(context, "定位失败", "需要关闭【允许模拟位置】功能后才能正常使用", new MyDialogListener() {
            @Override
            public void onFirst() {
                context.finish();
            }

            @Override
            public void onSecond() {
                context.startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                context.finish();
            }
        }).setBtnText("取消", "去关闭").show();

        return isOpen;
    }


    public static void checkJurisdiction(Activity context) {
        String[] permission = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS};

        List<String> qxList = new ArrayList<>();

        for (int i = 0; i < permission.length; i++)
            if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED)
                qxList.add(permission[i]);

        if (qxList.size() <= 0)
            return;
        String[] qx = new String[qxList.size()];
        for (int i = 0; i < qxList.size(); i++)
            qx[i] = qxList.get(i);
        ActivityCompat.requestPermissions(context, qx, 1);

    }


}
