package com.xiandong.fst.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 服务条款
 */

@ContentView(R.layout.activity_service_provision)
public class ServiceProvisionActivity extends AbsBaseActivity{

    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.serviceTv)
    TextView serviceTv;

    @Override
    protected void initialize() {
        initView();
        initServiceProvision();
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

    private void initServiceProvision(){
        RequestParams params = new RequestParams(Constant.APIURL+"xieyi");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String xieyi = object.getString("xieyi");
                    serviceTv.setText(xieyi);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
