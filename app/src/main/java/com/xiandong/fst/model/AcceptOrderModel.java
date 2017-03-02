package com.xiandong.fst.model;

import com.xiandong.fst.presenter.AcceptOrderPresenter;

/**
 * Created by dell on 2017/01/20
 */

public interface AcceptOrderModel {
    void acceptOrder(String id, String sendId ,AcceptOrderPresenter presenter);
}