package com.xiandong.fst.presenter;

import com.baidu.mapapi.map.MarkerOptions;
import com.xiandong.fst.model.bean.FriendsBean;

/**
 * Created by dell on 2017/1/16.
 */

public interface FriendsPresenter {
    void getFriendsSuccess(FriendsBean friendsBean);
    void getFriendsFails(String err);

    void friendsImgSuccess(MarkerOptions option);

    void friendsImgFails(String err);
}
