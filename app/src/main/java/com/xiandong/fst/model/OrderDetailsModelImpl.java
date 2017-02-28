package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.OrderDetailsMsgBean;
import com.xiandong.fst.presenter.OrderDetailsPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/23
 */

public class OrderDetailsModelImpl implements OrderDetailsModel {

    @Override
    public void getOrderMsg(String id, final OrderDetailsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "ordercontent");
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("OrderDetailsModelImpl", result);
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        OrderDetailsMsgBean msgBean = GsonUtil.fromJson(result, OrderDetailsMsgBean.class);
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

    public void complicateOrder(String id, final OrderDetailsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "qrorder");
        params.addParameter("uid", AppDbManager.getUserId());
        params.addParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        presenter.complicateOrderSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.complicateOrderFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.complicateOrderFails(ex.getMessage());
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
    public void cancelOrder(String id, String type ,final OrderDetailsPresenter presenter) {
        RequestParams params = null;
        switch (type){
            case "pt":
                params = new RequestParams(Constant.APIURL + "qxorder");
                break;
            case "xs":
                params = new RequestParams(Constant.APIURL + "xsorder");
                break;
            case "qz":
                params = new RequestParams(Constant.APIURL + "qxorder");
                break;
        }
        params.addParameter("id", id);
        params.addParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        presenter.cancelOrderSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.cancelOrderFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.cancelOrderFails(ex.getMessage());
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
    public void eWai(String id, String money, final OrderDetailsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "extra");
        params.addParameter("uid", AppDbManager.getUserId());
        params.addParameter("oid", id);
        params.addParameter("money", money);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("OrderDetailsModelImpl", result);
                AbsBaseBean absBaseBean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (absBaseBean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        presenter.eWaiSuccess(absBaseBean.getMessage());
                        break;
                    default:
                        presenter.eWaiFails(absBaseBean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.eWaiFails(ex.getMessage());
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