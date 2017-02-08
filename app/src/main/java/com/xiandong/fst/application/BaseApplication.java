package com.xiandong.fst.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.DemoApplication;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.controller.EaseUI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiandong.fst.BuildConfig;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

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

        //注册微信1
        iwxapi = WXAPIFactory.createWXAPI(this, Constant.WX_APPID, true);
        //注册微信2
        iwxapi.registerApp(Constant.WX_APPID);

        if (EaseUI.getInstance().init(this, null)) {
            EMClient.getInstance().setDebugMode(true);
            SDKInitializer.initialize(this);
            DemoHelper.getInstance().init(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

}
