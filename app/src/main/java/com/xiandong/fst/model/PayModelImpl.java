package com.xiandong.fst.model;


import android.app.Activity;
import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.EWaiPayBean;
import com.xiandong.fst.model.bean.PayBean;
import com.xiandong.fst.model.bean.PayOrderBean;
import com.xiandong.fst.model.bean.RedPacketPayBean;
import com.xiandong.fst.presenter.PayPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/10
 */

public class PayModelImpl implements PayModel {

    @Override
    public void getOrderId(PayBean payBean, final Activity context, final PayPresenter payPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "faorder");
        params.addBodyParameter("come", "android");
        params.addBodyParameter("position", payBean.getPosition());
        params.addBodyParameter("pcontent", payBean.getAddress());
        params.addBodyParameter("price", payBean.getMoney());
        params.addBodyParameter("title",payBean.getTitle());
        params.addBodyParameter("time", payBean.getTime());
        params.addBodyParameter("uid", payBean.getUid());
        params.addBodyParameter("phone", payBean.getPhone());
        params.addBodyParameter("info", payBean.getDetail());
        params.addBodyParameter("type", payBean.getType() + "");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                PayOrderBean orderBean;
                switch (bean.getResult()) {
                    case 1:  // 微信 订单
                        orderBean = GsonUtil.fromJson(result, PayOrderBean.class);
                        payPresenter.getOrderIdSuccess(orderBean.getOrderid());
                        break;
                    case 2:
                        orderBean = GsonUtil.fromJson(result, PayOrderBean.class);
                        payPresenter.getOrderIdSuccess(orderBean.getOrderid());
                        break;
                    case 3:
                        payPresenter.getOrderIdSuccess("");
                        break;
                    default:
                        payPresenter.getOrderIdFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                payPresenter.getOrderIdFails(ex.getMessage());
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
    public void getRedPacketOrderId(RedPacketPayBean payBean, final PayPresenter payPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "addredbag");
        params.addBodyParameter("uid", payBean.getUid());
        params.addBodyParameter("type", payBean.getType() + "");
        params.addBodyParameter("distance", payBean.getDistance());
        params.addBodyParameter("totalfee", payBean.getTotalFee());
        params.addBodyParameter("totalcount", payBean.getTotalCount());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                PayOrderBean orderBean;
                switch (bean.getResult()) {
                    case 1:  // 微信 订单
                        orderBean = GsonUtil.fromJson(result, PayOrderBean.class);
                        payPresenter.getOrderIdSuccess(orderBean.getOrderid());
                        break;
                    case 2:
                        orderBean = GsonUtil.fromJson(result, PayOrderBean.class);
                        payPresenter.getOrderIdSuccess(orderBean.getOrderid());
                        break;
                    case 3:
                        payPresenter.getOrderIdSuccess("");
                        break;
                    default:
                        payPresenter.getOrderIdFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                payPresenter.getOrderIdFails(ex.getMessage());
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
    public void getEWaiPriceOrderId(EWaiPayBean payBean, final PayPresenter payPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "extra");
        params.addParameter("uid", AppDbManager.getUserId());
        params.addParameter("oid", payBean.getId());
        params.addParameter("money", payBean.getMoney());
        params.addParameter("type",payBean.getType());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("OrderDetailsModelImpl", result);
                AbsBaseBean absBaseBean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (absBaseBean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        PayOrderBean orderBean = GsonUtil.fromJson(result, PayOrderBean.class);
                        payPresenter.getOrderIdSuccess(orderBean.getOrderid());
                        break;
                    default:
                        payPresenter.getOrderIdFails(absBaseBean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                payPresenter.getOrderIdFails(ex.getMessage());
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