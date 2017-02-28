package com.xiandong.fst.tools.jpush;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xiandong.fst.R;
import com.xiandong.fst.utils.AppUtils;
import com.xiandong.fst.view.activity.AddFriendsActivity;
import com.xiandong.fst.view.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        printBundle(bundle, intent, context);
    }

    // 打印所有的 intent extra 数据
    private void printBundle(Bundle bundle, Intent intent, Context context) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (bundle != null && bundle.getString(JPushInterface.EXTRA_EXTRA) != null) {
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Log.d(TAG, "json:--------------" + json);
                    int flag = json.getInt("flag");
                    String id = "", uid = "", price = "", url = "";
                    if (json.has("id"))
                        id = json.getString("id");
                    if (json.has("user_id"))
                        uid = json.getString("user_id");
                    if (json.has("price"))
                        price = json.getString("price");
                    if (json.has("url"))
                        url = json.getString("url");
                    JPushListenerManager.getInstance().sendBroadCast(new NoticeTag(flag, id, uid, price, url));
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            }
        }
//        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//        }
    }


    /**
     * 设置通知提示方式 - 基础属性
     */
    private void setStyleBasic(Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom(Context context) {
//        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
//                context, R.layout.customer_notitfication_layout,
//                R.id.icon, R.id.title, R.id.text);
//        builder.layoutIconDrawable = R.drawable.ic_launcher;
//        builder.developerArg0 = "developerArg2";
//        JPushInterface.setPushNotificationBuilder(2, builder);
    }


    private void toActivity(Context context) {
//        /** 前台是否运行 */
//        boolean isFrontAppRuning = false;
//        /** 后台是否运行 */
//        boolean isBgAppRuning = false;
//        ActivityManager am = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//        String currentPackageName = cn.getPackageName();
//        if (!TextUtils.isEmpty(currentPackageName)
//                && currentPackageName.equals(context.getPackageName())) {
//            isFrontAppRuning = true;
//        }
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
//        for (ActivityManager.RunningTaskInfo info : list) {
//            if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
//                    && info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
//                isBgAppRuning = true;
//                // find it, break
//                break;
//            }
//        }
//        // 不在前台
//        if (!isFrontAppRuning) {
//            Intent intent = new Intent(context, MainActivity.class);
//            if (isBgAppRuning) {
//                intent.putExtra("type", isBgAppRuning);
//            }
//            context.startActivity(intent);
//        }
        if (AppUtils.isBackground(context)) {
            Log.d(TAG, "is");
            Intent intent;
            intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else {
            Log.d(TAG, "fs");
        }
    }

}



