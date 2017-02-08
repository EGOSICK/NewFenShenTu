package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.AcceptOrderPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/20
 */

public class AcceptOrderModelImpl implements AcceptOrderModel {

    @Override
    public void acceptOrder(String id, final AcceptOrderPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "jieorder");
        params.addBodyParameter("user_id", AppDbManager.getUserId());
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.acceptOrderSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.acceptOrderFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.acceptOrderFails(ex.getMessage());
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