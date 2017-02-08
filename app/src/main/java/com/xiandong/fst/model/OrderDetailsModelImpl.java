package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.OrderDetailsMsgBean;
import com.xiandong.fst.presenter.OrderDetailsPresenter;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/23
*/

public class OrderDetailsModelImpl implements OrderDetailsModel{

    @Override
    public void getOrderMsg(String id, final OrderDetailsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"ordercontent");
        params.addBodyParameter("id",id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        OrderDetailsMsgBean msgBean = GsonUtil.fromJson(result ,OrderDetailsMsgBean.class);
                        presenter.getOrderMsgSuccess(msgBean);
                        break;
                    default:
                        presenter.getOrderMsgFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getOrderMsgFails(ex.getMessage());
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