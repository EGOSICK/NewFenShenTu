package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.LogOutPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/17
*/

public class LogOutModelImpl implements LogOutModel{

    @Override
    public void logOut(final LogOutPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"logout");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result ,AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        presenter.logOutSuccess();
                        AppDbManager.saveLogInData(false);
                        break;
                    default:
                        presenter.logOutFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.logOutFails(ex.getMessage());
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