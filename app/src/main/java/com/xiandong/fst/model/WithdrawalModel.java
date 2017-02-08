package com.xiandong.fst.model;

import com.xiandong.fst.presenter.WithdrawalPresenter;

/**
* Created by dell on 2017/01/20
*/

public interface WithdrawalModel{

    void withdrawal(String zh, String mz, String sj, String je,String psw , WithdrawalPresenter presenter);

    void creatPayPsw(String psw , WithdrawalPresenter presenter);

}