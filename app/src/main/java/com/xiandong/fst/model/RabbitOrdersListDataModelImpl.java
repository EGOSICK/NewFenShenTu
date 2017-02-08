package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.presenter.RabbitOrdersListDataPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/09
 */

public class RabbitOrdersListDataModelImpl implements RabbitOrdersListDataModel {

    @Override
    public void getRabbitOrdersListData(String sort, final RabbitOrdersListDataPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "order");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
        params.addBodyParameter("sort", sort);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("RabbitOrdersListDataMod", result);
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        OrderListBean listBean = GsonUtil.fromJson(result, OrderListBean.class);
                        if (listBean.getOrder() != null && listBean.getOrder().size() > 0) {
                            presenter.getRabbitOrdersListDataSuccess(listBean.getOrder());
                        } else {
                            presenter.getRabbitOrdersListDataFails("无订单");
                        }
                        break;
                    default:
                        presenter.getRabbitOrdersListDataFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getRabbitOrdersListDataFails(ex.getMessage());
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