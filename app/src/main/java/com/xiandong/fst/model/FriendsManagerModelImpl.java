package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.FriendsManagerPresenter;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/20
 */

public class FriendsManagerModelImpl implements FriendsManagerModel {

    @Override
    public void changeFriendBz(String id, final String bz, final FriendsManagerPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "editbz");
        params.addBodyParameter("id", id);
        params.addBodyParameter("bz", bz);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.changeFriendMessageSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.changeFriendsMessageFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.changeFriendsMessageFails(ex.getMessage());
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
    public void deleteFriend(String id, final FriendsManagerPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "deluser");
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.deleteFriendSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.changeFriendsMessageFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.changeFriendsMessageFails(ex.getMessage());
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
    public void shieldingPosition(int shi, String id, final FriendsManagerPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showposition");
        params.addBodyParameter("show",shi+"");
        params.addBodyParameter("id",id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.changeFriendsMessageFails(ex.getMessage());
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