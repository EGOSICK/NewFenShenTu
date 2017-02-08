package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.CodeBean;
import com.xiandong.fst.presenter.GetVerificationCodePresenter;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/04
*/

public class GetVerificationCodeModelImpl implements GetVerificationCodeModel{

    @Override
    public void getCode(String phone, final GetVerificationCodePresenter getVerificationCodePresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "yzm");
        params.addBodyParameter("phone", phone);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                CodeBean codeBean = GsonUtil.fromJson(result, CodeBean.class);
                switch (codeBean.getResult()) {
                    case 1:
                        getVerificationCodePresenter.onSendCodeSuccess(codeBean.getMessage(), String.valueOf(codeBean.getCode()));
                        break;
                    default:
                        getVerificationCodePresenter.onSendCodeFails(codeBean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                getVerificationCodePresenter.onSendCodeFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}