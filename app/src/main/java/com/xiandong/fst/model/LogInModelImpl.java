package com.xiandong.fst.model;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.xiandong.fst.application.BaseApplication;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.model.bean.WXUserBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.LogInPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 登录
 */

public class LogInModelImpl implements LogInModel {

    @Override
    public void logIn(String name, String passWord, final LogInPresenter logInPresenter) {

        RequestParams params = new RequestParams(Constant.APIURL + "login");
        params.addBodyParameter("phone", name);
        params.addBodyParameter("password", passWord);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                UserBean userBean = GsonUtil.fromJson(result, UserBean.class);
                switch (userBean.getResult()) {
                    case 1:
                        logInPresenter.onSuccess(userBean);
                        AppDbManager.saveUserData(userBean, true);
                        break;
                    default:
                        logInPresenter.onError(userBean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onFailure();
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
    public void transferWXLogIn(LogInPresenter logInPresenter) {
        if (!BaseApplication.iwxapi.isWXAppInstalled())
            logInPresenter.onError("没有安装微信");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "fen_shen_tu";
        BaseApplication.iwxapi.sendReq(req);
    }

    @Override
    public void wxLogIn(final LogInPresenter logInPresenter) {
        RequestParams params = new RequestParams(Constant.WXLOGINURL);
        params.addBodyParameter("appid", Constant.WX_APPID);
        params.addBodyParameter("secret", Constant.WX_APPSECRET);
        params.addBodyParameter("code", Constant.WX_CODE);
        params.addBodyParameter("grant_type", "authorization_code");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String token = object.getString("access_token");
                    String openId = object.getString("openid");
                    if (StringUtil.isEmpty(token) || StringUtil.isEmpty(openId))
                        logInPresenter.onError("微信token获取失败");
                    else
                        iWXLogIn(token, openId, logInPresenter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onError(ex.getMessage());
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
    public void wxBingDing(final LogInPresenter logInPresenter) {
        RequestParams params = new RequestParams(Constant.WXLOGINURL);
        params.addBodyParameter("appid", Constant.WX_APPID);
        params.addBodyParameter("secret", Constant.WX_APPSECRET);
        params.addBodyParameter("code", Constant.WX_CODE);
        params.addBodyParameter("grant_type", "authorization_code");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String token = object.getString("access_token");
                    String openId = object.getString("openid");
                    if (StringUtil.isEmpty(token) || StringUtil.isEmpty(openId))
                        logInPresenter.onError("微信token获取失败");
                    else
                        iWXBingDing(token, openId, logInPresenter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void iWXBingDing(final String token, String openId, final LogInPresenter logInPresenter){
        RequestParams params = new RequestParams(Constant.WXLOGUSERMSGURL);
        params.addBodyParameter("access_token", token);
        params.addBodyParameter("openid", openId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                WXUserBean wxUserBean = GsonUtil.fromJson(result, WXUserBean.class);
                if (StringUtil.isEmpty(wxUserBean.getNickname()))
                    logInPresenter.onWXLogInFailure("微信登录失败");
                else {
                    wXBingDing(wxUserBean.getHeadimgurl(), wxUserBean.getOpenid()
                            , wxUserBean.getUnionid(), wxUserBean.getNickname(), logInPresenter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onWXLogInFailure("微信登录失败:" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void wXBingDing(String img, final String openId, final String unionId,
                            final String name, final LogInPresenter logInPresenter){
        RequestParams params = new RequestParams(Constant.APIURL+"xiugai");
        params.addBodyParameter("uid",AppDbManager.getLastUser().getUserId());
        params.addBodyParameter("accesstoken",unionId);
        params.addBodyParameter("openid",openId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        logInPresenter.onWXLogInBingDingPhone();
                        UserEntity user = AppDbManager.getLastUser();
                        user.setUserWXOpenId(openId);
                        user.setUserWXUnionid(unionId);
                        AppDbManager.saveUserChange(user);
                        break;
                    default:
                        logInPresenter.onError(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void iWXLogIn(final String token, String openId, final LogInPresenter logInPresenter) {
        RequestParams params = new RequestParams(Constant.WXLOGUSERMSGURL);
        params.addBodyParameter("access_token", token);
        params.addBodyParameter("openid", openId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                WXUserBean wxUserBean = GsonUtil.fromJson(result, WXUserBean.class);
                if (StringUtil.isEmpty(wxUserBean.getNickname()))
                    logInPresenter.onWXLogInFailure("微信登录失败");
                else {
                    judgeLogInOrBingDingPhone(wxUserBean.getHeadimgurl(), wxUserBean.getOpenid()
                            , wxUserBean.getUnionid(), wxUserBean.getNickname(), logInPresenter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onWXLogInFailure("微信登录失败:" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void judgeLogInOrBingDingPhone(String img, String openId, String unionId,
                                           final String name, final LogInPresenter logInPresenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "wxlogin");
        params.addBodyParameter("img", img);
        params.addBodyParameter("openid", openId);
        params.addBodyParameter("accesstoken", unionId);
        params.addBodyParameter("nicheng", name);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        UserBean userBean = GsonUtil.fromJson(result, UserBean.class);
                        if (StringUtil.isEmpty(userBean.getUser().getPhone())) {
                            logInPresenter.onWXLogInBingDingPhone();
                            AppDbManager.saveUserData(userBean, false);
                        } else {
                            logInPresenter.onWXLogInSuccess(name);
                            AppDbManager.saveUserData(userBean, true);
                        }
                        break;
                    default:
                        logInPresenter.onWXLogInFailure("微信登录失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                logInPresenter.onWXLogInFailure("微信登录失败:" + ex.getMessage());
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