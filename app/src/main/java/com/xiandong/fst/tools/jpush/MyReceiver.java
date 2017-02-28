package com.xiandong.fst.tools.jpush;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xiandong.fst.R;
import com.xiandong.fst.utils.AppUtils;
import com.xiandong.fst.view.activity.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
//        Log.d(TAG, printBundle(bundle, intent, context));


//        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.d(TAG, "111un---123");
//            if (isAppAlive(context, "com.xiandong.fst")) {
//                Log.d(TAG, "111app alive");
//                Intent i = new Intent(context, NewHomeMap.class);
//                i.putExtras(bundle);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            } else {
//                Log.d(TAG, "111app unAlive");
//                Intent launchIntent = context.getPackageManager().
//                        getLaunchIntentForPackage("com.xiandong.fst");
//                launchIntent.setFlags(
//                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                launchIntent.putExtras(bundle);
//                context.startActivity(launchIntent);
//            }
//        }

        printBundle(bundle, intent, context);
//        int temp = 0;
//        if (bundle.getString("cn.jpush.android.ALERT") != null && bundle.getString("cn.jpush.android.ALERT").equals("")) {
//            temp = getContainCount(bundle.getString("cn.jpush.android.ALERT"), "请求添加您为好友");
//        }
//
//        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle, intent, context));
//
//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//            //send the Registration Id to your server...
//
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processCustomMessage(context, bundle);
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//            //////////////////////////////////////////////////////////////
//
//
//            Log.d(TAG, "=====================" + bundle.toString());
//
//
//            // 设置提醒
//            JPushListenerManager.getInstance().sendBroadCast(true);
//
//
//            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//
//            JPushListenerManager.getInstance().sendBroadCast(false);
//
//            //打开自定义的Activity
//            Intent i = new Intent(context, NewFriendActivity.class);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(i);
//
//
//        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
//            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
//
//        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
//        } else {
//            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
//        }
    }

//    private void goToOrder(Context context , Bundle bundle ,String oid){
//        Intent in = new Intent(context, MainActivity.class);
//        in.putExtras(bundle);
//        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        in.putExtra("orderId", oid);
//        context.startActivity(in);
//    }

    // 打印所有的 intent extra 数据
    private void printBundle(Bundle bundle, Intent intent, Context context) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (bundle != null && bundle.getString(JPushInterface.EXTRA_EXTRA) != null) {
//                try {
//                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                    if (json.toString().equals("{}")){
//                        Intent i = new Intent(context, MainActivity.class);
//                        i.putExtras(bundle);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        context.startActivity(i);
//                    }
//                    int flag = json.getInt("flag");
//                    switch (flag) {
//                        case 1:  // 跳转---->> 新好友
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(1, false));
//                            //打开自定义的Activity
//                            Intent i = new Intent(context, AddFriendsActivity.class);
//                            i.putExtras(bundle);
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            context.startActivity(i);
//                            break;
//                        case 2: ////// 接受好友申请
//
//                            break;
//                        case 3:  ///// 聊天
//                            //打开自定义的Activity
//                            goToOrder(context , bundle , json.getString("oid"));
//                            break;
//                        case 4: // 跳转---->> 进入群组
//                            String id = json.getString("id");
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(4, true, id));
//                            break;
//                        case 6: //////// 接单
//                            goToOrder(context , bundle , json.getString("id"));
//                            break;
//                        case 9: //////// 额外金额
//                            goToOrder(context, bundle, json.getString("id"));
//                            break;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }
//        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(5, true));  // 删除好友
//        }
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            toActivity(context);
        }
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            if (bundle != null && bundle.getString(JPushInterface.EXTRA_EXTRA) != null)
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Log.d(TAG, "json:--------------" + json);
//                    int flag = json.getInt("flag");
                    JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(1, true));
//                    switch (flag) {
//                        case 1:
//                            // 设置提醒  // 红色圆点
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(1, true));
//                            break;
//                        case 2:
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(2, true));
//                            break;
//                        case 3:
//
//                            break;
//                        case 4:
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(4, true));
//                            break;
//                        case 6: //////// 接单
//
//                            break;
//                        case 7: //////// 订单完成
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(7, true));
//                            break;
//                        case 8: //////// 取消
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(8, true));
//                            break;
//                        case 9: //////// 额外金额
//                            JPushListenerManager.getInstance().sendBroadCast(new NotifaceTag(9, true , json.getString("price")));
//                            break;
//                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
        }
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
        }else {
            Log.d(TAG, "fs");
        }
    }

}



