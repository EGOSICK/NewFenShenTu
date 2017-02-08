package com.xiandong.fst.model;


import android.graphics.Bitmap;
import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.ChooseImageViewPresenter;
import com.xiandong.fst.tools.ChooseImageTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/05
 */

public class ChooseImageViewModelImpl implements ChooseImageViewModel {

    @Override
    public void upDataUserImg(Bitmap bitmap, final ChooseImageViewPresenter chooseImageViewPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL+"logo");
        String uid = AppDbManager.getLastUser().getUserId();
        final String img = ChooseImageTools.bitmapToBase64(bitmap);
        params.addBodyParameter("uid", uid);
        params.addBodyParameter("img", img);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        chooseImageViewPresenter.upDataUserImgSuccess(bean.getMessage());
                        saveUserImgMsg();
                        break;
                    default:
                        chooseImageViewPresenter.upDataUserImgFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                chooseImageViewPresenter.upDataUserImgFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void saveUserImgMsg(){
        RequestParams params = new RequestParams(Constant.APIURL+"show");
        params.addBodyParameter("uid",AppDbManager.getLastUser().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case Constant.HTTPSUCCESS:
                        UserBean userBean = GsonUtil.fromJson(result ,UserBean.class);
                        UserEntity userEntity = AppDbManager.getLastUser();
                        userEntity.setUserImg(userBean.getUser().getImg());
                        AppDbManager.saveUserChange(userEntity);
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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