package com.xiandong.fst.utils.alipayutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 支付宝支付 帮助类
 */
public class AliPayUtils {
    private Context context;
    private AliPayListener listener;
    public AliPayUtils(Context context) {
        this.context = context;
    }

    // 商户PID
    public static final String PARTNER = "2088421431361755";
    // 商户收款账号
    public static final String SELLER = "zhifubaoshangjia@fenshentu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICXwIBAAKBgQC2aa0BpD/5dI4X3tbIs5AaYjPHkmXnUr0vyFlLL+S4X8jWjN8aqjjOUPTJPOr2PHzbscrJNKiEuLlESUr0BQQkkpNVadTFOqsw3ZsZbPLoYhmhht5Xn6Ra0P9mDhqiudUw767AkTSA2iPqoAGmpvkp0Y9XJigegv5Fr9ZM+TtaqwIDAQABAoGBAJ7AYXzoQXUMZw2HNk39WBBeHso1MtKbQ2LubiftSpTbZ6Kxfwlp035bHm50wdgC1HcuyFlLps27JiGz17N/GhZx2JFm3iuYhJCRbyNg4ZQNxP/9BZHoEwl2mQde+U0BNn2v3FkrquSJ12j6yeVOIsvd1P+RXPdE+fJHs7O7f8ihAkEA3ueRL1TTZ2dZtxErAmAVJmkYter5RTw3/lyM546BwZFFRBTcrLagmwzjJ0sG55Y9FP3cqBYFcq/AJu1Rnj/OxQJBANF/DCvHn0fWOp9QVSWFEYj1AGDAdQDQcHuM1A0/bmRrfsV2jY3B+RKsTOyODGuh7sxmQgksLn3WBURfVZf5Gq8CQQCMBwirpSwQ31n6qOxN17IxqP/++BeTqNq8DcPuixV0ViAfPsPKNAf8n0HrozQ5M6Y3k6qUZRETFNkvGxKUyRzJAkEAoZkn0Ul9oxMsSyHTXErGGOgsL3Wli0g9y//4vnNUex+0PStSy6t5rp68A6kbCZdytbaXb63l+ukxTOlhBAaCPwJBAKwNO0uEhagP2MO+u8pXq/wD8DOyxBdlAzxqD+ipkTIOW4vIN7SP8mL5CsWKcohsAxRa8ZbF5HrvviC4dSq/qpU=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        listener.aliPaySuccess();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            listener.aliPayFails("支付失败"+resultInfo);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(String name, String body, String price, String orderId , AliPayListener listener) {
        this.listener = listener;
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(context).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        String orderInfo = getOrderInfo(name, body, price, orderId);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price, String orderId) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://www.fenshentu.com/app.php/Index/alipay" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

//        orderId += "&goods_type=\"0\"";
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//        orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

//    /**
//     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
//     */
//    private String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


//    /***
//     * 自定义Toast
//     *
//     * @param is 成功失败
//     * @param tm 提示消息
//     */
//    protected void customToast(boolean is, String tm) {
//        int res;
//        if (is) {
//            res = R.drawable.toast_ture;
//        } else {
//            res = R.drawable.toast_error;
//        }
//        Toast toast = new Toast(context);
//        View tv = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
//        ImageView img = (ImageView) tv.findViewById(R.id.toastImg);
//        img.setImageResource(res);
//        TextView msg = (TextView) tv.findViewById(R.id.toastMsg);
//        msg.setText(tm);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(tv);
//        toast.show();
//    }
}
