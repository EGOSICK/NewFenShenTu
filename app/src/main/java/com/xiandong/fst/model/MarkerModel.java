package com.xiandong.fst.model;

import com.xiandong.fst.presenter.MarkerPresenter;

/**
 * Created by dell on 2017/01/21
 */

public interface MarkerModel {
    void getRedPacketMsg(String come, String id, String money,String address, MarkerPresenter presenter);

    void grabRedPacket(String id, MarkerPresenter presenter);
}