package com.xiandong.fst.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.OrderDetailsMsgBean;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.presenter.OrderDetailsPresenterImpl;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.tools.ChatTools;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.OrderDetailsView;
import com.xiandong.fst.view.fragment.ChatBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/22.
 */
@ContentView(R.layout.activity_order_drtails)
public class OrderDetailsActivity extends AbsBaseActivity implements OrderDetailsView {
    OrderDetailsPresenterImpl presenter;
    @ViewInject(R.id.orderDetailsNameTv)
    TextView orderDetailsNameTv;
    @ViewInject(R.id.orderDetailsMoneyTv)
    TextView orderDetailsMoneyTv;

    String phone;

    ImageView orderDetailsUserImg;
    @ViewInject(R.id.orderDetailsMsgView)
    View orderDetailsMsgView;
    @ViewInject(R.id.orderDetailsMapView)
    View orderDetailsMapView;
    @Override
    protected void initialize() {

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String img = intent.getStringExtra("img");
        final String sendId = intent.getStringExtra("sendId");
        String orderId = intent.getStringExtra("orderId");
        ChatTools.chooseGroup(orderId, AppDbManager.getUserId(), sendId, new ChatTools.chooseGroupComplete() {
            @Override
            public void complete(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChatBaseFragment fragment = new ChatBaseFragment();
                        Bundle args = new Bundle();
                        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                        args.putString(EaseConstant.EXTRA_USER_ID, sendId);
                        fragment.setArguments(args);
                        fragment.setMyAttributes(name, img);
                        getSupportFragmentManager().
                                beginTransaction().add(R.id.orderDetailsChatView, fragment).commit();
                    }
                });
            }
        });
        initOrderMessage(orderId);


    }

    private void initOrderMessage(String id) {
        presenter = new OrderDetailsPresenterImpl(this);
        presenter.getOrderMsg(id);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.orderDetailsPhoneImg, R.id.titleBackImg})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.orderDetailsPhoneImg:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                break;
            case R.id.titleBackImg:
                finish();
                break;

        }
    }

    @Override
    public void getOrderMsgSuccess(OrderDetailsMsgBean msgBean) {
        OrderDetailsMsgBean.OrderEntity orderEntity = msgBean.getOrder();
        if (StringUtil.isEquals(AppDbManager.getUserId(), orderEntity.getUid())) {
            orderDetailsMoneyTv.setText(orderEntity.getPrice());
            orderDetailsMoneyTv.setVisibility(View.GONE);
            orderDetailsNameTv.setText(orderEntity.getUsernicheng());
            phone = orderEntity.getUphone();
            XCircleImgTools.setCircleImg(orderDetailsUserImg , orderEntity.getUserimg());
            orderDetailsMsgView.setVisibility(View.GONE);
            orderDetailsMapView.setVisibility(View.VISIBLE);
        } else {
            orderDetailsMoneyTv.setText(orderEntity.getPrice());
            orderDetailsMoneyTv.setVisibility(View.VISIBLE);
            orderDetailsNameTv.setText(orderEntity.getUnicheng());
            phone = orderEntity.getPhone();
            XCircleImgTools.setCircleImg(orderDetailsUserImg , orderEntity.getUimg());
            orderDetailsMsgView.setVisibility(View.VISIBLE);
            orderDetailsMapView.setVisibility(View.GONE);
        }


    }

    @Override
    public void getOrderMsgFails(String err) {

    }
}
