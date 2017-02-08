package com.xiandong.fst.utils.wxpayutils;

/**
 * Created by dell on 2017/1/10.
 */

public class WXPayListenerManger {

    private WXPayListener wxPayListener;
    private static WXPayListenerManger listenerManger;

    public static WXPayListenerManger getInstance() {
        if (listenerManger == null) {
            listenerManger = new WXPayListenerManger();
        }
        return listenerManger;
    }

    public void regisnListener(WXPayListener listener) {
        wxPayListener = listener;
    }

    public void paySuccess() {
        wxPayListener.wxPaySuccess();
    }

    public void payFails(String err) {
        wxPayListener.wxPayFails(err);
    }
}
