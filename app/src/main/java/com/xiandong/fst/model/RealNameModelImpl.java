package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.RealNamePresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/17
*/

public class RealNameModelImpl implements RealNameModel{

    @Override
    public void certification(String name, String cardNum, final RealNamePresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"renzheng");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
        params.addBodyParameter("realname",name);
        params.addBodyParameter("cardid",cardNum);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("RealNameModelImpl", result);
                AbsBaseBean bean = GsonUtil.fromJson(result ,AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        presenter.certificationSuccess();
                        break;
                    default:
                        presenter.certificationFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.certificationFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}