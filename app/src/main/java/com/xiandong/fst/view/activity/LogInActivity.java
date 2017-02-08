package com.xiandong.fst.view.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.LogInPresenterImpl;
import com.xiandong.fst.presenter.RegisterPresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CostomAnimation;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.GetVerificationCodeView;
import com.xiandong.fst.view.LogInView;
import com.xiandong.fst.view.RegisterView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 登录界面
 */

@ContentView(R.layout.activity_login)
public class LogInActivity extends AbsBaseActivity implements LogInView, RegisterView, GetVerificationCodeView {

    Context context;
    @ViewInject(R.id.logInPhoneEt)
    EditText logInPhoneEt;
    @ViewInject(R.id.logInPasswordEt)
    EditText logInPasswordEt;
    @ViewInject(R.id.logInCodeEt)
    EditText logInCodeEt;
    @ViewInject(R.id.logInLogInBtn)
    CircularProgressButton logInLogInBtn;
    @ViewInject(R.id.weXinLogInBtn)
    ImageView weXinLogInBtn;
    @ViewInject(R.id.forgetPasswordTv)
    TextView forgetPasswordTv;
    @ViewInject(R.id.goToRegisterTv)
    TextView goToRegisterTv;
    @ViewInject(R.id.userAgreementView)
    View userAgreementView;
    @ViewInject(R.id.userAgreementCb)
    CheckBox userAgreementCb;
    @ViewInject(R.id.userAgreementTv)
    TextView userAgreementTv;
    @ViewInject(R.id.logInCodeView)
    View logInCodeView;
    @ViewInject(R.id.logInPhoneView)
    View logInPhoneView;
    @ViewInject(R.id.otherLogInView)
    View otherLogInView;
    @ViewInject(R.id.getCodeMsgTv)
    TextView getCodeMsgTv;
    @ViewInject(R.id.logInUserImg)
    ImageView logInUserImg;

    LogInPresenterImpl logInPresenter;
    RegisterPresenterImpl registerPresenter;
    boolean isRegister = false, isWXLogIn = false;
    int height, width;
    long executionTime = Constant.EXECUTIONTIME;

    @Override
    protected void initialize() {
        context = this;
        logInPresenter = new LogInPresenterImpl(this);
        registerPresenter = new RegisterPresenterImpl(this, this);
        logInLogInBtn.setIndeterminateProgressMode(true);
        logInLogInBtn.setText("登录");
        logInLogInBtn.setIdleText("登录");
        getViewHeight();

        UserEntity user = AppDbManager.getLastUser();
        if (user != null) {
            if (StringUtil.isEmpty(user.getUserImg()))
                return;
            XCircleImgTools.setCircleImg(logInUserImg, user.getUserImg());
            logInPhoneEt.setText(user.getUserPhone());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isWXLogIn)
            logInPresenter.wxLogIn();
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

    private void logIn() {
//        Pair<View, String>[] pairs = SharedAnimationUtils.
//                createSafeTransitionParticipants(this, false, logInUserImg);
//        ActivityOptionsCompat transitionActivityOptions =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
//        transitionActivityOptions.toBundle()
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }


    @Event(type = View.OnClickListener.class, value = {R.id.logInLogInBtn,
            R.id.weXinLogInBtn, R.id.forgetPasswordTv, R.id.goToRegisterTv,
            R.id.getCodeMsgTv})
    private void logIn(View view) {
        switch (view.getId()) {
            case R.id.logInLogInBtn:   // 注册或登录
                if (isRegister) {
                    CircularProgressButtonTools.showLoding(logInLogInBtn);
                    registerPresenter.register(
                            logInPhoneEt.getText().toString().trim(),
                            logInCodeEt.getText().toString().trim(),
                            logInPasswordEt.getText().toString().trim(),
                            userAgreementCb.isChecked());
                } else {
                    CircularProgressButtonTools.showLoding(logInLogInBtn);
                    logInPresenter.logIn();
                }
                break;
            case R.id.weXinLogInBtn:   // 微信登录
                WXLogInStart();
                break;
            case R.id.forgetPasswordTv:  // 忘记密码
                startActivity(new Intent(context, ForgetPasswordActivity.class));
                break;
            case R.id.goToRegisterTv: // 去注册或去登录
                if (isRegister)
                    goLogInAnimator();
                else
                    goRegisterAnimator();
                break;
            case R.id.getCodeMsgTv:  // 获取验证码
                String phone = logInPhoneEt.getText().toString().trim();
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
                    registerPresenter.getCodeMsg(phone);
                } else {
                    getCodeFails(getString(R.string.phone_err));
                }
                break;
        }
    }

    @Override
    public void showToast(String s) {
        CustomToast.customToast(false, s, this);
        CircularProgressButtonTools.showErr(logInLogInBtn);
    }

    @Override
    public void success() {
        CircularProgressButtonTools.showTrue(logInLogInBtn);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logIn();
            }
        }, executionTime * 2);
    }

    @Override
    public String getName() {
        return logInPhoneEt.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return logInPasswordEt.getText().toString().trim();
    }

    @Override
    public void wxLogInSuccess() {
        logIn();
        WXLogInComplete();
    }

    @Override
    public void wxLogInFails(String err) {
        WXLogInComplete();
    }

    @Override
    public void wxLogInBingDingPhone() {
        WXLogInComplete();
        startActivity(new Intent(context, BingDinPhoneNumActivity.class));
    }

    private void getViewHeight() {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        logInPhoneView.measure(w, h);
        height = logInPhoneView.getMeasuredHeight();
        width = logInPhoneView.getMeasuredWidth();
    }

    private void goRegisterAnimator() {
        ObjectAnimator or = ObjectAnimator.ofFloat(forgetPasswordTv,
                "translationX", 0, -width / 2).setDuration(executionTime);
        ObjectAnimator or1 = ObjectAnimator.ofFloat(forgetPasswordTv,
                "alpha", 1f, 0f).setDuration(executionTime);
        or.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                CircularProgressButtonTools.reduction(logInLogInBtn);
                goToRegisterTv.setClickable(false);
                forgetPasswordTv.setClickable(false);
                isRegister = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                forgetPasswordTv.setAlpha(0);
                userAgreementView.setClickable(true);
                userAgreementCb.setClickable(true);
                userAgreementTv.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator or2 = ObjectAnimator.ofFloat(userAgreementView,
                "translationX", width / 2, 0).setDuration(executionTime);
        ObjectAnimator or3 = ObjectAnimator.ofFloat(userAgreementView,
                "alpha", 0f, 1f).setDuration(executionTime);
        ObjectAnimator or4 = ObjectAnimator.ofFloat(goToRegisterTv,
                "alpha", 1f, 0f).setDuration(executionTime);
        ObjectAnimator or5 = ObjectAnimator.ofFloat(goToRegisterTv,
                "alpha", 0f, 1f).setDuration(executionTime);
        or5.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                goToRegisterTv.setText("去登录");
                logInLogInBtn.setText("注册");
                logInLogInBtn.setIdleText("注册");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                goToRegisterTv.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        ObjectAnimator or6 = ObjectAnimator.ofFloat(otherLogInView,
                "alpha", 1f, 0f).setDuration(executionTime * 2);
        or6.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                otherLogInView.setClickable(false);
                weXinLogInBtn.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        CostomAnimation.zoomAnimate(goToRegisterTv, logInCodeView, 0, height, "height", executionTime * 2);
        AnimatorSet set = new AnimatorSet();
        set.play(or).with(or1).before(or2).before(or3).with(or4).before(or5).with(or6);
        set.start();
    }

    private void goLogInAnimator() {
        ObjectAnimator or = ObjectAnimator.ofFloat(forgetPasswordTv,
                "translationX", -width / 2, 0).setDuration(executionTime);
        ObjectAnimator or1 = ObjectAnimator.ofFloat(forgetPasswordTv,
                "alpha", 0f, 1f).setDuration(executionTime);
        or.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                CircularProgressButtonTools.reduction(logInLogInBtn);
                goToRegisterTv.setClickable(false);
                userAgreementView.setClickable(false);
                userAgreementCb.setClickable(false);
                userAgreementTv.setClickable(false);
                isRegister = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                forgetPasswordTv.setAlpha(1);
                forgetPasswordTv.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        ObjectAnimator or2 = ObjectAnimator.ofFloat(userAgreementView,
                "translationX", 0, width / 2).setDuration(executionTime);
        ObjectAnimator or3 = ObjectAnimator.ofFloat(userAgreementView,
                "alpha", 1f, 0f).setDuration(executionTime);
        ObjectAnimator or4 = ObjectAnimator.ofFloat(goToRegisterTv,
                "alpha", 1f, 0f).setDuration(executionTime);
        ObjectAnimator or5 = ObjectAnimator.ofFloat(goToRegisterTv,
                "alpha", 0f, 1f).setDuration(executionTime);
        or5.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                goToRegisterTv.setText("去注册");
                logInLogInBtn.setText("登录");
                logInLogInBtn.setIdleText("登录");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                goToRegisterTv.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator or6 = ObjectAnimator.ofFloat(otherLogInView,
                "alpha", 0f, 1f).setDuration(executionTime * 2);
        or6.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                otherLogInView.setClickable(true);
                weXinLogInBtn.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(or2).with(or3).before(or).before(or1).with(or4).before(or5).with(or6);
        set.start();
        CostomAnimation.zoomAnimate(goToRegisterTv, logInCodeView, height, 0, "height", executionTime * 2);
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
    public void registerSuccess() {
        CustomToast.customToast(false, "注册成功", context);
        CircularProgressButtonTools.showTrue(logInLogInBtn);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logIn();
            }
        }, executionTime * 2);
    }

    @Override
    public void registerFails(String err) {
        CustomToast.customToast(false, err, context);
        CircularProgressButtonTools.showErr(logInLogInBtn);
    }

}
