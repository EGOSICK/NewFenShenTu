package com.xiandong.fst.presenter;


import android.app.Activity;
import android.content.Context;

import com.xiandong.fst.model.PayModel;
import com.xiandong.fst.model.PayModelImpl;
import com.xiandong.fst.model.bean.EWaiPayBean;
import com.xiandong.fst.model.bean.PayBean;
import com.xiandong.fst.model.bean.RedPacketPayBean;
import com.xiandong.fst.view.PayView;

/**
 * Created by dell on 2017/01/10
 */

public class PayPresenterImpl implements PayPresenter {
    PayModel model;
    PayView view;

    public PayPresenterImpl(PayView view) {
        this.view = view;
        this.model = new PayModelImpl();
    }

    public void getOrderId(PayBean payBean, Activity context) {
        model.getOrderId(payBean, context, this);
    }

    public void getRedPacketOrderId(RedPacketPayBean payBean){
        model.getRedPacketOrderId(payBean, this);
    }

    public void getEWaiOrderId(EWaiPayBean payBean){
        model.getEWaiPriceOrderId(payBean , this);
    }

    @Override
    public void getOrderIdFails(String err) {
        view.getOrderIdFails(err);
    }

    @Override
    public void getOrderIdSuccess(String orderId) {
        view.getOrderIdSuccess(orderId);
    }
}