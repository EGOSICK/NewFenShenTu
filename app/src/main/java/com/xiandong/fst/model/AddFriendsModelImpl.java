package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.AddFriendsPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/19
 */

public class AddFriendsModelImpl implements AddFriendsModel {

    @Override
    public void addFriends(String userId, final AddFriendsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "adduser");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("user_id", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        presenter.addFriendsSuccess();
                        break;
                    default:
                        presenter.addFriendsFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.addFriendsFails(ex.getMessage());
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