package com.xiandong.fst.view;

import com.xiandong.fst.model.bean.ContactBean;

import java.util.List;

/**
* Created by dell on 2017/01/19
*/

public interface ContactView{
    void upContactDateSuccess();

    void upContactDateFails(String err);

    void getContactDateSuccess(List<ContactBean> list);

    void getContactDateFails(String err);
}