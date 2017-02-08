package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.SearchFriendsBean;

import java.util.List;

/**
* Created by dell on 2017/01/20
*/

public interface SearchFriendsView{
    void searchFriendsFails(String err);
    void searchFriendsSuccess(List<SearchFriendsBean> list);
}