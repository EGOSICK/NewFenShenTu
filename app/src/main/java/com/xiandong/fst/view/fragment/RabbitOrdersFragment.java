package com.xiandong.fst.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.xiandong.fst.R;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.adapter.RabbitOrdersListAdapter;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 接单
 */

@ContentView(R.layout.fragment_rabbit_orders)
public class RabbitOrdersFragment extends AbsBaseFragment {
    private static  RabbitOrdersFragment rabbitOrdersFragment = null ;
    public static RabbitOrdersFragment getInstance() {
        if (rabbitOrdersFragment == null)
            rabbitOrdersFragment = new RabbitOrdersFragment();
        return rabbitOrdersFragment;
    }

    public RabbitOrdersFragment showPager(){
        getMainActivity().cleanMarks();
        MarkMapTools.choosePager(false, false, true, false, false);
        RabbitOrdersListAdapter adapter = new RabbitOrdersListAdapter(getFragmentManager());
        f = (RabbitOrdersListFragment) adapter.getItem(0);
        rabbitOrdersVp.setAdapter(adapter);
        rabbitOrdersTl.setupWithViewPager(rabbitOrdersVp);
        return  rabbitOrdersFragment;
    }

    @ViewInject(R.id.rabbitOrdersTl)
    TabLayout rabbitOrdersTl;
    @ViewInject(R.id.rabbitOrdersVp)
    ViewPager rabbitOrdersVp;
    RabbitOrdersListFragment f;

    @Override
    protected void initialize() {
        RabbitOrdersListAdapter adapter = new RabbitOrdersListAdapter(getFragmentManager());
        f = (RabbitOrdersListFragment) adapter.getItem(0);
        rabbitOrdersVp.setAdapter(adapter);
        rabbitOrdersTl.setupWithViewPager(rabbitOrdersVp);

    }

    @Event(type = View.OnClickListener.class , value = {R.id.rabbitOrdersBottomImg})
    private void onClickListener(View view){
        switch (view.getId()){
            case R.id.rabbitOrdersBottomImg:
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.viewThree ,  RabbitOrderFragment.getInstance(
                        f.getOrdersList() , getMainActivity().getBaiDuMap()));
                ft.commit();
                break;
        }
    }
}



