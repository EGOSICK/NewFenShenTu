package com.xiandong.fst.view.activity;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.presenter.ChangeUserMessagePresenterImpl;
import com.xiandong.fst.presenter.GetVerificationCodePresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.ChangeUserMessageView;
import com.xiandong.fst.view.GetVerificationCodeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/17.
 */
@ContentView(R.layout.activity_change_login_password)
public class ChangeLogInPasswordActivity extends AbsBaseActivity implements ChangeUserMessageView, GetVerificationCodeView {
    @ViewInject(R.id.changeLogInPasswordBtn)
    CircularProgressButton changeLogInPasswordBtn;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.changeLogInPswPhoneEt)
    EditText changeLogInPswPhoneEt;
    @ViewInject(R.id.changeLogInPswCodeEt)
    EditText changeLogInPswCodeEt;
    @ViewInject(R.id.changeLogInPswPasswordEt)
    EditText changeLogInPswPasswordEt;
    @ViewInject(R.id.changeLogInPswGetCodeMsgTv)
    TextView changeLogInPswGetCodeMsgTv;
    Context context;
    GetVerificationCodePresenterImpl codePresenter;

    @Override
    protected void initialize() {
        context = this;
        changeLogInPasswordBtn.setIndeterminateProgressMode(true);
        changeLogInPasswordBtn.setText("完成");
        changeLogInPasswordBtn.setIdleText("完成");
        titleTitleTv.setText("修改密码");
        codePresenter = new GetVerificationCodePresenterImpl(this);
        changeLogInPswPhoneEt.setText(AppDbManager.getLastUser().getUserPhone());
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg,
            R.id.changeLogInPasswordBtn, R.id.changeLogInPswGetCodeMsgTv})
    private void onCliCklistener(View view) {
        switch (view.getId()) {
            case R.id.changeLogInPasswordBtn:
                CircularProgressButtonTools.showLoding(changeLogInPasswordBtn);
                if (codePresenter.verificationCode(changeLogInPswCodeEt.getText().toString().trim())) {
                    ChangeUserMessagePresenterImpl presenter = new ChangeUserMessagePresenterImpl(this);
                    presenter.changeUserMessage("", "", changeLogInPswPasswordEt.getText().toString().trim());
                } else {
                    CustomToast.customToast(false, "验证码输入有误", context);
                    CircularProgressButtonTools.showErr(changeLogInPasswordBtn);
                }
                break;
            case R.id.changeLogInPswGetCodeMsgTv:
                String phone = changeLogInPswPhoneEt.getText().toString().trim();
                if (StringUtil.isTelPhone(phone)) {
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long l) {
                            changeLogInPswGetCodeMsgTv.setClickable(false);
                            changeLogInPswGetCodeMsgTv.setText(l / 1000 + "秒后重新获取");
                        }

                        @Override
                        public void onFinish() {
                            changeLogInPswGetCodeMsgTv.setClickable(true);
                            changeLogInPswGetCodeMsgTv.setText("重新获取");
                        }
                    }.start();
                    codePresenter.getCodeMsg(phone);
                } else {
                    getCodeFails(getString(R.string.phone_err));
                }
                break;
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    @Override
    public void changeUserMsgSuccess() {
        CustomToast.customToast(true, "登录密码更改成功", context);
        CircularProgressButtonTools.showTrue(changeLogInPasswordBtn);
        finish();
    }

    @Override
    public void changeUserMsgFails(String err) {
        CustomToast.customToast(false, err, context);
        CircularProgressButtonTools.showErr(changeLogInPasswordBtn);
    }

    @Override
    public void getCodeSuccess(String s) {
        CustomToast.customToast(true, s, context);
    }

    @Override
    public void getCodeFails(String s) {
        CustomToast.customToast(false, s, context);
    }
}
