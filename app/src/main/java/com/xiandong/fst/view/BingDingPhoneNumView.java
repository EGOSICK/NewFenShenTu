package com.xiandong.fst.view;

/**
* Created by dell on 2017/01/04
*/

public interface BingDingPhoneNumView{
    void bingDingSuccess(String msg);

    void showErrMessage(String err);

    void getCodeMsgSuccess(String msg);

    void getCodeMsgFails(String err);

}