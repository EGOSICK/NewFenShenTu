package com.xiandong.fst.model;

import com.xiandong.fst.presenter.RegisterPresenter;

/**
 * Created by dell on 2017/01/03
 */

public interface RegisterModel {

    void register(String phone , String psw , RegisterPresenter registerPresenter);

}