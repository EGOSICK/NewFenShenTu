package com.xiandong.fst.model;

import com.xiandong.fst.presenter.MyOrdersPresenter;

/**
* Created by dell on 2017/01/18
*/

public interface MyOrdersModel{

    void getMyOrders(MyOrdersPresenter presenter);

    void completeOrder(String id  , MyOrdersPresenter presenter);
}