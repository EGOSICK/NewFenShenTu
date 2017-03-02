package com.xiandong.fst.view;

/**
* Created by dell on 2017/01/20
*/

public interface AcceptOrderView{
    void acceptOrderSuccess(String msg , String id , String sendId);

    void acceptOrderFails(String err);
}