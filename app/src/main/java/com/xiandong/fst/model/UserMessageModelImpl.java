package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.presenter.UserMessagePresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/18
*/

public class UserMessageModelImpl implements UserMessageModel{

    @Override
    public void getUserMessage(final UserMessagePresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "show");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        UserBean userBean = GsonUtil.fromJson(result , UserBean.class);
                        AppDbManager.saveUserData(userBean,true);
                        presenter.getUserMessageSuccess(userBean);
                        break;
                    default:
                        presenter.getUserMessageFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getUserMessageFails(ex.getMessage());
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