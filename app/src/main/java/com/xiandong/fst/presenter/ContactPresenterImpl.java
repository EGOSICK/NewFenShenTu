package com.xiandong.fst.presenter;


import android.content.Context;

import com.xiandong.fst.model.AddFriendsModel;
import com.xiandong.fst.model.AddFriendsModelImpl;
import com.xiandong.fst.model.ContactModel;
import com.xiandong.fst.model.ContactModelImpl;
import com.xiandong.fst.model.bean.ContactBean;
import com.xiandong.fst.view.AddFriendsView;
import com.xiandong.fst.view.ContactView;

import java.util.List;


/**
 * Created by dell on 2017/01/19
 */

public class ContactPresenterImpl extends AddFriendsPresenterImpl implements ContactPresenter {
    ContactModel model;
    ContactView view;
    AddFriendsView addFriendsView;
    public ContactPresenterImpl(ContactView view,AddFriendsView addFriendsView) {
        super(addFriendsView);
        this.addFriendsView = addFriendsView;
        this.model = new ContactModelImpl();
        this.view = view;
    }

    public void upDateContact(Context context) {
        model.upDateContact(context, this);
    }

    public void getContactDate() {
        model.getContactDate(this);
    }

    @Override
    public void upContactDateSuccess() {
        view.upContactDateSuccess();
    }

    @Override
    public void upContactDateFails(String err) {
        view.upContactDateFails(err);
    }

    @Override
    public void getContactDateSuccess(List<ContactBean> list) {
        view.getContactDateSuccess(list);
    }

    @Override
    public void getContactDateFails(String err) {
        view.getContactDateFails(err);
    }
}