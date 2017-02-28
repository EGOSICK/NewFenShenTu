package com.xiandong.fst.view;

import com.baidu.mapapi.map.BaiduMap;
import com.xiandong.fst.model.bean.HotPintsBean;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.model.bean.SearchPintsBean;

import java.util.List;

/**
* Created by dell on 2017/01/20
*/

public interface HotPintsView{
    void getHotPintsSuccess(List<HotPintsBean.ForumEntity> list);

    void getHotPintsFails(String err);

    BaiduMap loadForum();

    void searchSuccess(SearchPintsBean friendsBean);

    void searchFails(String err);
}