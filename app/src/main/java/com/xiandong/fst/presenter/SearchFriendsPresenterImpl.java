package com.xiandong.fst.presenter;


import com.xiandong.fst.model.SearchFriendsModel;
import com.xiandong.fst.model.SearchFriendsModelImpl;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.view.SearchFriendsView;

import java.util.List;

/**
 * Created by dell on 2017/01/20
 */

public class SearchFriendsPresenterImpl implements SearchFriendsPresenter {
    SearchFriendsModel searchFriendsModel;
    SearchFriendsView searchFriendsView;

    public SearchFriendsPresenterImpl(SearchFriendsView searchFriendsView) {
        this.searchFriendsView = searchFriendsView;
        this.searchFriendsModel = new SearchFriendsModelImpl();
    }

    public void searchFriends(String search) {
        searchFriendsModel.searchFriends(search, this);
    }

    @Override
    public void searchFriendsFails(String err) {
        searchFriendsView.searchFriendsFails(err);
    }

    @Override
    public void searchFriendsSuccess(List<SearchFriendsBean> list) {
        searchFriendsView.searchFriendsSuccess(list);
    }
}