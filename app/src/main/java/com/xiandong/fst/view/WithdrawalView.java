package com.xiandong.fst.view;

/**
* Created by dell on 2017/01/20
*/

public interface WithdrawalView{
    void withdrawalSuccess(String msg);
    void withdrawalFails(String err);
    void setPayPswSuccess(String msg);
}