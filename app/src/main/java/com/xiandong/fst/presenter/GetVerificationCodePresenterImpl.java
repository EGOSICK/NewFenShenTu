package com.xiandong.fst.presenter;


import android.util.Log;

import com.xiandong.fst.model.GetVerificationCodeModel;
import com.xiandong.fst.model.GetVerificationCodeModelImpl;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.GetVerificationCodeView;

/**
 * Created by dell on 2017/01/04
 */

public class GetVerificationCodePresenterImpl implements GetVerificationCodePresenter {

    private GetVerificationCodeModel getVerificationCodeModel;
    private GetVerificationCodeView getVerificationCodeView;
    private String code = "";

    public GetVerificationCodePresenterImpl(GetVerificationCodeView getVerificationCodeView) {
        this.getVerificationCodeModel = new GetVerificationCodeModelImpl();
        this.getVerificationCodeView = getVerificationCodeView;
    }

    public void getCodeMsg(String phone) {
        if (StringUtil.isTelPhone(phone)) {
            getVerificationCodeModel.getCode(phone, this);
        } else {
            getVerificationCodeView.getCodeFails("手机号码输入有误");
        }
    }

    public boolean verificationCode(String code){
        if (StringUtil.isEmpty(code) || StringUtil.isEmpty(this.code))
            return false;
        if (StringUtil.isEquals(code,this.code))
            return true;
        return false;
    }

    @Override
    public void onSendCodeSuccess(String msg, String code) {
        getVerificationCodeView.getCodeSuccess(msg);
        this.code = code;
        Log.d("RegisterPresenterImpl", code);
    }

    @Override
    public void onSendCodeFails(String err) {
        getVerificationCodeView.getCodeFails(err);
    }
}