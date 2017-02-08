package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.OrderListBean;

import java.util.List;

/**
* Created by dell on 2017/01/09
*/

public interface RabbitOrdersListDataView{

    void getRabbitOrdersListDataSuccess(List<OrderListBean.OrderEntity> list);

    void getRabbitOrdersListDataFails(String err);

}