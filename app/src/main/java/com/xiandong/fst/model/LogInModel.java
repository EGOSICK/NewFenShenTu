package com.xiandong.fst.model;

import com.xiandong.fst.presenter.LogInPresenter;
import com.xiandong.fst.presenter.LogInPresenterImpl;

/**
* Created by dell on 2016/12/20
*/

public interface LogInModel{

    void logIn(String name , String passWord , LogInPresenter logInPresenter);

    void transferWXLogIn(LogInPresenter logInPresenter);

    void wxLogIn(LogInPresenter logInPresenter);

    void wxBingDing(LogInPresenter logInPresenter);
}