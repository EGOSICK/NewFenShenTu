package com.xiandong.fst.view;

/**
* Created by dell on 2017/01/21
*/

public interface MarkerView{
    void getRedPacketSuccess(int type, String come ,String money,String address);
    void getRedPacketFails(String err);

    void grabRedPacketFails(String err);
    void grabRedPacketSuccess();
}