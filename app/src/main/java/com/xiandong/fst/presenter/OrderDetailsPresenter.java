package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.OrderDetailsMsgBean;

/**
 * Created by dell on 2017/1/23.
 */

public interface OrderDetailsPresenter {

    void getOrderMsgSuccess(OrderDetailsMsgBean msgBean);

    void getOrderMsgFails(String err);
}
