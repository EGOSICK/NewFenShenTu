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

    public OrderDetailsPresenterImpl(OrderDetailsView view){
        this.view = view;
        this.model = new OrderDetailsModelImpl();
    }

    public void getOrderMsg(String id){
        model.getOrderMsg(id, this);
    }


    @Override
    public void getOrderMsgSuccess(OrderDetailsMsgBean bean) {
        view.getOrderMsgSuccess(bean);
    }

    @Override
    public void getOrderMsgFails(String err) {
        view.getOrderMsgFails(err);
    }
}