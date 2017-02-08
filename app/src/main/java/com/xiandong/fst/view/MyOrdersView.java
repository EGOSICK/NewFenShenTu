package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.MyOrdersBean;

/**
* Created by dell on 2017/01/18
*/

public interface MyOrdersView{
    void getMyOrdersFails(String err);

    void getMyOrdersSuccess(MyOrdersBean myOrdersBean);

    void completeOrderFails(String err);

    void completeOrderSuccess();
}