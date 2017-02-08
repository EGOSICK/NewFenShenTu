package com.xiandong.fst.model;

import com.xiandong.fst.presenter.ForgetPasswordPresenter;

/**
* Created by dell on 2017/01/04
*/

public interface ForgetPasswordModel{
    void resetPassword(String psw ,String phone, ForgetPasswordPresenter forgetPasswordPresenter);
}