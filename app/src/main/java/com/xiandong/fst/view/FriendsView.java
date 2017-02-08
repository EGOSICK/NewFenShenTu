package com.xiandong.fst.view;

import com.baidu.mapapi.map.MarkerOptions;
import com.xiandong.fst.model.bean.FriendsBean;

/**
* Created by dell on 2017/01/16
*/

public interface FriendsView{

    void getFriendsFails(String msg);

    void getFriendsSuccess(FriendsBean friendsBean);

    void friendsImgSuccess(MarkerOptions option);

    void friendsImgFails(String err);
}