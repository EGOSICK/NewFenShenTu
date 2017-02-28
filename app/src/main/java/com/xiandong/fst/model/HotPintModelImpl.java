package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.HotPintDetailBean;
import com.xiandong.fst.presenter.HotPintPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/22
 */

public class HotPintModelImpl implements HotPintModel {

    @Override
    public void getHotPintMsg(String id, final HotPintPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showforum");
        params.addBodyParameter("id", id);
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                HotPintDetailBean bean = GsonUtil.fromJson(result, HotPintDetailBean.class);
                presenter.getHotPintSuccess(bean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.fetHotPintFails(ex.getMessage());
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
    public void huiFu(String msg, String pid, String position, final HotPintPresenter presenter) {
        RequestParams params;
        switch (pid) {
            case "0":
                params = new RequestParams(Constant.APIURL + "addforum");
                break;
            default:
                params = new RequestParams(Constant.APIURL + "hfforum");
                break;
        }

        params.addBodyParameter("pid", pid);
        params.addBodyParameter("content", msg);
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("position", position);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                presenter.huiFuSuccess();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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