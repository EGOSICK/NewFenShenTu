package com.xiandong.fst.model;

import com.xiandong.fst.presenter.HotPintPresenter;

/**
 * Created by dell on 2017/01/22
 */

public interface HotPintModel {
    void getHotPintMsg(String id, HotPintPresenter presenter);
    void huiFu(String msg, String pid, String position, HotPintPresenter presenter);
}