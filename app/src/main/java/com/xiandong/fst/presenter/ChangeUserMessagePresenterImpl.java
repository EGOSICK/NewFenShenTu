package com.xiandong.fst.presenter;


import com.xiandong.fst.model.ChangeUserMessageModel;
import com.xiandong.fst.model.ChangeUserMessageModelImpl;
import com.xiandong.fst.view.ChangeUserMessageView;

/**
 * Created by dell on 2017/01/17
 */

public class ChangeUserMessagePresenterImpl implements ChangeUserMessagePresenter {

    ChangeUserMessageModel model;
    ChangeUserMessageView view;

    public ChangeUserMessagePresenterImpl(ChangeUserMessageView view) {
        this.view = view;
        this.model = new ChangeUserMessageModelImpl();
    }

    public void changeUserMessage(String... userMsg) {
        switch (userMsg.length) {
            case 1:
                model.changeUserMessage(userMsg[0], "", "", "", "", "", this);
                break;
            case 2:

                break;
            case 3:
                model.changeUserMessage(userMsg[0], userMsg[1], userMsg[2], "", "", "", this);
                break;
            case 4:
                model.changeUserMessage(userMsg[0], userMsg[1], userMsg[2], userMsg[3], "", "", this);
                break;
            case 5:
                model.changeUserMessage(userMsg[0], userMsg[1], userMsg[2], userMsg[3], userMsg[4], "", this);
                break;
            case 6:
                model.changeUserMessage(userMsg[0], userMsg[1], userMsg[2], userMsg[3], userMsg[4], userMsg[5], this);
                break;
        }

    }


    @Override
    public void changeUserMsgSuccess() {
        view.changeUserMsgSuccess();
    }

    @Override
    public void changeUserMsgFails(String err) {
        view.changeUserMsgFails(err);
    }
}