package com.xiandong.fst.presenter;


import android.app.Dialog;
import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jungly.gridpasswordview.GridPasswordView;
import com.xiandong.fst.R;
import com.xiandong.fst.model.WithdrawalModel;
import com.xiandong.fst.model.WithdrawalModelImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.WithdrawalView;

/**
 * Created by dell on 2017/01/20
 */

public class WithdrawalPresenterImpl implements WithdrawalPresenter {
    WithdrawalModel model;
    WithdrawalView view;
    Dialog dialog;
    public WithdrawalPresenterImpl(WithdrawalView view) {
        this.view = view;
        this.model = new WithdrawalModelImpl();
    }

    public void withdrawal(final String zh, final String mz, final String sj, final String je, final Context context) {
        if (StringUtil.isEmpty(zh)) {
            CustomToast.customToast(false, "账号不可为空", context);
        } else {
            if (StringUtil.isEmpty(mz)) {
                CustomToast.customToast(false, "姓名不可为空", context);
            } else {
                if (!StringUtil.isTelPhone(sj)) {
                    CustomToast.customToast(false, context.getString(R.string.phone_err), context);
                } else {
                    if (StringUtil.isEmpty(je)) {
                        CustomToast.customToast(false, "请输入要提现的金额", context);
                    } else {
                        dialog = StyledDialogTools.showPayPswDialog("输入密码",context, new GridPasswordView.OnPasswordChangedListener() {
                            @Override
                            public void onTextChanged(String psw) {}
                            @Override
                            public void onInputFinish(String psw) {
                                if (StringUtil.isEquals(psw, AppDbManager.getLastUser().getUserPayPsw())) {
                                    model.withdrawal(zh, mz, sj, je, psw, WithdrawalPresenterImpl.this);
                                } else {
                                    CustomToast.customToast(false, "密码不正确", context);
                                }
//                                StyledDialogTools.disMissStyleDialog();
//                                StyledDialogTools.disMissDialog();
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }
        }
    }

    public void setPayPassword(Context context) {
        dialog = StyledDialogTools.showPayPswDialog("创建支付密码",context, new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {}
            @Override
            public void onInputFinish(String psw) {
                model.creatPayPsw(psw, WithdrawalPresenterImpl.this);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void withdrawalSuccess(String msg) {
        view.withdrawalSuccess(msg);
    }

    @Override
    public void withdrawalFails(String err) {
        view.withdrawalFails(err);
    }

    @Override
    public void setPayPswSuccess(String msg) {
        view.setPayPswSuccess(msg);
    }
}