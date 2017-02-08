package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.MoneyRecordBean;
import com.xiandong.fst.presenter.MoneyRecordPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/01/18
 */

public class MoneyRecordModelImpl implements MoneyRecordModel {

    @Override
    public void getMoneyRecord(final MoneyRecordPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "jilu");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        MoneyRecordBean moneyRecordBean = new MoneyRecordBean();
                        try {
                            List<MoneyRecordBean.TXDetails> txList = new ArrayList<>();
                            List<MoneyRecordBean.XFDetails> xfList = new ArrayList<>();
                            List<MoneyRecordBean.YJDetails> yjList = new ArrayList<>();
                            moneyRecordBean.setTXDetails(txList);
                            moneyRecordBean.setXFDetails(xfList);
                            moneyRecordBean.setYJDetails(yjList);
                            JSONObject jl = new JSONObject(result);
                            JSONArray jsonArray = jl.getJSONArray("jl");
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject one = jsonArray.getJSONObject(i);
                                    switch (one.getString("act")) {
                                        case "2": //佣金
                                            MoneyRecordBean.YJDetails yjDetails =
                                                    new MoneyRecordBean.YJDetails();
                                            yjDetails.setImg(one.getString("img"));
                                            yjDetails.setMoney(one.getString("money"));
                                            yjDetails.setPhone(one.getString("nicheng"));
                                            yjDetails.setTimeLine(one.getString("timeline"));
//                                            yjDetails.setUid(one.getString("uid"));
//                                            yjDetails.setUser_id(one.getString("user_id"));
                                            yjList.add(yjDetails);

                                            MoneyRecordBean.XFDetails xfDetailst =
                                                    new MoneyRecordBean.XFDetails();
                                            xfDetailst.setMoney(one.getString("money"));
                                            xfDetailst.setTimeLine(one.getString("timeline"));
//                                            xfDetailst.setUid(one.getString("uid"));
//                                            xfDetailst.setUser_id(one.getString("user_id"));
//                                            xfDetailst.setPid("");
                                            xfDetailst.setXfName("佣金");
                                            xfList.add(xfDetailst);
                                            break;
                                        case "3": // 提现
                                            MoneyRecordBean.TXDetails txDetails =
                                                    new MoneyRecordBean.TXDetails();
//                                            txDetails.setUser_id(one.getString("user_id"));
//                                            txDetails.setUid(one.getString("uid"));
                                            txDetails.setTimeLine(one.getString("timeline"));
                                            txDetails.setMoney(one.getString("money"));
                                            txList.add(txDetails);

                                            MoneyRecordBean.XFDetails xfDetailsx =
                                                    new MoneyRecordBean.XFDetails();
                                            xfDetailsx.setMoney(one.getString("money"));
                                            xfDetailsx.setTimeLine(one.getString("timeline"));
//                                            xfDetailsx.setUid(one.getString("uid"));
//                                            xfDetailsx.setUser_id(one.getString("user_id"));
//                                            xfDetailsx.setPid("");
                                            xfDetailsx.setXfName("提现");
                                            xfList.add(xfDetailsx);
                                            break;
                                        case "4": // 接单
                                            MoneyRecordBean.XFDetails xfDetailsj =
                                                    new MoneyRecordBean.XFDetails();
                                            xfDetailsj.setMoney(one.getString("money"));
                                            xfDetailsj.setTimeLine(one.getString("timeline"));
//                                            xfDetailsj.setUid(one.getString("uid"));
//                                            xfDetailsj.setUser_id(one.getString("user_id"));
//                                            xfDetailsj.setPid("");
                                            xfDetailsj.setXfName("接单");
                                            xfList.add(xfDetailsj);
                                            break;
                                        case "5": // 发单
                                            MoneyRecordBean.XFDetails xfDetailsf =
                                                    new MoneyRecordBean.XFDetails();
                                            xfDetailsf.setMoney(one.getString("money"));
                                            xfDetailsf.setTimeLine(one.getString("timeline"));
//                                            xfDetailsf.setUid(one.getString("uid"));
//                                            xfDetailsf.setUser_id(one.getString("user_id"));
//                                            xfDetailsf.setPid("");
                                            xfDetailsf.setXfName("发单");
                                            xfList.add(xfDetailsf);
                                            break;
                                        case "6": // 红包
                                            MoneyRecordBean.XFDetails xfDetailsh =
                                                    new MoneyRecordBean.XFDetails();
                                            xfDetailsh.setMoney(one.getString("money"));
                                            xfDetailsh.setTimeLine(one.getString("timeline"));
//                                            xfDetailsh.setUid(one.getString("uid"));
//                                            xfDetailsh.setUser_id(one.getString("user_id"));
//                                            xfDetailsh.setPid(one.getString("pid"));
                                            xfDetailsh.setXfName("红包");
                                            xfList.add(xfDetailsh);
                                            break;
                                    }
                                }
                                presenter.moneyRecordSuccess(moneyRecordBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            presenter.moneyRecordFails(e.getMessage());
                        }
                        break;
                    default:
                        presenter.moneyRecordFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.moneyRecordFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}