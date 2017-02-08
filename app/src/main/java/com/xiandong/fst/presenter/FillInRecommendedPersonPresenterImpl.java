package com.xiandong.fst.presenter;


import android.content.Context;

import com.xiandong.fst.R;
import com.xiandong.fst.model.FillInRecommendedPersonModel;
import com.xiandong.fst.model.FillInRecommendedPersonModelImpl;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.FillInRecommendedPersonView;

/**
 * Created by dell on 2017/01/06
 */

public class FillInRecommendedPersonPresenterImpl implements FillInRecommendedPersonPresenter {

    FillInRecommendedPersonModel model;
    FillInRecommendedPersonView view;

    public FillInRecommendedPersonPresenterImpl(FillInRecommendedPersonView view) {
        this.model = new FillInRecommendedPersonModelImpl();
        this.view = view;
    }

    public void fillInRecommendedPerson(String phone, Context context) {
        if (StringUtil.isTelPhone(phone))
            model.fillInRecommendedPerson(phone, this);
        else
            view.fillInRecommendedPersonFails(context.getString(R.string.phone_err));
    }

    @Override
    public void fillInRecommendedPersonSuccess(String msg) {
        view.fillInRecommendedPersonFails(msg);
    }

    @Override
    public void fillInRecommendedPersonFails(String err) {
        view.fillInRecommendedPersonFails(err);
    }
}