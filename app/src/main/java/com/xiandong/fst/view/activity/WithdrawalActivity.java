package com.xiandong.fst.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.WithdrawalPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.WithdrawalView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/20.
 */
@ContentView(R.layout.activity_withdrawa)
public class WithdrawalActivity extends AbsBaseActivity implements WithdrawalView {

    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.haveMoneyTv)
    TextView haveMoneyTv;
    @ViewInject(R.id.withdrawalEt)
    EditText withdrawalEt;
    @ViewInject(R.id.withdrawalBtn)
    CircularProgressButton withdrawalBtn;
    @ViewInject(R.id.withdrawalAccountEt)
    EditText withdrawalAccountEt;
    @ViewInject(R.id.withdrawalNameEt)
    EditText withdrawalNameEt;
    @ViewInject(R.id.withdrawalPhoneEt)
    EditText withdrawalPhoneEt;

    Context context;
    UserEntity entity;
    WithdrawalPresenterImpl withdrawalPresenter;
    String account, phone, name, money;

    @Override
    protected void initialize() {
        titleTitleTv.setText("提现");
        context = this;
        withdrawalPresenter = new WithdrawalPresenterImpl(this);
        withdrawalBtn.setText("完成");
        withdrawalBtn.setIdleText("完成");
        withdrawalBtn.setIndeterminateProgressMode(true);
        entity = AppDbManager.getLastUser();
        haveMoneyTv.setText(entity.getUserBalance());
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg
            , R.id.withdrawalAllTv, R.id.withdrawalRulesTv, R.id.withdrawalBtn})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.withdrawalRulesTv:

                break;
            case R.id.withdrawalAllTv:
                withdrawalEt.setText(entity.getUserBalance());
                break;
            case R.id.withdrawalBtn:
//                CircularProgressButtonTools.showLoding(withdrawalBtn);
                account = withdrawalAccountEt.getText().toString().trim();
                phone = withdrawalPhoneEt.getText().toString().trim();
                name = withdrawalNameEt.getText().toString().trim();
                money = withdrawalEt.getText().toString().trim();
                if (StringUtil.isEmpty(entity.getUserPayPsw())) {
                    ////////// 设置支付密码
                    withdrawalPresenter.setPayPassword(context);
                } else {
                    withdrawalPresenter.withdrawal(account, name, phone, money, context);
                }
                break;
        }
    }

    @Override
    public void withdrawalSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
        finish();
    }

    @Override
    public void withdrawalFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void setPayPswSuccess(String msg) {
        withdrawalPresenter.withdrawal(account, name, phone, money, context);
    }
}
