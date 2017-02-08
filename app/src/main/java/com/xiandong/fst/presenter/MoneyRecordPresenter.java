package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.MoneyRecordBean;

/**
 * Created by dell on 2017/1/18.
 */

public interface MoneyRecordPresenter {
    void moneyRecordSuccess(MoneyRecordBean recordBean);
    void moneyRecordFails(String err);
}
