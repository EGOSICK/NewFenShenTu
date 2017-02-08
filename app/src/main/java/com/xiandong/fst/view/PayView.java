package com.xiandong.fst.view;

/**
* Created by dell on 2017/01/10
*/

public interface PayView{

//    void paySuccess(String msg);
//
//    void payFails(String err);

    void getOrderIdSuccess(String orderId);

    void getOrderIdFails(String err);
}