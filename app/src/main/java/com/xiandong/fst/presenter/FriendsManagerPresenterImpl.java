package com.xiandong.fst.presenter;


import com.xiandong.fst.model.FriendsManagerModel;
import com.xiandong.fst.model.FriendsManagerModelImpl;
import com.xiandong.fst.view.FriendsManagerView;

/**
 * Created by dell on 2017/01/20
 */

public class FriendsManagerPresenterImpl implements FriendsManagerPresenter {
    FriendsManagerModel friendsMengerModel;
    FriendsManagerView friendsManagerView;

    public FriendsManagerPresenterImpl(FriendsManagerView friendsManagerView) {
        this.friendsManagerView = friendsManagerView;
        this.friendsMengerModel = new FriendsManagerModelImpl();
    }

    public void changeFriendBz(String id, String bz) {
        friendsMengerModel.changeFriendBz(id, bz, this);
    }

    public void deleteFriend(String id) {
        friendsMengerModel.deleteFriend(id, this);
    }

    public void shieldingPosition(int shi , String id){
        friendsMengerModel.shieldingPosition(shi , id , this);
    }

    @Override
    public void changeFriendMessageSuccess(String msg) {
        friendsManagerView.changeFriendMessageSuccess(msg);
    }

    @Override
    public void changeFriendsMessageFails(String err) {
        friendsManagerView.changeFriendsMessageFails(err);
    }

    @Override
    public void deleteFriendSuccess(String msg) {
        friendsManagerView.deleteFriendSuccess(msg);
    }
}