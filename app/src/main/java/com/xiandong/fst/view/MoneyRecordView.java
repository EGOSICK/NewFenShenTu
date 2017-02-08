package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.MoneyRecordBean;

/**
* Created by dell on 2017/01/18
*/

public interface MoneyRecordView{
    void moneyRecordSuccess(MoneyRecordBean recordBean);
    void moneyRecordFails(String err);
}