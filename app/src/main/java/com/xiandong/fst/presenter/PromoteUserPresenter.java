package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.PromoteUserBean;

/**
 *
 */

public interface PromoteUserPresenter {

    void getPromoteUserFails(String err);

    void getPromoteUserSuccess();

    void getPromoteUserSuccess(PromoteUserBean bean);

}
