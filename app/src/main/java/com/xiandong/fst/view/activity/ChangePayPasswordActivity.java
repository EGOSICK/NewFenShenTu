package com.xiandong.fst.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.presenter.ChangeUserMessagePresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.ChangeUserMessageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/17.
 */
@ContentView(R.layout.activity_change_pay_password)
public class ChangePayPasswordActivity extends AbsBaseActivity {
    @ViewInject(R.id.payPasswordBtn)
    CircularProgressButton payPasswordBtn;
    @ViewInject(R.id.payOriginalPasswordEt)
    EditText payOriginalPasswordEt;
    @ViewInject(R.id.payConfirmPasswordEt)
    EditText payConfirmPasswordEt;
    @ViewInject(R.id.payYuanPasswordView)
    View payYuanPasswordView;
    @ViewInject(R.id.payYuanPasswordEt)
    EditText payYuanPasswordEt;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    Context context;

    String zfPassword;

    @Override
    protected void initialize() {
        initView();
        initData();
    }

    private void initView() {
        context = this;
        payPasswordBtn.setIndeterminateProgressMode(true);
        titleTitleTv.setText("支付密码");
        payPasswordBtn.setText("完成");
        payPasswordBtn.setIdleText("完成");
    }

    private void initData() {
        if (StringUtil.isEmpty(AppDbManager.getLastUser().getUserPayPsw())) {
            zfPassword = "";
            payYuanPasswordView.setVisibility(View.GONE);
        } else {
            zfPassword = AppDbManager.getLastUser().getUserPayPsw();
            payYuanPasswordView.setVisibility(View.VISIBLE);
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.payPasswordBtn, R.id.titleBackImg})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.payPasswordBtn:
                String payOriginal = payOriginalPasswordEt.getText().toString().trim();
                String payConfirm = payConfirmPasswordEt.getText().toString().trim();
                String payYuan = payYuanPasswordEt.getText().toString().trim();

                if (StringUtil.isEmpty(payOriginal) || StringUtil.isEmpty(payConfirm)) {
                    CustomToast.customToast(false, "密码不能为空", context);
                    CircularProgressButtonTools.showErr(payPasswordBtn);
                    return;
                }
                if (payOriginal.length() == 6 && payConfirm.length() == 6) {

                    if (StringUtil.isEmpty(zfPassword)) {
                        if (StringUtil.isEquals(payConfirm, payOriginal)) {
                            ChangeUserMessagePresenterImpl presenter = new ChangeUserMessagePresenterImpl(new ChangeUserMessageView() {
                                @Override
                                public void changeUserMsgSuccess() {
                                    CustomToast.customToast(true, "支付密码设置成功", context);
                                    CircularProgressButtonTools.showTrue(payPasswordBtn);
                                    finish();
                                }

                                @Override
                                public void changeUserMsgFails(String err) {
                                    CustomToast.customToast(false, err, context);
                                    CircularProgressButtonTools.showErr(payPasswordBtn);
                                }
                            });
                            presenter.changeUserMessage("", "", "", payConfirm);
                        } else {
                            CustomToast.customToast(false, "两次密码不同", context);
                            CircularProgressButtonTools.showErr(payPasswordBtn);
                        }
                    } else {
                        if (StringUtil.isEquals(payYuan, zfPassword)) {
                            if (StringUtil.isEquals(payConfirm, payOriginal)) {
                                ChangeUserMessagePresenterImpl presenter = new ChangeUserMessagePresenterImpl(new ChangeUserMessageView() {
                                    @Override
                                    public void changeUserMsgSuccess() {
                                        CustomToast.customToast(true, "支付密码设置成功", context);
                                        CircularProgressButtonTools.showTrue(payPasswordBtn);
                                        finish();
                                    }

                                    @Override
                                    public void changeUserMsgFails(String err) {
                                        CustomToast.customToast(false, err, context);
                                        CircularProgressButtonTools.showErr(payPasswordBtn);
                                    }
                                });
                                presenter.changeUserMessage("", "", "", payConfirm);
                            } else {
                                CustomToast.customToast(false, "两次密码不同", context);
                                CircularProgressButtonTools.showErr(payPasswordBtn);
                            }
                        } else {
                            CustomToast.customToast(false, "原密码不正确", context);
                            CircularProgressButtonTools.showErr(payPasswordBtn);
                        }
                    }


                } else {
                    CustomToast.customToast(false, "支付密码请设置为6位", context);
                    CircularProgressButtonTools.showErr(payPasswordBtn);
                }
                CircularProgressButtonTools.showLoding(payPasswordBtn);
                break;
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
