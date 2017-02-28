package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.presenter.MeetPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
* Created by dell on 2017/02/08
*/

public class MeetModelImpl implements MeetModel{

    @Override
    public void logoutMeet(String id, final MeetPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "ifmeeting");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("id",id);
        params.addBodyParameter("act","0");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        presenter.logoutMeetSuccess();
                        break;
                    default:
                        presenter.logoutMeetFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.logoutMeetFails(ex.getMessage());
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