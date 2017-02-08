package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.CodeBean;
import com.xiandong.fst.presenter.BingDingPhoneNumPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/04
 */

public class BingDingPhoneNumModelImpl implements BingDingPhoneNumModel {

    /***
     * 验证手机号码是否可用
     * @param phone 手机号码
     */
    @Override
    public void bingDingPhoneNum(final String phone, final String psw, final BingDingPhoneNumPresenter bingDingPhoneNumPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "yanzheng");
        params.addBodyParameter("phone", phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        bingDingPhone(phone, psw, bingDingPhoneNumPresenter);
                        break;
                    default:
                        bingDingPhoneNumPresenter.bingDingFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                bingDingPhoneNumPresenter.bingDingFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void sendCodeMessage(String phone, final BingDingPhoneNumPresenter bingDingPhoneNumPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "yzm");
        params.addBodyParameter("phone", phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CodeBean codeBean = GsonUtil.fromJson(result, CodeBean.class);
                switch (codeBean.getResult()) {
                    case 1:
                        bingDingPhoneNumPresenter.sendCodeMsgSuccess(codeBean.getMessage(), String.valueOf(codeBean.getCode()));
                        break;
                    default:
                        bingDingPhoneNumPresenter.sendCodeMsgFails(codeBean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                bingDingPhoneNumPresenter.sendCodeMsgFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void bingDingPhone(String phone, String psw, final BingDingPhoneNumPresenter bingDingPhoneNumPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "xiugai");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
//        params.addBodyParameter("nicheng",);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", psw);
//        params.addBodyParameter("accesstoken",);
//        params.addBodyParameter("openid",);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result,AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        bingDingPhoneNumPresenter.bingDingSuccess(bean.getMessage());
                        AppDbManager.saveLogInData(true);
                        break;
                    default:
                        bingDingPhoneNumPresenter.bingDingFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                bingDingPhoneNumPresenter.bingDingFails(ex.getMessage());
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