package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.MyOrdersBean;

/**
 * Created by dell on 2017/1/18.
 */

public interface MyOrdersPresenter {
    void getMyOrdersFails(String err);

    void getMyOrdersSuccess(MyOrdersBean myOrdersBean);

    void completeOrderFails(String err);

    void completeOrderSuccess();
}
