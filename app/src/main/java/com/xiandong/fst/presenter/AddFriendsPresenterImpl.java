package com.xiandong.fst.presenter;


import com.xiandong.fst.model.AddFriendsModel;
import com.xiandong.fst.model.AddFriendsModelImpl;
import com.xiandong.fst.view.AddFriendsView;

/**
 * Created by dell on 2017/01/19
 */

public class AddFriendsPresenterImpl implements AddFriendsPresenter {

    AddFriendsModel model;
    AddFriendsView view;

    public AddFriendsPresenterImpl(AddFriendsView view) {
        this.view = view;
        this.model = new AddFriendsModelImpl();
    }

    public void addFriends(String userId) {
        model.addFriends(userId, this);
    }

    @Override
    public void addFriendsFails(String err) {
        view.addFriendsFails(err);
    }

    @Override
    public void addFriendsSuccess() {
        view.addFriendsSuccess();
    }
}