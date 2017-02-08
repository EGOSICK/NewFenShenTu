package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/4.
 */

public interface BingDingPhoneNumPresenter {

    void bingDingSuccess(String msg);

    void bingDingFails(String err);

    void sendCodeMsgSuccess(String msg ,String code);

    void sendCodeMsgFails(String err);

}
