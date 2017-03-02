package com.xiandong.fst.view.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
    double mey;

    @Override
    protected void initialize() {
        titleTitleTv.setText("提现");
        context = this;
        withdrawalPresenter = new WithdrawalPresenterImpl(this);
        withdrawalBtn.setText("完成");
        withdrawalBtn.setIdleText("完成");
        withdrawalBtn.setIndeterminateProgressMode(true);
        entity = AppDbManager.getLastUser();
        mey = Double.parseDouble(entity.getUserBalance());
        haveMoneyTv.setText("余额 : " + entity.getUserBalance() + "元");

        withdrawalEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    CharSequence str = charSequence.toString().subSequence(0, 1);
                    if (StringUtil.isEquals(str.toString(), "0") ||
                            StringUtil.isEquals(str.toString(), ".")) {
                        withdrawalEt.setText("");
                        withdrawalEt.setSelection(0);
                    } else {
                        if (charSequence.toString().contains(".")) {
                            if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                                charSequence = charSequence.toString().subSequence(0, charSequence.toString().indexOf(".") + 3);
                                withdrawalEt.setText(charSequence);
                                withdrawalEt.setSelection(charSequence.length());
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    CharSequence str = editable.toString().subSequence(0, 1);
                    if (StringUtil.isEquals(str.toString(), "0") ||
                            StringUtil.isEquals(str.toString(), ".")) {
                    } else {
                        double editMey = Double.parseDouble(editable.toString());
                        if (editMey > mey) {
                            haveMoneyTv.setText("输入余额超过当前余额");
                        } else {
                            if (editMey > 1) {
                                if (editMey < 100) {
                                    haveMoneyTv.setText("提现手续费为1元");
                                } else {
                                    haveMoneyTv.setText("提现100元以上无手续费");
                                }
                            } else {
                                haveMoneyTv.setText("提现金额需大于1元");
                            }
                        }
                    }
                } else {
                    haveMoneyTv.setText("余额 : " + mey + "元");
                }
            }
        });


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
                if (mey <= 1) {
                    CustomToast.customToast(false, "提现金额需大于1元", context);
                } else if (mey < 100) {
                    withdrawalEt.setText(String.valueOf(mey - 1));
                } else {
                    withdrawalEt.setText(String.valueOf(mey));
                }
                break;
            case R.id.withdrawalBtn:
                account = withdrawalAccountEt.getText().toString().trim();
                phone = withdrawalPhoneEt.getText().toString().trim();
                name = withdrawalNameEt.getText().toString().trim();
                money = withdrawalEt.getText().toString().trim();
                double txMoney = Double.parseDouble(money);
                if (txMoney <= 1) {
                    return;
                }else if (txMoney > mey){
                    return;
                }

                if (StringUtil.isEmpty(entity.getUserPayPsw())) {
                    ////////// 设置支付密码
                    withdrawalPresenter.setPayPassword(context);
                } else {
                    if (txMoney <= mey - 1) {
                        withdrawalPresenter.withdrawal(account, name, phone, money, context);
                    } else {
                        withdrawalPresenter.withdrawal(account, name, phone,
                                String.valueOf(mey - 1), context);
                    }
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
