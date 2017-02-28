package com.xiandong.fst.application;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.easeui.DemoApplication;
import com.hyphenate.easeui.DemoHelper;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiandong.fst.tools.jpush.IListener;
import com.xiandong.fst.tools.jpush.JPushListenerManager;
import com.xiandong.fst.tools.jpush.NoticeTag;
import com.xiandong.fst.view.activity.AddFriendsActivity;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * 程序入口
 */

public class BaseApplication extends DemoApplication {
    public static IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性能.
        SDKInitializer.initialize(this);
        //JPush1   设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(false);
        //JPush2 初始化 JPush
        JPushInterface.init(this);
        //注册微信1
        iwxapi = WXAPIFactory.createWXAPI(this, Constant.WX_APPID, true);
        //注册微信2
        iwxapi.registerApp(Constant.WX_APPID);

        DemoHelper.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
