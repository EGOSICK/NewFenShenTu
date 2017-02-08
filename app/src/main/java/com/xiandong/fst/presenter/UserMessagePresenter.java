package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.UserBean;

/**
 * Created by dell on 2017/1/18.
 */

public interface UserMessagePresenter {
    void getUserMessageSuccess(UserBean userBean);

    void getUserMessageFails(String err);
}
