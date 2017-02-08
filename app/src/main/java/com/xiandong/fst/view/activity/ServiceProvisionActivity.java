package com.xiandong.fst.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 服务条款
 */

@ContentView(R.layout.activity_service_provision)
public class ServiceProvisionActivity extends AbsBaseActivity{

    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;

    @Override
    protected void initialize() {
        initView();
    }

    private void initView(){
        titleTitleTv.setText("服务条款");
    }

    @Event(type = View.OnClickListener.class,value = {R.id.titleBackImg})
    private void serviceProvisionObClick(View v){
        switch (v.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
