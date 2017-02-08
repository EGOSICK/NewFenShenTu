package com.xiandong.fst.presenter;


import com.xiandong.fst.model.AcceptOrderModel;
import com.xiandong.fst.model.AcceptOrderModelImpl;
import com.xiandong.fst.view.AcceptOrderView;

/**
 * Created by dell on 2017/01/20
 */

public class AcceptOrderPresenterImpl implements AcceptOrderPresenter {

    AcceptOrderModel model;
    AcceptOrderView view;

    public AcceptOrderPresenterImpl(AcceptOrderView view) {
        this.view = view;
        this.model = new AcceptOrderModelImpl();
    }

    public void acceptOrder(String id) {
        model.acceptOrder(id, this);
    }

    @Override
    public void acceptOrderSuccess(String msg) {
        view.acceptOrderSuccess(msg);
    }

    @Override
    public void acceptOrderFails(String err) {
        view.acceptOrderFails(err);
    }
}