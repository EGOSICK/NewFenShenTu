package com.xiandong.fst.model;

import com.xiandong.fst.presenter.FriendsManagerPresenter;

/**
 * Created by dell on 2017/01/20
 */

public interface FriendsManagerModel {
    void changeFriendBz(String id, String bz, FriendsManagerPresenter presenter);

    void deleteFriend(String id, FriendsManagerPresenter presenter);

    void shieldingPosition(int shi, String id, FriendsManagerPresenter presenter);
}