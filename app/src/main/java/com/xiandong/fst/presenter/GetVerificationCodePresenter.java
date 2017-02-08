package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/4.
 */

public interface GetVerificationCodePresenter {

    void onSendCodeSuccess(String msg,String code);

    void onSendCodeFails(String err);
}
