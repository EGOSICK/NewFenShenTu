package com.xiandong.fst.presenter;


import com.xiandong.fst.model.OrderDetailsModel;
import com.xiandong.fst.model.OrderDetailsModelImpl;
import com.xiandong.fst.model.bean.OrderDetailsMsgBean;
import com.xiandong.fst.view.OrderDetailsView;

/**
 * Created by dell on 2017/01/23
 */

public class OrderDetailsPresenterImpl implements OrderDetailsPresenter {
    OrderDetailsModel model;
    OrderDetailsView view;

    public OrderDetailsPresenterImpl(OrderDetailsView view) {
        this.view = view;
        this.model = new OrderDetailsModelImpl();
    }

    public void getOrderMsg(String id) {
        model.getOrderMsg(id, this);
    }

    public void complicateOrder(String id) {
        model.complicateOrder(id, this);
    }

    public void cancelOrder(String id , String type){
        model.cancelOrder(id , type , this);
    }

    public void eWai(String id, String money) {
        model.eWai(id, money, this);
    }

    @Override
    public void getOrderMsgSuccess(OrderDetailsMsgBean bean) {
        view.getOrderMsgSuccess(bean);
    }

    @Override
    public void getOrderMsgFails(String err) {
        view.getOrderMsgFails(err);
    }

    @Override
    public void complicateOrderSuccess(String msg) {
        view.complicateOrderSuccess(msg);
    }

    @Override
    public void complicateOrderFails(String err) {
        view.complicateOrderFails(err);
    }

    @Override
    public void cancelOrderFails(String err) {
        view.cancelOrderFails(err);
    }

    @Override
    public void cancelOrderSuccess(String msg) {
        view.cancelOrderSuccess(msg);
    }

    @Override
    public void eWaiSuccess(String msg) {
        view.eWaiSuccess(msg);
    }

    @Override
    public void eWaiFails(String err) {
        view.eWaiFails(err);
    }
}