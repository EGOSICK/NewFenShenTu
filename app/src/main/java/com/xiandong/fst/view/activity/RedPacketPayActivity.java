package com.xiandong.fst.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.jungly.gridpasswordview.GridPasswordView;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.RedPacketPayBean;
import com.xiandong.fst.presenter.PayPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.utils.alipayutils.AliPayListener;
import com.xiandong.fst.utils.alipayutils.AliPayUtils;
import com.xiandong.fst.utils.wxpayutils.WXPayListener;
import com.xiandong.fst.utils.wxpayutils.WXPayListenerManger;
import com.xiandong.fst.utils.wxpayutils.WXPayUtils;
import com.xiandong.fst.view.PayView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 红包支付
 */

@ContentView(R.layout.activity_pay)
public class RedPacketPayActivity extends AbsBaseActivity implements PayView {
    @ViewInject(R.id.payBtn)
    CircularProgressButton payBtn;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.payMoneyTv)
    TextView payMoneyTv;
    @ViewInject(R.id.payRg)
    RadioGroup payRg;
    @ViewInject(R.id.payZFBWayRb)
    RadioButton payZFBWayRb;
    Context context;
    RedPacketPayBean payBean;
    Dialog dialog;

    @Override
    protected void initialize() {
        initView();
        initData();
    }

    private void initView() {
        payBtn.setText("完成");
        payBtn.setIdleText("完成");
        titleTitleTv.setText("支付方式");
    }

    private void initData() {
        context = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("payBean");
        payBean = bundle.getParcelable("payBean");
        payMoneyTv.setText("订单支付" + payBean.getTotalFee() + "元");
        payRg.check(payZFBWayRb.getId());
    }

    @Event(type = RadioButton.OnCheckedChangeListener.class, value = {
            R.id.payWXWayRb, R.id.payZFBWayRb, R.id.payYUEWayRb})
    private void choosePayWay(CompoundButton btn, boolean isCheck) {
        if (isCheck) {
            switch (btn.getId()) {
                case R.id.payZFBWayRb:
                    payBean.setType(Constant.ZFBPAY);
                    break;
                case R.id.payWXWayRb:
                    payBean.setType(Constant.WXPAY);
                    break;
                case R.id.payYUEWayRb:
                    payBean.setType(Constant.YUEPAY);
                    break;
            }
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.payBtn})
    private void payClassOnClick(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.payBtn:
                final PayPresenterImpl payPresenter = new PayPresenterImpl(this);
                if (payBean.getType() == Constant.YUEPAY){
                    dialog = StyledDialogTools.showPayPswDialog("输入支付密码", context, new GridPasswordView.OnPasswordChangedListener() {
                        @Override
                        public void onTextChanged(String psw) {
                        }

                        @Override
                        public void onInputFinish(String psw) {
                            dialog.dismiss();
                            if (StringUtil.isEquals(psw, AppDbManager.getLastUser().getUserPayPsw())) {
                                payPresenter.getRedPacketOrderId(payBean);
                            } else {
                                payFails("密码错误");
                            }
                        }
                    });
                }else {
                    payPresenter.getRedPacketOrderId(payBean);
                }
                break;
        }
    }

    private void pay() {
        switch (payBean.getType()) {
            case Constant.WXPAY:
                WXPayUtils wx = new WXPayUtils(this, Constant.NOTIFAURL);
                wx.registerAPP();
                wx.pay("分身兔", payBean.getTotalFee(), payBean.getOrderId());
                WXPayListenerManger.getInstance().regisnListener(new WXPayListener() {
                    @Override
                    public void wxPaySuccess() {
                        paySuccess("微信支付成功");
                    }

                    @Override
                    public void wxPayFails(String err) {
                        payFails(err);
                    }
                });
                break;
            case Constant.ZFBPAY:
                AliPayUtils ali = new AliPayUtils(context);
                ali.pay("分身兔", "红包", payBean.getTotalFee(), payBean.getOrderId(),
                        new AliPayListener() {
                            @Override
                            public void aliPaySuccess() {
                                paySuccess("支付宝支付成功");
                            }

                            @Override
                            public void aliPayFails(String err) {
                                payFails(err);
                            }
                        });
                break;
            case Constant.YUEPAY:
                paySuccess("余额支付成功");
                break;
        }
    }

    public void paySuccess(String msg) {
        setResult(0);
        CustomToast.customToast(true, msg, context);
        finish();
    }

    public void payFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void getOrderIdSuccess(String orderId) {
        payBean.setOrderId(orderId);
        pay();
    }

    @Override
    public void getOrderIdFails(String err) {
        CustomToast.customToast(false, err, context);
    }
}
