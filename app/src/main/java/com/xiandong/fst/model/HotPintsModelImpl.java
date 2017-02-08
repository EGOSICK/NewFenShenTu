package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.HotPintsBean;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.model.bean.SearchPintsBean;
import com.xiandong.fst.presenter.HotPintsPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/20
 */

public class HotPintsModelImpl implements HotPintsModel {

    @Override
    public void getHotPints(final HotPintsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showforumpos");
        params.addBodyParameter("id", "");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        HotPintsBean hotPintsBean = GsonUtil.fromJson(result, HotPintsBean.class);
                        presenter.getHotPintsSuccess(hotPintsBean.getForum());
                        break;
                    default:
                        presenter.fetHotPintsFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.fetHotPintsFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void searchPints(String position, String name, final HotPintsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showforumpos");
        if (!StringUtil.isEmpty(position))
            params.addBodyParameter("position", position);
        if (!StringUtil.isEmpty(name))
            params.addBodyParameter("searchname", name);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result ,AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        SearchPintsBean search = GsonUtil.fromJson(result ,SearchPintsBean.class);

                        presenter.searchSuccess(search);
                        break;
                    default:
                        presenter.fetHotPintsFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.searchFails(ex.getMessage());
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