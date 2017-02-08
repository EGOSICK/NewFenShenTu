package com.xiandong.fst.presenter;


import com.xiandong.fst.model.ForgetPasswordModel;
import com.xiandong.fst.model.ForgetPasswordModelImpl;
import com.xiandong.fst.view.ForgetPasswordView;
import com.xiandong.fst.view.GetVerificationCodeView;

/**
 * Created by dell on 2017/01/04
 */

public class ForgetPasswordPresenterImpl extends GetVerificationCodePresenterImpl
        implements ForgetPasswordPresenter {
    ForgetPasswordModel forgetPasswordModel;
    ForgetPasswordView forgetPasswordView;

    public ForgetPasswordPresenterImpl(GetVerificationCodeView getVerificationCodeView,
                                       ForgetPasswordView forgetPasswordView) {
        super(getVerificationCodeView);

        this.forgetPasswordView = forgetPasswordView;
        this.forgetPasswordModel = new ForgetPasswordModelImpl();
    }

    public void resetPassword(String psw, String phone) {
        forgetPasswordModel.resetPassword(psw, phone, this);
    }

    @Override
    public void resetPswSuccess(String msg) {
        forgetPasswordView.resetPswSuccess(msg);
    }

    @Override
    public void resetPswFails(String err) {
        forgetPasswordView.resetPswFails(err);
    }
}