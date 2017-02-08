package com.xiandong.fst.presenter;


import com.xiandong.fst.model.GetNewFriendsModel;
import com.xiandong.fst.model.GetNewFriendsModelImpl;
import com.xiandong.fst.model.bean.NewFriendsBean;
import com.xiandong.fst.view.GetNewFriendsView;

/**
 * Created by dell on 2017/01/19
 */

public class GetNewFriendsPresenterImpl implements GetNewFriendsPresenter {
    GetNewFriendsModel model;
    GetNewFriendsView view;

    public GetNewFriendsPresenterImpl(GetNewFriendsView view) {
        this.view = view;
        this.model = new GetNewFriendsModelImpl();
    }

    public void getNewFriends() {
        model.getNewFriends(this);
    }

    public void agreedAddFriends(String userId){
        model.agreedAddFriends(userId , this);
    }


    @Override
    public void getNewFriendsSuccess( NewFriendsBean friendsBean) {
        view.getNewFriendsSuccess(friendsBean);
    }

    @Override
    public void getNewFriendsFails(String err) {
        view.getNewFriendsFails(err);
    }

    @Override
    public void agreedAddFriendsFails(String err) {
        view.agreedAddFriendsFails(err);
    }

    @Override
    public void agreedAddFriendsSuccess() {
        view.agreedAddFriendsSuccess();
    }
}