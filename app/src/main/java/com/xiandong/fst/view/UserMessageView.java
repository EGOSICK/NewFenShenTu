package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.UserBean;

/**
* Created by dell on 2017/01/18
*/

public interface UserMessageView{
    void getUserMessageSuccess(UserBean userBean);

    void getUserMessageFails(String err);
}