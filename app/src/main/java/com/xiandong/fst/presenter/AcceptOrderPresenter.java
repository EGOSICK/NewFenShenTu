package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/20.
 */

public interface AcceptOrderPresenter  {

    void acceptOrderSuccess(String msg ,String id ,String sendId);

    void acceptOrderFails(String err);
}
