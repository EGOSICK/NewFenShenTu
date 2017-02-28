package com.xiandong.fst.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.RedPacketDetailsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.adapter.RedPacketDetailsAdapter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by dell on 2017/2/7.
 */

@ContentView(R.layout.activity_red_packet_details)
public class RedPacketDetailsActivity extends AbsBaseActivity {
    @ViewInject(R.id.redPacketDetailsImg)
    ImageView redPacketDetailsImg;
    @ViewInject(R.id.redPacketDetailsTv)
    TextView redPacketDetailsTv;
    @ViewInject(R.id.redPacketDetailsLv)
    ListView redPacketDetailsLv;
    RedPacketDetailsAdapter adapter;
    String id;

    @Override
    protected void initialize() {
        id = getIntent().getStringExtra("id");
        adapter = new RedPacketDetailsAdapter(this);
        redPacketDetailsLv.setAdapter(adapter);
        initNetWork(id);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.redPacketDetailsBackImg})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.redPacketDetailsBackImg:
                finish();
                break;
        }
    }

    private void initNetWork(String id) {
        RequestParams params = new RequestParams(Constant.APIURL + "grab");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 2:
                        RedPacketDetailsBean detailsBean = GsonUtil.fromJson(result, RedPacketDetailsBean.class);
                        RedPacketDetailsBean.RedbagEntity rr = detailsBean.getRedbag();
                        redPacketDetailsTv.setText(rr.getUfee());
                        XCircleImgTools.setCircleImg(redPacketDetailsImg , rr.getImg());
                        adapter.addData(rr.getChildren());
                        break;
                    default:

                        break;
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
