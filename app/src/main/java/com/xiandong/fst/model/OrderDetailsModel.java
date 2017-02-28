package com.xiandong.fst.model;

import com.xiandong.fst.presenter.OrderDetailsPresenter;

/**
* Created by dell on 2017/01/23
*/

public interface OrderDetailsModel{
    void getOrderMsg(String id , OrderDetailsPresenter presenter);

    void complicateOrder(String id , OrderDetailsPresenter presenter);

    void cancelOrder(String id,String type , OrderDetailsPresenter presenter);

    void eWai(String id , String money ,OrderDetailsPresenter presenter);
}