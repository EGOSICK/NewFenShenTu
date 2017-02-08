package com.xiandong.fst.application;

import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;

/**
 * App 常量
 */

public class Constant {
    public static final String APIURL = "http://www.fenshentu.com/mobile.php/Index/index/act/";
    // 基础执行时间
    public static final long EXECUTIONTIME = 250;
    // 请求成功
    public static final int HTTPSUCCESS = 1;
    // 微信第三方登陆需要提供AppID和AppSecret
    public static String WX_CODE = "";
    public static final String WX_APPID = "wx989b2a078f4148ec";
    public static final String WX_APPSECRET = "04a7f3977060cd8cb4087ac6150a2d3b";
    public static final String WXLOGINURL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String WXLOGUSERMSGURL = "https://api.weixin.qq.com/sns/userinfo";
    // 微信第三方支付需要提供AppID和AppKey和MchId
    //商户ID
    public static final String MCH_ID = "1366014802";
    //应用签名 MD5值
    public static final String API_KEY = "4E27B9ABD05D4229B91BF1747FB4C249";
    // 微信支付通知接口
    public static final String NOTIFAURL = "http://www.fenshentu.com/app.php/Index/wxpay";

    // 图片库
    public static final int NONE = 0;        //
    public static final int PHOTOHRAPH = 1;  // 拍照
    public static final int PHOTOZOOM = 2;   // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";                        //图片格式
    public static final File sImgPath = Environment.getExternalStorageDirectory(); //照片存储路径
    public static final String sImgname = "temp.jpg";                                //照片名称
    public static final String sImgSeparator = "/";                                  //分隔符
    public final static int USERIMGCODE = 0x0014;

    public static final int ZFBPAY = 1;
    public static final int WXPAY = 2;
    public static final int YUEPAY = 3;

    public static final String REDPACKETPAY = "redPacket";
    public static final String BILLINGPAY = "billing";

    //通讯录
    //电话号码
    public static final int PHONES_NUMBER_INDEX = 1;
    //联系人显示名称
    public static final int PHONES_DISPLAY_NAME_INDEX = 0;
    //获取库Phone表字段
    public static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    public static class MarkerType{
        public static final String REDPACKET = "redPacket";
        public static final String FORUM = "forum";
        public static final String REDPACKETCOME = "fenshentu";
    }

}
