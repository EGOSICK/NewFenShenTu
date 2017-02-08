package com.xiandong.fst.presenter;

/**
 * Created by dell on 2016/12/20.
 */

public interface LogInPresenter<T> {

    void onSuccess(T t);

    void onFailure();

    void onError(String message);

    void onWXLogInSuccess(String nickName);

    void onWXLogInFailure(String err);

    void onWXLogInBingDingPhone();
}
