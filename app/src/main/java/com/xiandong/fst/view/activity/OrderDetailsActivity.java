package com.xiandong.fst.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.EWaiPayBean;
import com.xiandong.fst.model.bean.OrderDetailsMsgBean;
import com.xiandong.fst.presenter.OrderDetailsPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.tools.chat.ChatTools;
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
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.orderDetailsNameTv)
    TextView orderDetailsNameTv;
    @ViewInject(R.id.orderDetailsMoneyTv)
    TextView orderDetailsMoneyTv;
    @ViewInject(R.id.orderDetailsUserImg)
    ImageView orderDetailsUserImg;
    @ViewInject(R.id.orderDetailsMsgView)
    View orderDetailsMsgView;
    @ViewInject(R.id.orderDetailsMapView)
    View orderDetailsMapView;
    @ViewInject(R.id.orderDetailsOtherTitleTv)
    TextView orderDetailsOtherTitleTv;
    @ViewInject(R.id.orderDetailsOtherContentTv)
    TextView orderDetailsOtherContentTv;
    @ViewInject(R.id.orderDetailsOtherAddressTv)
    TextView orderDetailsOtherAddressTv;
    @ViewInject(R.id.orderMapView)
    MapView mapView;
    @ViewInject(R.id.eWaiFeiYongView)
    View eWaiFeiYongView;
    @ViewInject(R.id.eWaiEt)
    EditText eWaiEt;
    @ViewInject(R.id.eWaiBtn)
    Button eWaiBtn;
    @ViewInject(R.id.roteBtn)
    ImageView roteBtn;
    @ViewInject(R.id.topView)
    View topView;
    BaiduMap mBaiduMap;
    String phone, orderId;
    boolean isSendOrderUser, isShowDtl = true;

    @Override
    protected void initialize() {
        orderId = initView();
        initMapView();
        initOrderMessage(orderId);
    }

    private String initView() {
        titleTitleTv.setText("订单详情");
        Intent intent = getIntent();
//        final String name = intent.getStringExtra("name");
//        final String img = intent.getStringExtra("img");
        final String sendId = intent.getStringExtra("sendId");
        final String orderId = intent.getStringExtra("orderId");
        ChatTools.chooseGroup("order" + orderId, new ChatTools.chooseGroupComplete() {
            @Override
            public void complete(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChatBaseFragment fragment = new ChatBaseFragment();
                        Bundle args = new Bundle();
                        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                        args.putString(EaseConstant.EXTRA_USER_ID, s);
                        fragment.setArguments(args);
                        fragment.setOrderId("order" + s);
                        fragment.setMyAttributes(AppDbManager.getLastUser().getUserName(),
                                AppDbManager.getLastUser().getUserImg());
                        DemoHelper.getInstance().setMeAvatar(AppDbManager.getLastUser().getUserImg());
                        getSupportFragmentManager().
                                beginTransaction().add(R.id.orderDetailsChatView, fragment).commit();
                    }
                });
            }
        }, AppDbManager.getUserId(), sendId);

//        eWaiEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
////                    topView.setVisibility(View.GONE);
//                    setAnimation(roteBtn, 0, 180);
//                    isShowDtl = false;
//                } else {
////                    topView.setVisibility(View.VISIBLE);
////                    setAnimation(roteBtn, 180, 360);
////                    isShowDtl = true;
//                }
////                showOrDisMiss();
//            }
//        });

        return orderId;
    }

    private void initMapView() {
        mBaiduMap = mapView.getMap();
        // 比例尺控件
        mapView.showScaleControl(false);
        // 缩放控件
        mapView.showZoomControls(false);
        // 百度地图LoGo -> 正式版切记不能这么做，本人只是觉得logo丑了
        mapView.removeViewAt(1);
        // 百度地图logo显示位置
        // mapView.setLogoPosition(LogoPosition.logoPostionRightTop);
        // 不倾斜
        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(false);
        // 不旋转
        mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
        // 设置缩放层级
        mBaiduMap.setMaxAndMinZoomLevel(19, 10);

        initLocation();
    }

    boolean isFirstLoc = true;
    LatLng location;
    LocationClient mLocClient;

    private void initLocation() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setNeedDeviceDirect(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 定位监听
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                // map view 销毁后不在处理新接收的位置
                if (bdLocation == null || mapView == null)
                    return;
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(0).latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                location = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());


                if (isFirstLoc) {
                    isFirstLoc = false;
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(location);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    LatLngBounds.Builder bounds = new LatLngBounds.Builder();
                    bounds.include(location);
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(bounds.build().getCenter());
                    mBaiduMap.setMapStatus(u);
                }
            }
        });
    }

    private void initOrderMessage(String id) {
        presenter = new OrderDetailsPresenterImpl(this);
        presenter.getOrderMsg(id);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.orderDetailsPhoneImg, R.id.titleBackImg,
            R.id.eWaiFeiYongBtn, R.id.wanChengDingDanBtn, R.id.quXiaoDingDanBtn, R.id.eWaiBtn,
            R.id.showMoreOrLessView})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.orderDetailsPhoneImg:
                StyledDialogTools.showCallDialog(this, phone, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + phone);
                        intent.setData(data);
                        if (ActivityCompat.checkSelfPermission(OrderDetailsActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                        StyledDialogTools.disMissStyleDialog();
                    }

                    @Override
                    public void onSecond() {
                        StyledDialogTools.disMissStyleDialog();
                    }
                });
                break;
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.eWaiFeiYongBtn:
                if (eWaiFeiYongView.getVisibility() == View.VISIBLE) {
                    eWaiFeiYongView.setVisibility(View.GONE);
                } else {
                    eWaiFeiYongView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.wanChengDingDanBtn:
                StyledDialogTools.showComplacteOrderDialog(this, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        presenter.complicateOrder(orderId);
                        StyledDialogTools.disMissStyleDialog();
                    }

                    @Override
                    public void onSecond() {
                        StyledDialogTools.disMissStyleDialog();
                    }
                });
                break;
            case R.id.quXiaoDingDanBtn:
                StyledDialogTools.showCancelOrderDialog(this, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        presenter.cancelOrder(orderId, "xs");
                        StyledDialogTools.disMissStyleDialog();
                    }

                    @Override
                    public void onSecond() {
                        presenter.cancelOrder(orderId, "qz");
                        StyledDialogTools.disMissStyleDialog();
                    }
                });
                break;
            case R.id.eWaiBtn:
                String money = eWaiEt.getText().toString().trim();
                if (!StringUtil.isEmpty(money)) {
                    int price = Integer.parseInt(money);
                    if (price >= 1) {
                        if (isSendOrderUser) {
                            Bundle bundle = new Bundle();
                            EWaiPayBean payBean = new EWaiPayBean();
                            payBean.setId(orderId);
                            payBean.setMoney(money);
                            bundle.putParcelable("payBean", payBean);
                            startActivityForResult(new Intent(OrderDetailsActivity.this,
                                    PayEWaiActivity.class).putExtra("payBean", bundle), 0);
                        } else {
                            presenter.eWai(orderId, money);
                        }
                    } else {
                        CustomToast.customToast(false, "请输入1及以上的有效金额", this);
                    }
                } else {
                    CustomToast.customToast(false, "请输入1及以上的有效金额", this);
                }
                break;
            case R.id.showMoreOrLessView:
                showOrDisMiss();
                break;
        }
    }

    private void showOrDisMiss() {
        if (isShowDtl) {
            setAnimation(roteBtn, 0, 180);
            isShowDtl = false;
        } else {
            setAnimation(roteBtn, 180, 360);
            isShowDtl = true;
        }
    }

    @Override
    public void getOrderMsgSuccess(OrderDetailsMsgBean msgBean) {
        OrderDetailsMsgBean.OrderEntity orderEntity = msgBean.getOrder();
        if (StringUtil.isEquals(AppDbManager.getUserId(), orderEntity.getUid())) {
            isSendOrderUser = true;
        } else {
            isSendOrderUser = false;
        }
        if (isSendOrderUser) {
            orderDetailsMoneyTv.setText(orderEntity.getPrice());
            orderDetailsMoneyTv.setVisibility(View.GONE);
            orderDetailsNameTv.setText(orderEntity.getUsernicheng());
            phone = orderEntity.getUserphone();
            XCircleImgTools.setCircleImg(orderDetailsUserImg, orderEntity.getUserimg());
            orderDetailsMsgView.setVisibility(View.GONE);
            orderDetailsMapView.setVisibility(View.VISIBLE);
            String[] p = orderEntity.getPosition().split(";");
            LatLng latLng = new LatLng(Double.valueOf(p[0]), Double.valueOf(p[1]));
            mBaiduMap.addOverlay(new MarkerOptions().icon(
                    BitmapDescriptorFactory.fromResource(R.mipmap.froum_icon)).position(latLng));
        } else {
            orderDetailsMoneyTv.setText(orderEntity.getPrice());
            orderDetailsMoneyTv.setVisibility(View.VISIBLE);
            orderDetailsNameTv.setText(orderEntity.getUnicheng());
            phone = orderEntity.getUphone();
            XCircleImgTools.setCircleImg(orderDetailsUserImg, orderEntity.getUimg());
            orderDetailsMsgView.setVisibility(View.VISIBLE);
            orderDetailsMapView.setVisibility(View.GONE);
            orderDetailsOtherTitleTv.setText(orderEntity.getTitle());
            orderDetailsOtherContentTv.setText(orderEntity.getInfo());
            orderDetailsOtherAddressTv.setText(orderEntity.getPcontent());
        }
        if (StringUtil.isEmpty(orderEntity.getExtraprice())) {
            if (StringUtil.isEmpty(orderEntity.getExtrauser_id())) {
                eWaiEt.setText("");
                eWaiEt.setFocusable(true);
                eWaiEt.setClickable(true);
                eWaiBtn.setVisibility(View.VISIBLE);
            } else {
                eWaiEt.setText(orderEntity.getExtrauser_id());
                eWaiEt.setFocusable(false);
                eWaiEt.setClickable(false);
                if (isSendOrderUser) {
                    eWaiBtn.setVisibility(View.VISIBLE);
                } else {
                    eWaiBtn.setVisibility(View.GONE);
                }
            }
        } else {
            eWaiEt.setText(orderEntity.getExtraprice());
            eWaiEt.setFocusable(false);
            eWaiEt.setClickable(false);
            eWaiBtn.setVisibility(View.INVISIBLE);
            eWaiFeiYongView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getOrderMsgFails(String err) {

    }

    @Override
    public void complicateOrderSuccess(String msg) {
        setResult(0);
        finish();
    }

    @Override
    public void complicateOrderFails(String err) {
        CustomToast.customToast(false, err, this);
    }

    @Override
    public void cancelOrderFails(String err) {
        CustomToast.customToast(false, err, this);
    }

    @Override
    public void cancelOrderSuccess(String msg) {
        setResult(0);
        finish();
    }

    @Override
    public void eWaiSuccess(String msg) {
        eWaiEt.setFocusable(false);
        eWaiEt.setClickable(false);
        eWaiBtn.setVisibility(View.INVISIBLE);
        eWaiFeiYongView.setVisibility(View.GONE);
    }

    @Override
    public void eWaiFails(String err) {
        CustomToast.customToast(false, err, this);
    }

    /***
     * 设置旋转动画
     *
     * @param v 旋转的view
     * @param a 起始角度
     * @param b 结束角度
     */
    private void setAnimation(View v, int a, int b) {
        RotateAnimation ra = new RotateAnimation(a, b, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true);
        ra.setDuration(300);
        v.startAnimation(ra);
//        if (topView.getHeight() == 0){
//
//        }else {
//            topViewH = topView.getHeight();
//        }

        if (a == 0) {
            topView.setVisibility(View.GONE);
//            CostomAnimation.zoomAnimate(v, topView, topViewH, 0, CostomAnimation.HEIGHT, 300);
//            CostomAnimation.panningAnimation(v,topView, -topView.getHeight(), 0,
//                   CostomAnimation.HEIGHT , 300);
        } else {
//            CostomAnimation.panningAnimation(v,topView, 0, -topView.getHeight(),
//                   CostomAnimation.HEIGHT , 300);
//            CostomAnimation.zoomAnimate(v, topView, 0, topViewH, CostomAnimation.HEIGHT, 300);
            topView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            eWaiEt.setFocusable(false);
            eWaiEt.setClickable(false);
            eWaiBtn.setVisibility(View.INVISIBLE);
            eWaiFeiYongView.setVisibility(View.GONE);
        } else {
            eWaiEt.setFocusable(true);
            eWaiEt.setClickable(true);
            eWaiBtn.setVisibility(View.VISIBLE);
            eWaiFeiYongView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mapView.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //退出时销毁定位
        if (mLocClient != null) {
            mLocClient.stop();
        }
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mapView.onDestroy();
        super.onDestroy();
    }
}
