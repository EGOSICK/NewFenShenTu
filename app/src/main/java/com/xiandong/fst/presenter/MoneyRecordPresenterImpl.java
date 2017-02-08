package com.xiandong.fst.presenter;


import com.xiandong.fst.model.MoneyRecordModel;
import com.xiandong.fst.model.MoneyRecordModelImpl;
import com.xiandong.fst.model.bean.MoneyRecordBean;
import com.xiandong.fst.view.MoneyRecordView;

/**
 * Created by dell on 2017/01/18
 */

public class MoneyRecordPresenterImpl implements MoneyRecordPresenter {
    MoneyRecordModel model;
    MoneyRecordView view;

    public MoneyRecordPresenterImpl(MoneyRecordView view) {
        this.view = view;
        this.model = new MoneyRecordModelImpl();
    }

    public void getMoneyRecord() {
        model.getMoneyRecord(this);
    }

    @Override
    public void moneyRecordSuccess(MoneyRecordBean recordBean) {
        view.moneyRecordSuccess(recordBean);
    }

    @Override
    public void moneyRecordFails(String err) {
        view.moneyRecordFails(err);
    }
}