package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.WithdrawalPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/20
 */

public class WithdrawalModelImpl implements WithdrawalModel {

    @Override
    public void withdrawal(String zh, String mz, String sj, String je, String psw, final WithdrawalPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "tixian");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("zfbid", zh);
        params.addBodyParameter("money", je);
        params.addBodyParameter("realname", mz);
        params.addBodyParameter("phonenumber", sj);
        params.addBodyParameter("zfpassword", psw);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.withdrawalSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.withdrawalFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.withdrawalFails(ex.getMessage());
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
    public void creatPayPsw(String psw, final WithdrawalPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "xiugai");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("zfpassword", psw);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.setPayPswSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.withdrawalFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.withdrawalFails(ex.getMessage());
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