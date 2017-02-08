package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.OrderListBean;

import java.util.List;

/**
 * Created by dell on 2017/1/9.
 */

public interface RabbitOrdersListDataPresenter {

    void getRabbitOrdersListDataSuccess(List<OrderListBean.OrderEntity> list);

    void getRabbitOrdersListDataFails(String err);
}
