package com.xiandong.fst.model;

import com.xiandong.fst.presenter.BingDingPhoneNumPresenter;

/**
 * Created by dell on 2017/01/04
 */

public interface BingDingPhoneNumModel {

    void bingDingPhoneNum(String phone, String psw, BingDingPhoneNumPresenter bingDingPhoneNumPresenter);

    void sendCodeMessage(String phone, BingDingPhoneNumPresenter bingDingPhoneNumPresenter);
}