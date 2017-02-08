package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.RealNameAuthenticationBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.LogInPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.LogInView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_security_center)
public class SecurityCenterActivity extends AbsBaseActivity {

    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.securityCenterWXBDTv)
    TextView securityCenterWXBDTv;
    @ViewInject(R.id.securityCenterPhoneBDTv)
    TextView securityCenterPhoneBDTv;
    Context context;
    @ViewInject(R.id.securityCenterPhoneBDView)
    View securityCenterPhoneBDView;
    @ViewInject(R.id.securityCenterWXBDView)
    View securityCenterWXBDView;
    @ViewInject(R.id.securityCenterShiMingView)
    View securityCenterShiMingView;
    @ViewInject(R.id.securityCenterShiMingTv)
    TextView securityCenterShiMingTv;
    boolean isWXLogIn = false;

    @Override
    protected void initialize() {
        context = this;
        initView();
        initShiMingType();
    }

    LogInPresenterImpl logInPresenter;

    private void initView() {
        titleTitleTv.setText("安全中心");
        UserEntity user = AppDbManager.getLastUser();
        String phone = user.getUserPhone();
        String token = user.getUserWXUnionid();
        if (StringUtil.isEmpty(phone)) {
            securityCenterPhoneBDTv.setText("未绑定");
            securityCenterPhoneBDView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, BingDinPhoneNumActivity.class));
                }
            });
        } else {
            securityCenterPhoneBDTv.setText(phone);
        }
        if (StringUtil.isEmpty(token)) {
            securityCenterWXBDTv.setText("未绑定");
            securityCenterWXBDView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logInPresenter = new LogInPresenterImpl(new LogInView() {
                        @Override
                        public void success() {

                        }

                        @Override
                        public void showToast(String s) {
                            CustomToast.customToast(false, s, context);
                            WXLogInComplete();
                        }

                        @Override
                        public String getName() {
                            return null;
                        }

                        @Override
                        public String getPassWord() {
                            return null;
                        }

                        @Override
                        public void wxLogInSuccess() {

                        }

                        @Override
                        public void wxLogInFails(String err) {

                        }

                        @Override
                        public void wxLogInBingDingPhone() {
                            securityCenterWXBDTv.setText("已绑定");
                            WXLogInComplete();
                        }
                    });
                    WXLogInStart();
                }
            });
        } else {
            securityCenterWXBDTv.setText("已绑定");
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg,
            R.id.securityCenterPayPasswordView, R.id.securityCenterLogInPswView,
            R.id.securityCenterShiMingView})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.securityCenterPayPasswordView:
                startActivity(new Intent(context, ChangePayPasswordActivity.class));
                break;
            case R.id.securityCenterLogInPswView:
                startActivity(new Intent(context, ChangeLogInPasswordActivity.class));
                break;
            case R.id.securityCenterShiMingView:
                startActivityForResult(new Intent(context, RealNameAuthenticationActivity.class),0);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isWXLogIn)
            logInPresenter.wxBingDing();
    }

    private void WXLogInComplete() {
        isWXLogIn = false;
        StyledDialogTools.disMissStyleDialog();
    }

    private void WXLogInStart() {
        isWXLogIn = true;
        StyledDialogTools.showStyledDialog(context);
        logInPresenter.transferWXLogIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!= null && resultCode == 0){
            securityCenterShiMingTv.setText("审核中");
            securityCenterShiMingView.setClickable(false);
        }
    }

    private void initShiMingType(){
        RequestParams params = new RequestParams(Constant.APIURL+"rzinfo");
        params.addBodyParameter("uid",AppDbManager.getLastUser().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result , AbsBaseBean.class);
                switch (bean.getResult()){
                    case 1:
                        RealNameAuthenticationBean real = GsonUtil.fromJson(result ,
                                RealNameAuthenticationBean.class);
                       switch (real.getInfo().getRenzheng()){
                           case "0":
                               securityCenterShiMingTv.setText("审核中");
                               securityCenterShiMingView.setClickable(false);
                               break;
                           case "1":
                               securityCenterShiMingTv.setText("已认证");
                               securityCenterShiMingView.setClickable(false);
                               break;
                           case "2":
                               securityCenterShiMingTv.setText("未通过");
                               break;
                       }
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
