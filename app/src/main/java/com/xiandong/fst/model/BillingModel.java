package com.xiandong.fst.model;

import com.xiandong.fst.presenter.BillingPresenter;

/**
 * Created by dell on 2017/01/09
 */

public interface BillingModel {

    void billing(String position ,String address, String title, String money,
                 String time, String phone, String detial,
                 BillingPresenter billingPresenter);
}