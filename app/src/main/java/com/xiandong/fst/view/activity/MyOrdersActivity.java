package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MyOrdersBean;
import com.xiandong.fst.presenter.MyOrdersPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.adapter.MyOrdersAdapter;
import com.xiandong.fst.view.customview.emptyview.HHEmptyView;
import com.xiandong.fst.view.MyOrdersView;
import com.xiandong.fst.view.fragment.ChatBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/18.
 */
@ContentView(R.layout.activity_my_orders)
public class MyOrdersActivity extends AbsBaseActivity implements MyOrdersView {
    @ViewInject(R.id.myOrdersLv)
    ListView myOrdersLv;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.hhEmptyView)
    HHEmptyView hhEmptyView;
    Context context;
    MyOrdersAdapter adapter;
    MyOrdersPresenterImpl presenter;

    @Override
    protected void initialize() {
        context = this;
        titleTitleTv.setText("我的订单");
        adapter = new MyOrdersAdapter(context);
        myOrdersLv.setAdapter(adapter);
        adapter.setListener(new MyOrdersAdapter.MyOrdersInterface() {
            @Override
            public void clickListener(int type, String[] msg) {
                switch (type) {
                    case 0:
                        presenter.completeOrder(msg[0]);
                        break;
                    case 1:
                        startActivity(new Intent(context , EvaluationOrderActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context , OrderDetailsActivity.class)
                                .putExtra("name",msg[0]).putExtra("img",msg[1])
                                .putExtra("orderId",msg[2]).putExtra("sendId",msg[3]));
                        break;
                }
            }
        });
        presenter = new MyOrdersPresenterImpl(this);
        presenter.getMyOrders();
        hhEmptyView.bindView(myOrdersLv);
        hhEmptyView.setOnBtnClickListener(new HHEmptyView.OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                hhEmptyView.loading();
                presenter.getMyOrders();
            }
        });
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
    public void getMyOrdersFails(String err) {
        CustomToast.customToast(false, err, context);
        hhEmptyView.empty(err);
    }

    @Override
    public void getMyOrdersSuccess(MyOrdersBean myOrdersBean) {
        adapter.addData(myOrdersBean);
        hhEmptyView.success();
    }

    @Override
    public void completeOrderFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void completeOrderSuccess() {
        presenter.getMyOrders();
    }
}
