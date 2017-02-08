package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.SearchFriendsBean;

import java.util.List;

/**
 * Created by dell on 2017/1/20.
 */

public interface SearchFriendsPresenter {
    void searchFriendsFails(String err);
    void searchFriendsSuccess(List<SearchFriendsBean> list);
}
