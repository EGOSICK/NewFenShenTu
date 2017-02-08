package com.xiandong.fst.presenter;


import com.xiandong.fst.model.MarkerModel;
import com.xiandong.fst.model.MarkerModelImpl;
import com.xiandong.fst.view.MarkerView;

/**
 * Created by dell on 2017/01/21
 */

public class MarkerPresenterImpl implements MarkerPresenter {
    MarkerModel model;
    MarkerView view;

    public MarkerPresenterImpl(MarkerView view) {
        this.view = view;
        this.model = new MarkerModelImpl();
    }

    public void getRedPacketMsg(String come, String id, String money) {
        model.getRedPacketMsg(come, id, money, this);
    }

    public void grabRedPacket(String id) {
        model.grabRedPacket(id,  this);
    }

    @Override
    public void getRedPacketSuccess(int type, String come, String money) {
        view.getRedPacketSuccess(type, come, money);
    }

    @Override
    public void getRedPacketFails(String err) {
        view.getRedPacketFails(err);
    }

    @Override
    public void grabRedPacketSuccess() {
        view.grabRedPacketSuccess();
    }

    @Override
    public void grabRedPacketFails(String err) {
        view.grabRedPacketFails(err);
    }
}