package com.xiandong.fst.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.CredibilityIntegralBean;
import com.xiandong.fst.tools.adapter.CredibilityIntegralAdapter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.view.customview.emptyview.HHEmptyView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by dell on 2017/1/21.
 */
@ContentView(R.layout.activity_credibilty_integral_details)
public class CredibilityIntegralDetailsActivity extends AbsBaseActivity {
    @ViewInject(R.id.integralDetailsLv)
    ListView integralDetailsLv;
    CredibilityIntegralAdapter adapter;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;

    @ViewInject(R.id.hhEmptyView)
    HHEmptyView hhEmptyView;

    @Override
    protected void initialize() {
        titleTitleTv.setText("积分详情");
        hhEmptyView.bindView(integralDetailsLv);
        hhEmptyView.setOnBtnClickListener(new HHEmptyView.OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                initNetWork();
            }
        });
        adapter = new CredibilityIntegralAdapter(this);
        integralDetailsLv.setAdapter(adapter);
        initNetWork();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    private void initNetWork() {
        RequestParams params = new RequestParams(Constant.APIURL + "jifencontent");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CredibilityIntegralBean bean = GsonUtil.fromJson(result, CredibilityIntegralBean.class);
                if (bean == null || bean.getJf() == null || bean.getJf().size() <= 0) {
                    hhEmptyView.empty();
                } else {
                    hhEmptyView.success();
                    adapter.addData(bean.getJf());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hhEmptyView.empty();
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
