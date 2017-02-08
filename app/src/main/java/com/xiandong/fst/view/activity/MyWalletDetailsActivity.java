package com.xiandong.fst.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MoneyRecordBean;
import com.xiandong.fst.presenter.MoneyRecordPresenterImpl;
import com.xiandong.fst.tools.adapter.MyWalletDetailsAdapter;
import com.xiandong.fst.view.MoneyRecordView;
import com.xiandong.fst.view.customview.emptyview.HHEmptyView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/21.
 */
@ContentView(R.layout.activity_my_wallet_details)
public class MyWalletDetailsActivity extends AbsBaseActivity implements MoneyRecordView {
    @ViewInject(R.id.myWalletDetailsLv)
    private ListView myWalletDetailsLv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.hhEmptyView)
    HHEmptyView hhEmptyView;

    MoneyRecordPresenterImpl presenter;
    private MyWalletDetailsAdapter adapter;
    String act;

    @Override
    protected void initialize() {
        hhEmptyView.bindView(myWalletDetailsLv);
        hhEmptyView.setOnBtnClickListener(new HHEmptyView.OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                presenter.getMoneyRecord();
            }
        });
        presenter = new MoneyRecordPresenterImpl(this);
        presenter.getMoneyRecord();
        adapter = new MyWalletDetailsAdapter(this);
        act = getIntent().getStringExtra("act");
        switch (act) {
            case "2":
                titleTitleTv.setText("佣金记录");
                break;
            case "3":
                titleTitleTv.setText("提现记录");
                break;
            default:
                titleTitleTv.setText("消费记录");
                break;
        }
        myWalletDetailsLv.setAdapter(adapter);

    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    @Override
    public void moneyRecordSuccess(MoneyRecordBean recordBean) {
        adapter.addData(act, recordBean);
        hhEmptyView.success();
    }

    @Override
    public void moneyRecordFails(String err) {
        hhEmptyView.empty(err);
    }
}
