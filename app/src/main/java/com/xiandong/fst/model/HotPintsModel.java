package com.xiandong.fst.model;

import com.xiandong.fst.presenter.HotPintsPresenter;

/**
* Created by dell on 2017/01/20
*/

public interface HotPintsModel{
    void getHotPints(HotPintsPresenter presenter);

    void searchPints(String position, String name , HotPintsPresenter presenter);
}