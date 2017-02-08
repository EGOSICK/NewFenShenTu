package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.FillInRecommendedPersonPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/01/06
*/

public class FillInRecommendedPersonModelImpl implements FillInRecommendedPersonModel {


    @Override
    public void fillInRecommendedPerson(String phone, final FillInRecommendedPersonPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"addtuijian");
        params.addBodyParameter("uid",AppDbManager.getLastUser().getUserId());
        params.addBodyParameter("phone",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result,AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        presenter.fillInRecommendedPersonSuccess(bean.getMessage());
                        break;
                    default:
                        presenter.fillInRecommendedPersonFails(bean.getMessage());
                        break;
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.fillInRecommendedPersonFails(ex.getMessage());
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