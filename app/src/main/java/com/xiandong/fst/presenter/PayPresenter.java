package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/10.
 */

public interface PayPresenter {

//    void paySuccess(String msg);
//
//    void payFails(String err);

    void getOrderIdFails(String err);

    void getOrderIdSuccess(String orderId);

}
