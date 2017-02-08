package com.xiandong.fst.presenter;


import android.os.Handler;
import android.util.Log;

import com.xiandong.fst.model.GetVerificationCodeModel;
import com.xiandong.fst.model.GetVerificationCodeModelImpl;
import com.xiandong.fst.model.RegisterModel;
import com.xiandong.fst.model.RegisterModelImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.GetVerificationCodeView;
import com.xiandong.fst.view.RegisterView;

/**
 * Created by dell on 2017/01/03
 */

public class RegisterPresenterImpl extends GetVerificationCodePresenterImpl implements RegisterPresenter {
    private RegisterModel registerModel;
    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterView registerView, GetVerificationCodeView getVerificationCodeView) {
        super(getVerificationCodeView);
        this.registerModel = new RegisterModelImpl();
        this.registerView = registerView;
    }

    public void register(String phone, String code, String psw, boolean isCheckBox) {
        if (!StringUtil.isEmpty(phone)) {
            if (!StringUtil.isEmpty(psw)) {
                if (StringUtil.isTelPhone(phone)) {
                    if (verificationCode(code)) {
                        if (isCheckBox) {
                            registerModel.register(phone, psw, this);
                        } else {
                            registerView.registerFails("请同意用户协议");
                        }
                    } else {
                        registerView.registerFails("验证码输入有误");
                    }
                } else {
                    registerView.registerFails("手机号码输入有误");
                }
            } else {
                registerView.registerFails("请输入密码");
            }
        } else {
            registerView.registerFails("请输入手机号码");
        }
    }

    @Override
    public void onRegisterSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerView.registerSuccess();
            }
        }, 1000);
    }

    @Override
    public void onRegisterFails(String s) {
        registerView.registerFails(s);
    }
}