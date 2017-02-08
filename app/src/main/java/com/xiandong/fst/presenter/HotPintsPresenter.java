package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.HotPintsBean;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.model.bean.SearchPintsBean;

import java.util.List;

/**
 * Created by dell on 2017/1/20.
 */

public interface HotPintsPresenter {

    void getHotPintsSuccess(List<HotPintsBean.ForumEntity> list);

    void fetHotPintsFails(String err);

    void searchSuccess(SearchPintsBean search);

    void searchFails(String err);
}
