package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/21.
 */

public interface MarkerPresenter  {
    void getRedPacketSuccess(int type, String come ,String money);
    void getRedPacketFails(String err);

    void grabRedPacketSuccess();

    void grabRedPacketFails(String err);
}
