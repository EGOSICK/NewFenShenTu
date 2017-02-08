package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.ContactBean;

import java.util.List;

/**
 * Created by dell on 2017/1/19.
 */

public interface ContactPresenter {
    void upContactDateSuccess();

    void upContactDateFails(String err);

    void getContactDateSuccess(List<ContactBean> list);

    void getContactDateFails(String err);
}
