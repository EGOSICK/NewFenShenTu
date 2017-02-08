package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.NewFriendsBean;
import com.xiandong.fst.presenter.GetNewFriendsPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/19
 */

public class GetNewFriendsModelImpl implements GetNewFriendsModel {

    @Override
    public void getNewFriends(final GetNewFriendsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showadduser");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        NewFriendsBean friendsBean = GsonUtil.fromJson(result , NewFriendsBean.class);
                        presenter.getNewFriendsSuccess(friendsBean);
                        break;
                    default:
                        presenter.getNewFriendsFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getNewFriendsFails(ex.getMessage());
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
    public void agreedAddFriends(String userId, final GetNewFriendsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"querenadd");
        params.addBodyParameter("uid",AppDbManager.getUserId());
        params.addBodyParameter("id",userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean absBaseBean = GsonUtil.fromJson(result ,AbsBaseBean.class);
                switch (absBaseBean.getResult()){
                    case 1:
                        presenter.agreedAddFriendsSuccess();
                        break;
                    default:
                        presenter.agreedAddFriendsFails(absBaseBean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.agreedAddFriendsFails(ex.getMessage());
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