package com.xiandong.fst.model;

import android.content.Context;

import com.xiandong.fst.presenter.ContactPresenter;

/**
 * Created by dell on 2017/01/19
 */

public interface ContactModel {
    void upDateContact(Context context ,ContactPresenter presenter);

    void getContactDate(ContactPresenter presenter);
}