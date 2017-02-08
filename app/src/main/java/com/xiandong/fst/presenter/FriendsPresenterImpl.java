package com.xiandong.fst.presenter;


import android.content.Context;

import com.baidu.mapapi.map.MarkerOptions;
import com.xiandong.fst.model.FriendsModel;
import com.xiandong.fst.model.FriendsModelImpl;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.view.FriendsView;

import java.util.List;

/**
 * Created by dell on 2017/01/16
 */

public class FriendsPresenterImpl implements FriendsPresenter {
    private FriendsModel model;
    private FriendsView view;

    public FriendsPresenterImpl(FriendsView view) {
        this.model = new FriendsModelImpl();
        this.view = view;
    }

    public void getFriends() {
        model.getFriends(this);
    }

    public void showFriendsPosition(Context context, List<FriendsBean.FriendEntity> list) {
        model.showFriendsPosition(context, list, this);
    }

    @Override
    public void getFriendsSuccess(FriendsBean friendsBean) {
        view.getFriendsSuccess(friendsBean);
    }

    @Override
    public void getFriendsFails(String err) {
        view.getFriendsFails(err);
    }

    @Override
    public void friendsImgSuccess(MarkerOptions option) {
        view.friendsImgSuccess(option);
    }

    @Override
    public void friendsImgFails(String err) {
        view.friendsImgFails(err);
    }
}