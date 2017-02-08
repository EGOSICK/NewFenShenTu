package com.xiandong.fst.model;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.MyOrdersBean;
import com.xiandong.fst.presenter.MyOrdersPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/18
*/
public class MyOrdersModelImpl implements MyOrdersModel{

    @Override
    public void getMyOrders(final MyOrdersPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"myorder");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result ,AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        MyOrdersBean myOrdersBean = GsonUtil.fromJson(result , MyOrdersBean.class);
                        presenter.getMyOrdersSuccess(myOrdersBean);
                        break;
                    default:
                        presenter.getMyOrdersFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getMyOrdersFails(ex.getMessage());
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
    public void completeOrder(String id, final MyOrdersPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"qrorder");
        params.addBodyParameter("uid",AppDbManager.getUserId());
        params.addBodyParameter("id",id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        presenter.completeOrderSuccess();
                        break;
                    default:
                        presenter.completeOrderFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.completeOrderFails(ex.getMessage());
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