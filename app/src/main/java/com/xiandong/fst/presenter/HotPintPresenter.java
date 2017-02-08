package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.HotPintDetailBean;

/**
 * Created by dell on 2017/1/22.
 */

public interface HotPintPresenter {

    void getHotPintSuccess( HotPintDetailBean bean);

    void fetHotPintFails(String err);

    void huiFuSuccess();
}
