package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.ForgetPasswordPresenter;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/04
*/

public class ForgetPasswordModelImpl implements ForgetPasswordModel{

    @Override
    public void resetPassword(String psw,String phone, final ForgetPasswordPresenter forgetPasswordPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "wangjimima");
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("npassword",psw);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result,AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        forgetPasswordPresenter.resetPswSuccess(bean.getMessage());
                        break;
                    default:
                        forgetPasswordPresenter.resetPswFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                forgetPasswordPresenter.resetPswFails(ex.getMessage());
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