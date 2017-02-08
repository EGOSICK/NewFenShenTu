package com.xiandong.fst.model;


import com.xiandong.fst.model.bean.PayBean;
import com.xiandong.fst.presenter.BillingPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;

/**
 * Created by dell on 2017/01/09
 */

public class BillingModelImpl implements BillingModel {

    @Override
    public void billing(String position, String address, String title, String money, String time,
                        String phone, String detial, BillingPresenter billingPresenter) {
        if (!StringUtil.isEmpty(position)) {
            if (!StringUtil.isEmpty(address)) {
                if (!StringUtil.isEmpty(title)) {
                    if (!StringUtil.isEmpty(money) && Double.valueOf(money) >= 1) {
                        if (!StringUtil.isEmpty(time)) {
                            if (StringUtil.isTelPhone(phone)) {
                                if (!StringUtil.isEmpty(detial)) {
                                    PayBean payBean = new PayBean();
                                    payBean.setAddress(address);
                                    payBean.setTitle(title);
                                    payBean.setMoney(money);
                                    payBean.setTime(time);
                                    payBean.setPhone(phone);
                                    payBean.setDetail(detial);
                                    payBean.setUid(AppDbManager.getLastUser().getUserId());
                                    payBean.setPosition(position);
                                    billingPresenter.billingMsgSce(payBean);
                                } else {
                                    billingPresenter.billingMsgErr("请填写订单详情");
                                }
                            } else {
                                billingPresenter.billingMsgErr("订单手机号码格式不正确");
                            }
                        } else {
                            billingPresenter.billingMsgErr("请选择订单到期时间");
                        }
                    } else {
                        billingPresenter.billingMsgErr("订单金额不能少于1");
                    }
                } else {
                    billingPresenter.billingMsgErr("请填写订单主题");
                }
            } else {
                billingPresenter.billingMsgErr("请填写订单地址");
            }
        } else {
            billingPresenter.billingMsgErr("位置信息获取失败,请重试");
        }
    }
}