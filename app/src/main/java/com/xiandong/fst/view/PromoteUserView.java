package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.PromoteUserBean;

/**
* Created by dell on 2017/01/18
*/

public interface PromoteUserView{

    void getPromoteUserFails(String err);

    void getPromoteUserSuccess();

    void getPromoteUserSuccess(PromoteUserBean bean);
}