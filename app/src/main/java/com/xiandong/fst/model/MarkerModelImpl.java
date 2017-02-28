package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.MarkerPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/21
 */

public class MarkerModelImpl implements MarkerModel {

    @Override
    public void getRedPacketMsg(final String come, String id, final String money,
                                final String address , final MarkerPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "grab");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                presenter.getRedPacketSuccess(bean.getResult(), come, money ,address);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getRedPacketFails(ex.getMessage());
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
    public void grabRedPacket(String id, final MarkerPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "grabredbag");
        params.addBodyParameter("id",id);
        params.addBodyParameter("uid",AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        presenter.grabRedPacketSuccess();
                        break;
                    default:
                        presenter.grabRedPacketFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.grabRedPacketFails(ex.getMessage());
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