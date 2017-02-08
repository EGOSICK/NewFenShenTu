package com.xiandong.fst.presenter;


import com.xiandong.fst.model.PromoteUserModel;
import com.xiandong.fst.model.PromoteUserModelImpl;
import com.xiandong.fst.model.bean.PromoteUserBean;
import com.xiandong.fst.view.PromoteUserView;

/**
 * Created by dell on 2017/01/18
 */

public class PromoteUserPresenterImpl implements PromoteUserPresenter {
    PromoteUserModel model;
    PromoteUserView view;

    public PromoteUserPresenterImpl(PromoteUserView view) {
        this.view = view;
        this.model = new PromoteUserModelImpl();
    }

    public void getPromoteUser() {
        model.getPromoteUser(this);
    }


    @Override
    public void getPromoteUserFails(String err) {
        this.view.getPromoteUserFails(err);
    }

    @Override
    public void getPromoteUserSuccess() {
        this.view.getPromoteUserSuccess();
    }

    @Override
    public void getPromoteUserSuccess(PromoteUserBean bean) {
        this.view.getPromoteUserSuccess(bean);
    }
}