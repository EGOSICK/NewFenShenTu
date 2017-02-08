package com.xiandong.fst.view;

/**
* Created by dell on 2016/12/20
*/

public interface LogInView<T>{

    void success();

    void showToast(String s);

    String getName();

    String getPassWord();

    void wxLogInSuccess();

    void wxLogInFails(String err);

    void wxLogInBingDingPhone();

}