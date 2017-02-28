package com.xiandong.fst.presenter;


import android.os.Handler;

import com.xiandong.fst.model.LogInModel;
import com.xiandong.fst.model.LogInModelImpl;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.LogInView;

/**
 * Created by dell on 2016/12/20
 */

public class LogInPresenterImpl implements LogInPresenter {
    private LogInModel logInModel;
    private LogInView logInView;

    public LogInPresenterImpl(LogInView logInView) {
        this.logInView = logInView;
        this.logInModel = new LogInModelImpl();
    }

    public void logIn() {
        String name = logInView.getName();
        String psw = logInView.getPassWord();

        if (!StringUtil.isTelPhone(name)) {
            logInView.showToast("手机号码输入有误");
            return;
        }

        if (StringUtil.isEmpty(psw) || psw.length() < 4) {
            logInView.showToast("请输入4位及以上的密码");
            return;
        }

        logInModel.logIn(name, psw, this);
    }

    public void transferWXLogIn(){
        logInModel.transferWXLogIn(this);
    }

    public void wxLogIn(){
        logInModel.wxLogIn(this);
    }

    public void wxBingDing(){
        logInModel.wxBingDing(this);
    }

    @Override
    public void onSuccess(Object o) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logInView.success();
            }
        }, 1000);
    }

    @Override
    public void onFailure() {
        logInView.showToast("请求失败");
    }

    @Override
    public void onError(String message) {
        logInView.showToast(message);
    }

    @Override
    public void onWXLogInSuccess(String nickName) {
        logInView.wxLogInSuccess();
    }

    @Override
    public void onWXLogInFailure(String err) {
        logInView.wxLogInFails(err);
    }

    @Override
    public void onWXLogInBingDingPhone() {
        logInView.wxLogInBingDingPhone();
    }
}