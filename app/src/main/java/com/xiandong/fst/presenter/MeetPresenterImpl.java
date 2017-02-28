package com.xiandong.fst.presenter;


import com.xiandong.fst.model.MeetModel;
import com.xiandong.fst.model.MeetModelImpl;
import com.xiandong.fst.view.MeetView;

/**
 * Created by dell on 2017/02/08
 */

public class MeetPresenterImpl implements MeetPresenter {
    private MeetModel model;
    private MeetView view;

    public MeetPresenterImpl(MeetView view) {
        this.view = view;
        this.model = new MeetModelImpl();
    }

    public void logoutMeet(String id) {
        model.logoutMeet(id, this);
    }

    @Override
    public void logoutMeetSuccess() {
        view.logoutMeetSuccess();
    }

    @Override
    public void logoutMeetFails(String err) {
        view.logoutMeetFails(err);
    }
}