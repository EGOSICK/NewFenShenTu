package com.xiandong.fst.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.utils.wxpayutils.Util;

import java.io.File;

/**
 * 分享工具
 */

public class WXShareTools {
    /**
     * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
     *
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public static void wechatShare(int flag, Context context) {
        //实例化
        wxApi = WXAPIFactory.createWXAPI(context, Constant.WX_APPID);
        wxApi.registerApp(Constant.WX_APPID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "www.fenshentu.com/wxshare/index.html";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "风里雨里 , 分身兔等你 !";
        msg.description = "\n 约会神器 ! 聚会必备 !\n 我们不止是跑腿平台 !";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.share_icon);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }

    private static IWXAPI wxApi;

    //分享文字
    public static void shareText(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "www.fenshentu.com/wxshare/index.html");
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
