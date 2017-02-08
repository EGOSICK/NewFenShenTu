package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MoneyRecordBean;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.MoneyRecordPresenterImpl;
import com.xiandong.fst.presenter.UserMessagePresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.MoneyRecordView;
import com.xiandong.fst.view.UserMessageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 我的钱包
 */

@ContentView(R.layout.activity_my_purse)
public class MyWalletActivity extends AbsBaseActivity implements UserMessageView {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.withdrawalsBtn)
    CircularProgressButton withdrawalsBtn;
    @ViewInject(R.id.myMoneyTv)
    TextView myMoneyTv;
    @ViewInject(R.id.yongJinJiLuView)
    View yongJinJiLuView;
    @ViewInject(R.id.tiXianJiLuView)
    View tiXianJiLuView;
    @ViewInject(R.id.xiaoFeiMingXiView)
    View xiaoFeiMingXiView;

    Context context;

    @Override
    protected void initialize() {
        context = this;
        initView();
        initData();
        initNetWork();
    }

    private void initView() {
        titleTitleTv.setText("我的钱包");
        withdrawalsBtn.setText("提现");
        withdrawalsBtn.setIdleText("提现");
    }

    private void initData() {
        UserEntity user = AppDbManager.getLastUser();
        if (user != null && !StringUtil.isEmpty(user.getUserBalance()))
            myMoneyTv.setText(user.getUserBalance());
    }

    private void initNetWork() {
        UserMessagePresenterImpl presenter = new UserMessagePresenterImpl(this);
        presenter.getUserMessage();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.withdrawalsBtn,
            R.id.yongJinJiLuView, R.id.tiXianJiLuView,R.id.xiaoFeiMingXiView})
    private void mYWalletOnClick(View v) {
        switch (v.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.withdrawalsBtn:
//                CircularProgressButtonTools.showLoding(withdrawalsBtn);
                startActivity(new Intent(context , WithdrawalActivity.class));
                break;
            case R.id.yongJinJiLuView:
                startActivity(new Intent(this , MyWalletDetailsActivity.class)
                        .putExtra("act","2"));
                break;
            case R.id.tiXianJiLuView:
                startActivity(new Intent(this , MyWalletDetailsActivity.class)
                        .putExtra("act","3"));
                break;
            case R.id.xiaoFeiMingXiView:
                startActivity(new Intent(this , MyWalletDetailsActivity.class)
                        .putExtra("act","0"));
                break;
        }
    }


    @Override
    public void getUserMessageSuccess(UserBean userBean) {
        myMoneyTv.setText(userBean.getUser().getYue());
    }

    @Override
    public void getUserMessageFails(String err) {
        CustomToast.customToast(false, err, this);
    }

}
