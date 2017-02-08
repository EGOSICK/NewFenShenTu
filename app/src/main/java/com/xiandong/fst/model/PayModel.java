package com.xiandong.fst.model;

import android.app.Activity;
import android.content.Context;

import com.xiandong.fst.model.bean.PayBean;
import com.xiandong.fst.model.bean.RedPacketPayBean;
import com.xiandong.fst.presenter.PayPresenter;
import com.xiandong.fst.presenter.PayPresenterImpl;

/**
 * Created by dell on 2017/01/10
 */

public interface PayModel {

    void getOrderId(PayBean payBean , Activity context , PayPresenter payPresenter);

    void getRedPacketOrderId(RedPacketPayBean payBean, PayPresenter payPresenter);
}