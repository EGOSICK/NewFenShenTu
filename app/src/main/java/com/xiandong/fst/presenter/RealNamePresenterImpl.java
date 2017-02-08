package com.xiandong.fst.presenter;


import com.xiandong.fst.model.RealNameModel;
import com.xiandong.fst.model.RealNameModelImpl;
import com.xiandong.fst.view.RealNameView;

/**
 * Created by dell on 2017/01/17
 */

public class RealNamePresenterImpl implements RealNamePresenter {
    RealNameModel model;
    RealNameView view;

    public RealNamePresenterImpl(RealNameView view) {
        this.view = view;
        this.model = new RealNameModelImpl();
    }

    public void certification(String name, String cardNum) {
        model.certification(name, cardNum, this);
    }


    @Override
    public void certificationSuccess() {
        view.certificationSuccess();
    }

    @Override
    public void certificationFails(String err) {
        view.certificationFails(err);
    }
}