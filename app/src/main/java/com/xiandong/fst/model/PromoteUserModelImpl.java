package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.PromoteUserBean;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.presenter.PromoteUserPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/18
 */

public class PromoteUserModelImpl implements PromoteUserModel {

    @Override
    public void getPromoteUser(final PromoteUserPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "show");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        UserBean userBean = GsonUtil.fromJson(result, UserBean.class);
                        AppDbManager.saveUserData(userBean, true);
                        presenter.getPromoteUserSuccess();
                        break;
                    default:
                        presenter.getPromoteUserFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getPromoteUserFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        RequestParams params1 = new RequestParams(Constant.APIURL+"tuiguang");
        params1.addBodyParameter("uid",AppDbManager.getLastUser().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PromoteUserBean bean = GsonUtil.fromJson(result , PromoteUserBean.class);
                presenter.getPromoteUserSuccess(bean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getPromoteUserFails(ex.getMessage());
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