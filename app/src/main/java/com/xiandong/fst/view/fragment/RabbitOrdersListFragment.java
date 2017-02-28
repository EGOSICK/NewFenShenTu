package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.presenter.AcceptOrderPresenterImpl;
import com.xiandong.fst.presenter.RabbitOrdersListDataPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.adapter.RabbitOrdersListContentAdapter;
import com.xiandong.fst.view.AcceptOrderView;
import com.xiandong.fst.view.customview.emptyview.HHEmptyView;
import com.xiandong.fst.view.RabbitOrdersListDataView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 接单 列表
 */

@ContentView(R.layout.fragment_rabbit_orders_list)
public class RabbitOrdersListFragment extends AbsBaseFragment implements RabbitOrdersListDataView, AcceptOrderView {
    private int position;

    public static RabbitOrdersListFragment getInstance(int position) {
        RabbitOrdersListFragment fragment = new RabbitOrdersListFragment();
        fragment.getBundle(position);
        return fragment;
    }

    private void getBundle(int position) {
        this.position = position;
    }

    Context context;
    @ViewInject(R.id.rabbitOrdersListLv)
    private ListView rabbitOrdersListLv;
    @ViewInject(R.id.hhEmptyView)
    private HHEmptyView hhEmptyView;
    private RabbitOrdersListContentAdapter adapter;
    private RabbitOrdersListDataPresenterImpl presenterImpl;
    private AcceptOrderPresenterImpl acceptOrderPresenter;

    @Override
    protected void initialize() {
        context = getContext();
        hhEmptyView.bindView(rabbitOrdersListLv);
        hhEmptyView.setLoadingModel(HHEmptyView.MODEL_DEFAULT);
        acceptOrderPresenter = new AcceptOrderPresenterImpl(this);
        presenterImpl = new RabbitOrdersListDataPresenterImpl(this);
        adapter = new RabbitOrdersListContentAdapter(getContext());
        rabbitOrdersListLv.setAdapter(adapter);
        rabbitOrdersListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        adapter.setRobBtnClick(new RabbitOrdersListContentAdapter.rabbitOrdersListContentAdapterListener() {
            @Override
            public void rabbitOrdersListContentRobBtnClick(String uid) {
                acceptOrderPresenter.acceptOrder(uid);
            }
        });
        initNetWork();
    }

    private void initNetWork() {
        switch (position) {
            case 0:
                presenterImpl.getRabbitOrdersListData("distance");
                break;
            case 1:
                presenterImpl.getRabbitOrdersListData("money");
                break;
        }
    }

    public List<OrderListBean.OrderEntity> getOrdersList() {
        return list;
    }

    List<OrderListBean.OrderEntity> list;

    @Override
    public void getRabbitOrdersListDataSuccess(List<OrderListBean.OrderEntity> list) {
        hhEmptyView.success();
        this.list = list;
        adapter.addData(list);
    }

    @Override
    public void getRabbitOrdersListDataFails(String err) {
        hhEmptyView.empty(err);
        hhEmptyView.setOnBtnClickListener(new HHEmptyView.OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                hhEmptyView.loading();
                initNetWork();
            }
        });
    }

    @Override
    public void acceptOrderSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
        initNetWork();
    }

    @Override
    public void acceptOrderFails(String err) {
        CustomToast.customToast(false, err, context);
    }
}
