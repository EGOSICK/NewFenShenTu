package com.xiandong.fst.presenter;


import com.xiandong.fst.model.MyOrdersModel;
import com.xiandong.fst.model.MyOrdersModelImpl;
import com.xiandong.fst.model.bean.MyOrdersBean;
import com.xiandong.fst.view.MyOrdersView;

/**
 * Created by dell on 2017/01/18
 */

public class MyOrdersPresenterImpl implements MyOrdersPresenter {
    MyOrdersModel model;
    MyOrdersView view;

    public MyOrdersPresenterImpl(MyOrdersView view) {
        this.view = view;
        this.model = new MyOrdersModelImpl();
    }

    public void getMyOrders() {
        model.getMyOrders(this);
    }

    public void completeOrder(String id) {
        model.completeOrder(id, this);
    }

    @Override
    public void getMyOrdersFails(String err) {
        view.getMyOrdersFails(err);
    }

    @Override
    public void getMyOrdersSuccess(MyOrdersBean myOrdersBean) {
        view.getMyOrdersSuccess(myOrdersBean);
    }

    @Override
    public void completeOrderFails(String err) {
        view.completeOrderFails(err);
    }

    @Override
    public void completeOrderSuccess() {
        view.completeOrderSuccess();
    }
}