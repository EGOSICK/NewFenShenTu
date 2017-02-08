package com.xiandong.fst.presenter;


import com.xiandong.fst.model.UserMessageModel;
import com.xiandong.fst.model.UserMessageModelImpl;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.view.UserMessageView;

/**
 * Created by dell on 2017/01/18
 */

public class UserMessagePresenterImpl implements UserMessagePresenter {
    UserMessageView view;
    UserMessageModel model;

    public UserMessagePresenterImpl(UserMessageView view) {
        this.view = view;
        this.model = new UserMessageModelImpl();
    }

    public void getUserMessage() {
        model.getUserMessage(this);
    }

    @Override
    public void getUserMessageSuccess(UserBean userBean) {
        view.getUserMessageSuccess(userBean);
    }

    @Override
    public void getUserMessageFails(String err) {
        view.getUserMessageFails(err);
    }
}