package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.NewFriendsBean;

/**
* Created by dell on 2017/01/19
*/

public interface GetNewFriendsView{
    void getNewFriendsSuccess(NewFriendsBean friendsBean);
    void getNewFriendsFails(String err);


    void agreedAddFriendsFails(String err);
    void agreedAddFriendsSuccess();
}