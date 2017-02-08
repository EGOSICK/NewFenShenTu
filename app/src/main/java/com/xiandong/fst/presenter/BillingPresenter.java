package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.PayBean;

/**
 * Created by dell on 2017/1/9.
 */

public interface BillingPresenter {

    void billingMsgErr(String err);

    void billingMsgSce(PayBean payBean);

}
