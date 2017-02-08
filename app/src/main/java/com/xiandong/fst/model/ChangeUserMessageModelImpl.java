package com.xiandong.fst.model;


import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.ChangeUserMessagePresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by dell on 2017/01/17
 */

public class ChangeUserMessageModelImpl implements ChangeUserMessageModel {

    @Override
    public void changeUserMessage(final String nickName, final String phone,
                                  final String psw, final String zfPsw,
                                  final String token, final String openId,
                                  final ChangeUserMessagePresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "xiugai");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
        if (!StringUtil.isEmpty(nickName))
            params.addBodyParameter("nicheng", nickName);
        if (!StringUtil.isEmpty(phone))
            params.addBodyParameter("phone", phone);
        if (!StringUtil.isEmpty(psw))
            params.addBodyParameter("password", psw);
        if (!StringUtil.isEmpty(zfPsw))
            params.addBodyParameter("zfpassword", zfPsw);
        if (!StringUtil.isEmpty(token))
            params.addBodyParameter("accesstoken", token);
        if (!StringUtil.isEmpty(openId))
            params.addBodyParameter("openid", openId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.changeUserMsgSuccess();
                        UserEntity user = AppDbManager.getLastUser();
                        if (!StringUtil.isEmpty(nickName))
                            user.setUserName(nickName);
                        if (!StringUtil.isEmpty(phone))
                            user.setUserPhone(phone);
                        if (!StringUtil.isEmpty(psw))
                            user.setUserPassword(psw);
                        if (!StringUtil.isEmpty(zfPsw))
                            user.setUserPayPsw(zfPsw);
                        if (!StringUtil.isEmpty(token))
                            user.setUserWXUnionid(token);
                        if (!StringUtil.isEmpty(openId))
                            user.setUserWXOpenId(openId);
                        AppDbManager.saveUserChange(user);
                        break;
                    default:
                        presenter.changeUserMsgFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.changeUserMsgFails(ex.getMessage());
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