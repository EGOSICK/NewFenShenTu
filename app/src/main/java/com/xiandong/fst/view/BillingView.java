package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.PayBean;

/**
* Created by dell on 2017/01/09
*/

public interface BillingView{

    void billingMsgErr(String err);

    void billingMsgTrue(PayBean payBean);
}