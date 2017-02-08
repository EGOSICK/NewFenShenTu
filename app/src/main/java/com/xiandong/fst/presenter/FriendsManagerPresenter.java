package com.xiandong.fst.presenter;

/**
 * Created by dell on 2017/1/20.
 */

public interface FriendsManagerPresenter {
    void changeFriendMessageSuccess(String msg);
    void changeFriendsMessageFails(String err);

    void deleteFriendSuccess(String msg);
}
