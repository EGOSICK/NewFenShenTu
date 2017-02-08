package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.NewFriendsBean;

/**
 * Created by dell on 2017/1/19.
 */

public interface GetNewFriendsPresenter {
    void getNewFriendsSuccess( NewFriendsBean friendsBean);
    void getNewFriendsFails(String err);

    void agreedAddFriendsFails(String err);
    void agreedAddFriendsSuccess();
}
