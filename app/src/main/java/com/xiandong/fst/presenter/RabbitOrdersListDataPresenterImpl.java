package com.xiandong.fst.presenter;


import com.xiandong.fst.model.RabbitOrdersListDataModel;
import com.xiandong.fst.model.RabbitOrdersListDataModelImpl;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.view.RabbitOrdersListDataView;

import java.util.List;

/**
* Created by dell on 2017/01/09
*/

public class RabbitOrdersListDataPresenterImpl implements RabbitOrdersListDataPresenter{
    RabbitOrdersListDataModel model;
    RabbitOrdersListDataView view;

    public RabbitOrdersListDataPresenterImpl(RabbitOrdersListDataView view){
        this.view = view;
        this.model = new RabbitOrdersListDataModelImpl();
    }

    public void getRabbitOrdersListData(String sort) {
        model.getRabbitOrdersListData(sort , this);
    }

    @Override
    public void getRabbitOrdersListDataSuccess(List<OrderListBean.OrderEntity> list) {
        view.getRabbitOrdersListDataSuccess(list);
    }

    @Override
    public void getRabbitOrdersListDataFails(String err) {
        view.getRabbitOrdersListDataFails(err);
    }
}