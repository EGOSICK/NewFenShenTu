package com.xiandong.fst.model;

import android.content.Context;

import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.presenter.FriendsPresenter;

import java.util.List;

/**
* Created by dell on 2017/01/16
*/

public interface FriendsModel{

    void getFriends(FriendsPresenter presenter);

    void showFriendsPosition(Context context ,List<FriendsBean.FriendEntity> list, FriendsPresenter presenter);

}