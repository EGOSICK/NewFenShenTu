package com.xiandong.fst.model;

import android.graphics.Bitmap;

import com.xiandong.fst.presenter.ChooseImageViewPresenter;

/**
* Created by dell on 2017/01/05
*/

public interface ChooseImageViewModel{

    void upDataUserImg(Bitmap bitmap , ChooseImageViewPresenter chooseImageViewPresenter);
}