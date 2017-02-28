package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.HotPintDetailBean;

/**
* Created by dell on 2017/01/22
*/

public interface HotPintView{

    void getHotPintSuccess( HotPintDetailBean bean);

    void getHotPintFails(String err);

    void huiFuSuccess();
}