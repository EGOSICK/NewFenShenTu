package com.xiandong.fst.model;

import com.xiandong.fst.presenter.GetNewFriendsPresenter;

/**
 * Created by dell on 2017/01/19
 */

public interface GetNewFriendsModel {
    void getNewFriends(GetNewFriendsPresenter presenter);
    void agreedAddFriends(String userId, GetNewFriendsPresenter presenter);
}