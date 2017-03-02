package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.RedPacketPayBean;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;

/**
 * 发红包
 */
@ContentView(R.layout.activity_send_red_packet)
public class SendRedPacketActivity extends AbsBaseActivity {
    @ViewInject(R.id.titleView)
    View titleView;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.sendRedPacketBtn)
    CircularProgressButton sendRedPacketBtn;
    @ViewInject(R.id.redPacketNumEt)
    EditText redPacketNumEt;
    @ViewInject(R.id.redPacketMoneyEt)
    EditText redPacketMoneyEt;
    @ViewInject(R.id.redPacketDistanceEt)
    EditText redPacketDistanceEt;
    @ViewInject(R.id.redPacketMoneyTv)
    TextView redPacketMoneyTv;
    String redPacketNum;
    String redPacketMoney;
    String redPacketDistance;
    boolean isRedPacketNum = false;
    boolean isRedPacketMoney = false;
    boolean isRedPacketDistance = true;
    Context context;

    @Override
    protected void initialize() {
        initView();
        editTextListener();
        updateMoney();
    }

    private void initView() {
        sendRedPacketBtn.setIndeterminateProgressMode(true);
        titleView.setBackgroundColor(0xFFE5494A);
        titleTitleTv.setText("发红包");
        titleTitleTv.setTextColor(0xFFFFFFFF);
        titleBackImg.setImageResource(R.mipmap.title_back_white);
        context = this;
    }

    private void editTextListener() {
        redPacketNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0 &&
                        !StringUtil.isEmpty(editable.toString())) {
                    redPacketNum = editable.toString();
                    isRedPacketNum = true;
                } else {
                    isRedPacketNum = false;
                }
                updateMoney();
            }
        });
        redPacketMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0 &&
                        !StringUtil.isEmpty(editable.toString())) {
                    redPacketMoney = editable.toString();
                    isRedPacketMoney = true;
                } else {
                    isRedPacketMoney = false;
                }
                updateMoney();
            }
        });

        redPacketDistanceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0 &&
                        !StringUtil.isEmpty(editable.toString())) {
                    redPacketDistance = editable.toString();
                    isRedPacketDistance = true;
                } else {
                    isRedPacketDistance = false;
                }
                updateMoney();
            }
        });
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.sendRedPacketBtn})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.sendRedPacketBtn:
                if (StringUtil.isEmpty(redPacketDistance) || redPacketDistance.length() <= 0){
                    CustomToast.customToast(false,"请输入红包可以领取距离",context);
                    return;
                }
                double jl = Double.parseDouble(redPacketDistance);
                if (jl < 500){
                    CustomToast.customToast(false,"请输入500米以上的距离",context);
                }else {
                    CircularProgressButtonTools.showLoding(sendRedPacketBtn);
                    RedPacketPayBean payBean = new RedPacketPayBean();
                    payBean.setTotalCount(redPacketNum);
                    payBean.setDistance(redPacketDistance);
                    payBean.setTotalFee(redPacketMoney);
                    payBean.setUid(AppDbManager.getLastUser().getUserId());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("payBean", payBean);
                    startActivityForResult(new Intent(context, RedPacketPayActivity.class)
                            .putExtra("payBean", bundle)
                            .setAction(Constant.REDPACKETPAY), 0);
                }

                break;
        }
    }

    private void updateMoney() {
        if (isRedPacketNum && isRedPacketMoney && isRedPacketDistance) {
            double money = Double.parseDouble(redPacketMoney);
            redPacketMoneyTv.setText("￥" + new DecimalFormat("#0.00").format(money));
            sendRedPacketBtn.setClickable(true);
        } else {
            redPacketMoneyTv.setText("￥0.00");
            sendRedPacketBtn.setClickable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            finish();
            CircularProgressButtonTools.showTrue(sendRedPacketBtn);
        } else {
            CircularProgressButtonTools.showErr(sendRedPacketBtn);
        }
    }
}
