package com.xiandong.fst.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.tools.adapter.MyRabbitSayTitleAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/24.
 */
@ContentView(R.layout.activity_my_rabbit_say)
public class MyRabbitSayActivity extends AbsBaseActivity{
    @ViewInject(R.id.myRabbitSayTl)
    TabLayout myRabbitSayTl;
    @ViewInject(R.id.myRabbitSayVp)
    ViewPager myRabbitSayVp;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;

    @Override
    protected void initialize() {
        titleTitleTv.setText("我的兔子说");
        myRabbitSayVp.setAdapter(new MyRabbitSayTitleAdapter(getSupportFragmentManager()));
        myRabbitSayTl.setupWithViewPager(myRabbitSayVp);
    }

    @Event(type = View.OnClickListener.class ,value = {R.id.titleBackImg})
    private void onClickListener(View view){
        switch (view.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
