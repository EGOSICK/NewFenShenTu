package com.xiandong.fst.presenter;


import android.content.Context;

import com.xiandong.fst.R;
import com.xiandong.fst.model.BingDingPhoneNumModel;
import com.xiandong.fst.model.BingDingPhoneNumModelImpl;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.BingDingPhoneNumView;

/**
 * Created by dell on 2017/01/04
 */

public class BingDingPhoneNumPresenterImpl implements BingDingPhoneNumPresenter {

    private BingDingPhoneNumView bingDingPhoneNumView;
    private BingDingPhoneNumModel bingDingPhoneNumModel;
    private String code;

    public BingDingPhoneNumPresenterImpl(BingDingPhoneNumView bingDingPhoneNumView) {
        this.bingDingPhoneNumView = bingDingPhoneNumView;
        this.bingDingPhoneNumModel = new BingDingPhoneNumModelImpl();
    }

    public void bingDingPhoneNum(String phone, String code, String psw, Context context) {
        if (StringUtil.isTelPhone(phone))
            if (StringUtil.isEquals(code, this.code))
                if (!StringUtil.isEmpty(psw))
                    bingDingPhoneNumModel.bingDingPhoneNum(phone, psw, this);
                else
                    bingDingPhoneNumView.showErrMessage(context.getString(R.string.password_err));
            else
                bingDingPhoneNumView.showErrMessage(context.getString(R.string.code_err));
        else
            bingDingPhoneNumView.showErrMessage(context.getString(R.string.phone_err));
    }

    public void sendCodeMessage(String phone, Context context) {
        if (StringUtil.isTelPhone(phone))
            bingDingPhoneNumModel.sendCodeMessage(phone, this);
        else
            bingDingPhoneNumView.showErrMessage(context.getString(R.string.phone_err));
    }

    @Override
    public void bingDingSuccess(String msg) {
        bingDingPhoneNumView.bingDingSuccess(msg);
    }

    @Override
    public void bingDingFails(String err) {
        bingDingPhoneNumView.showErrMessage(err);
    }

    @Override
    public void sendCodeMsgSuccess(String msg, String code) {
        bingDingPhoneNumView.getCodeMsgSuccess(msg);
        this.code = code;
    }

    @Override
    public void sendCodeMsgFails(String err) {
        bingDingPhoneNumView.getCodeMsgFails(err);
    }
}