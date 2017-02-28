package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.OrderDetailsMsgBean;

/**
* Created by dell on 2017/01/23
*/

public interface OrderDetailsView{

    void getOrderMsgSuccess(OrderDetailsMsgBean msgBean);

    void getOrderMsgFails(String err);

    void complicateOrderSuccess(String msg);

    void complicateOrderFails(String err);

    void cancelOrderFails(String err);

    void cancelOrderSuccess(String msg);

    void eWaiSuccess(String msg);

    void eWaiFails(String err);
}