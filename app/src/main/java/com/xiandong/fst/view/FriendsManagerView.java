package com.xiandong.fst.view;

/**
* Created by dell on 2017/01/20
*/

public interface FriendsManagerView {
    void changeFriendMessageSuccess(String msg);
    void changeFriendsMessageFails(String err);

    void deleteFriendSuccess(String msg);
}