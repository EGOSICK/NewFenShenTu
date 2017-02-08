package com.xiandong.fst.view.activity;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.presenter.ForgetPasswordPresenterImpl;
import com.xiandong.fst.presenter.GetVerificationCodePresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.ForgetPasswordView;
import com.xiandong.fst.view.GetVerificationCodeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 忘记密码 activity
 */

@ContentView(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends AbsBaseActivity implements GetVerificationCodeView, ForgetPasswordView {

    @ViewInject(R.id.forgetPasswordBtn)
    CircularProgressButton forgetPasswordBtn;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.phoneEt)
    EditText phoneEt;
    @ViewInject(R.id.codeEt)
    EditText codeEt;
    @ViewInject(R.id.getCodeMsgTv)
    TextView getCodeMsgTv;
    @ViewInject(R.id.successTv)
    TextView successTv;
    @ViewInject(R.id.stepView)
    View stepView;
    @ViewInject(R.id.confirmPswEt)
    EditText confirmPswEt;
    @ViewInject(R.id.newPswEt)
    EditText newPswEt;
    @ViewInject(R.id.pswView)
    View pswView;
    @ViewInject(R.id.phoneView)
    View phoneView;
    Context context;
    ForgetPasswordPresenterImpl forgetPasswordPresenter;
    @ViewInject(R.id.forgetPswBImg)
    ImageView forgetPswBImg;
    @ViewInject(R.id.forgetPswBLine)
    View forgetPswBLine;
    @ViewInject(R.id.forgetPswCImg)
    ImageView forgetPswCImg;
    @ViewInject(R.id.forgetPswCLine)
    View forgetPswCLine;
    int step = 1;

    @Override
    protected void initialize() {
        context = this;
        titleTitleTv.setText("忘记密码");
        forgetPasswordBtn.setIndeterminateProgressMode(true);
        forgetPasswordBtn.setText("下一步");
        forgetPasswordBtn.setIdleText("下一步");
        forgetPasswordPresenter = new ForgetPasswordPresenterImpl(this, this);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg
            , R.id.forgetPasswordBtn, R.id.getCodeMsgTv})
    private void forgetPasswordClick(View view) {
        String phone = phoneEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.forgetPasswordBtn:
                CircularProgressButtonTools.showLoding(forgetPasswordBtn);
                if (step == 1) {
                    String code = codeEt.getText().toString().trim();
                    if (StringUtil.isEmpty(phone)) {
                        getCodeFails(getString(R.string.phone_err));
                        CircularProgressButtonTools.showErr(forgetPasswordBtn);
                        return;
                    } else if (forgetPasswordPresenter.verificationCode(code))
                        next();
                    else {
                        getCodeFails(getString(R.string.code_err));
                        CircularProgressButtonTools.showErr(forgetPasswordBtn);
                    }
                } else if (step == 2) {
                    String newPsw = newPswEt.getText().toString().trim();
                    String confirmPsw = confirmPswEt.getText().toString().trim();
                    if (StringUtil.isEmpty(newPsw) || StringUtil.isEmpty(confirmPsw)) {
                        getCodeFails("密码为必填");
                        CircularProgressButtonTools.showErr(forgetPasswordBtn);
                        return;
                    }
                    if (StringUtil.isEquals(newPsw, confirmPsw)) {
                        forgetPasswordPresenter.resetPassword(newPsw, phone);
                    } else {
                        getCodeFails("两次密码不一致");
                        CircularProgressButtonTools.showErr(forgetPasswordBtn);
                    }
                } else if (step == 3) {
                    next();
                }

                break;
            case R.id.getCodeMsgTv:
                if (StringUtil.isTelPhone(phone)) {
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long l) {
                            getCodeMsgTv.setClickable(false);
                            getCodeMsgTv.setText(l / 1000 + "秒后重新获取");
                        }

                        @Override
                        public void onFinish() {
                            getCodeMsgTv.setClickable(true);
                            getCodeMsgTv.setText("重新获取");
                        }
                    }.start();
                    forgetPasswordPresenter.getCodeMsg(phone);
                } else {
                    getCodeFails(getString(R.string.phone_err));
                }
                break;
        }
    }

    /***
     * 下一步
     */
    private void next() {
        if (step == 1) {
            CircularProgressButtonTools.showTrue(forgetPasswordBtn);
            CircularProgressButtonTools.reduction(forgetPasswordBtn);
            pswView.setVisibility(View.VISIBLE);
            phoneView.setVisibility(View.GONE);
            forgetPswBImg.setImageResource(R.mipmap.wang_ji_mi_ma_ob);
            forgetPswBLine.setBackgroundResource(R.color.appBlue);
        } else if (step == 2) {
            CircularProgressButtonTools.showTrue(forgetPasswordBtn);
            CircularProgressButtonTools.reduction(forgetPasswordBtn);
            successTv.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.GONE);
            forgetPasswordBtn.setText("完成");
            forgetPasswordBtn.setIdleText("完成");
            forgetPswCImg.setImageResource(R.mipmap.wang_ji_mi_ma_oc);
            forgetPswCLine.setBackgroundResource(R.color.appBlue);
        } else if (step == 3) {
            CircularProgressButtonTools.showTrue(forgetPasswordBtn);
            CircularProgressButtonTools.reduction(forgetPasswordBtn);
            finish();
        }
        step++;
    }

    @Override
    public void getCodeSuccess(String s) {
        CustomToast.customToast(true, s, context);
    }

    @Override
    public void getCodeFails(String s) {
        CustomToast.customToast(false, s, context);
    }

    @Override
    public void resetPswSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
        next();
    }

    @Override
    public void resetPswFails(String err) {
        CustomToast.customToast(false, err, context);
        CircularProgressButtonTools.showTrue(forgetPasswordBtn);
        CircularProgressButtonTools.reduction(forgetPasswordBtn);
    }
}
