package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/20.
 */

public interface WithdrawalPresenter {
    void withdrawalSuccess(String msg);
    void withdrawalFails(String err);

    void setPayPswSuccess(String msg);
}
