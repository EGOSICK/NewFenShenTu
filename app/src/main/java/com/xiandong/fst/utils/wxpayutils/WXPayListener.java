package com.xiandong.fst.utils.wxpayutils;

/**
 * Created by dell on 2017/1/10.
 */

public interface WXPayListener {
    void wxPaySuccess();
    void wxPayFails(String err);
}
