package com.xiandong.fst.model;

import com.xiandong.fst.presenter.GetVerificationCodePresenter;

/**
 * Created by dell on 2017/01/04
 */

public interface GetVerificationCodeModel {
    void getCode(String phone, GetVerificationCodePresenter getVerificationCodePresenter);
}