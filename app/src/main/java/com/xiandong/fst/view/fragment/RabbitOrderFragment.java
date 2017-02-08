package com.xiandong.fst.view.fragment;

import android.support.v4.view.ViewPager;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.tools.adapter.RabbitOrdersVpAdapter;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import java.util.List;


@ContentView(R.layout.fragment_rabbit_order)
public class RabbitOrderFragment extends AbsBaseFragment {
    @ViewInject(R.id.rabbitOrdersVp)
    ViewPager rabbitOrdersVp;
    RabbitOrdersVpAdapter adapter;
    List<OrderListBean.OrderEntity> list;

    public static RabbitOrderFragment getInstance(List<OrderListBean.OrderEntity> list) {
        RabbitOrderFragment fragment = new RabbitOrderFragment();
        fragment.getOrdersList(list);
        return fragment;
    }

    private void getOrdersList(List<OrderListBean.OrderEntity> list) {
        this.list = list;
    }

    @Override
    protected void initialize() {
        adapter = new RabbitOrdersVpAdapter(getContext(),getFragmentManager().beginTransaction());
        rabbitOrdersVp.setAdapter(adapter);
        rabbitOrdersVp.setCurrentItem(100);
        adapter.addData(list);
    }
}
