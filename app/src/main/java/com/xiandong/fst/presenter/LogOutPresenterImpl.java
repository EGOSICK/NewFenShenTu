package com.xiandong.fst.presenter;


import com.xiandong.fst.model.LogOutModel;
import com.xiandong.fst.model.LogOutModelImpl;
import com.xiandong.fst.view.LogOutView;

/**
 * Created by dell on 2017/01/17
 */

public class LogOutPresenterImpl implements LogOutPresenter {
    LogOutView view;
    LogOutModel model;

    public LogOutPresenterImpl(LogOutView view){
        this.view = view;
        this.model = new LogOutModelImpl();
    }


    public void logOut(){
        model.logOut(this);
    }

    @Override
    public void logOutSuccess() {
        view.logOutSuccess();
    }

    @Override
    public void logOutFails(String err) {
        view.logOutFails(err);
    }
}