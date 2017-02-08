package com.xiandong.fst.presenter;


import com.xiandong.fst.model.BillingModel;
import com.xiandong.fst.model.BillingModelImpl;
import com.xiandong.fst.model.bean.PayBean;
import com.xiandong.fst.view.BillingView;

/**
 * Created by dell on 2017/01/09
 */

public class BillingPresenterImpl implements BillingPresenter {
    BillingModel model;
    BillingView view;

    public BillingPresenterImpl(BillingView view) {
        this.view = view;
        this.model = new BillingModelImpl();
    }


    public void billing(String position ,String address, String title, String money,
                        String time, String phone, String detial) {
        model.billing(position ,address, title, money, time, phone, detial, this);
    }


    @Override
    public void billingMsgErr(String err) {
        view.billingMsgErr(err);
    }

    @Override
    public void billingMsgSce(PayBean payBean) {
        view.billingMsgTrue(payBean);
    }
}