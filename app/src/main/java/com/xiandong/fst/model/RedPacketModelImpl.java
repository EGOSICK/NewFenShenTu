package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.RedPacketBean;
import com.xiandong.fst.presenter.RedPacketPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/21
 */

public class RedPacketModelImpl implements RedPacketModel {

    @Override
    public void loadRedPacket(final RedPacketPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showredbag");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        RedPacketBean redPacketBean = GsonUtil.fromJson(result ,RedPacketBean.class);
                        presenter.loadRedPacketSuccess(redPacketBean.getRedbag());
                        break;
                    default:
                        presenter.loadRedPacketFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.loadRedPacketFails(ex.getMessage());
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