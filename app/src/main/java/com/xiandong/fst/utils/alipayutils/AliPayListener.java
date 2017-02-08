package com.xiandong.fst.utils.alipayutils;

/**
 * Created by dell on 2017/1/10.
 */

public interface AliPayListener {
    void aliPaySuccess();
    void aliPayFails(String err);
}
