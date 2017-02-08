package com.xiandong.fst.model;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.presenter.RegisterPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/03
 */

public class RegisterModelImpl implements RegisterModel {

    @Override
    public void register(String phone, String psw, final RegisterPresenter registerPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "zhuce");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", psw);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean ab = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (ab.getResult()) {
                    case 1:
                        UserBean userBean = GsonUtil.fromJson(result, UserBean.class);
                        AppDbManager.saveUserData(userBean, true);
                        registerPresenter.onRegisterSuccess();
                        break;
                    default:
                        registerPresenter.onRegisterFails(ab.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                registerPresenter.onRegisterFails(ex.getMessage());
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