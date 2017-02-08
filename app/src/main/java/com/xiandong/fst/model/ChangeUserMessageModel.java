package com.xiandong.fst.model;

import com.xiandong.fst.presenter.ChangeUserMessagePresenter;

/**
 * Created by dell on 2017/01/17
 */

public interface ChangeUserMessageModel {

    void changeUserMessage(String nickName, String phone
            , String psw, String zfPsw, String token, String openId, ChangeUserMessagePresenter presenter);
}