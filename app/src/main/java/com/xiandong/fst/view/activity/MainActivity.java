package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.presenter.MarkerPresenterImpl;
import com.xiandong.fst.tools.ChatTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.WXShareTools;
import com.xiandong.fst.tools.WechatShareManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.MainActivityInterface;
import com.xiandong.fst.view.MainActivityInterfaceManger;
import com.xiandong.fst.view.MarkerView;
import com.xiandong.fst.view.customview.MyViewPager;
import com.xiandong.fst.view.fragment.ChatBaseFragment;
import com.xiandong.fst.view.fragment.RabbitBillingFragment;
import com.xiandong.fst.view.fragment.RabbitMeFragment;
import com.xiandong.fst.view.fragment.RabbitNestFragment;
import com.xiandong.fst.view.fragment.RabbitOrdersFragment;
import com.xiandong.fst.view.fragment.RabbitSayFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * 主Activity
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AbsBaseActivity implements MarkerView {
    Context context;
    @ViewInject(android.R.id.tabhost)
    TabHost tabHost;
    MarkerPresenterImpl markerPresenter;
    String locationCity;

    @Override
    protected void initialize() {
        context = this;
        markerPresenter = new MarkerPresenterImpl(this);
        initTabSpace();
        initFragment();
        initMapView();
        initMarKer();
        ChatTools.initChat(context);
    }

    @ViewInject(R.id.mapView)
    MapView mapView;
    BaiduMap mBaiduMap;

    protected void initMapView() {
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
        initLocation();  // 定位
        initSearch();    // 搜索
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

                MainActivityInterfaceManger.getInstance().refresh(location);

                if (isFirstLoc) {
                    isFirstLoc = false;
                    locationCity = bdLocation.getCity();
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

    GeoCoder mSearch;

    LatLng mapStatusChangeLatLng;
    SuggestionSearch mSuggestionSearch;

    private void initSearch() {

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                MainActivityInterfaceManger.getInstance().mapChangeStart();
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                MainActivityInterfaceManger.getInstance().mapChanging();
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                mapStatusChangeLatLng = mapStatus.target;
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatusChangeLatLng));
            }
        });

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
                    //没有检索到结果
                    return;
                //获取地理编码结果
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                }
                //获取反向地理编码结果
                StringBuffer address = new StringBuffer();
                if (reverseGeoCodeResult.getPoiList() != null && reverseGeoCodeResult.getPoiList().size() > 0) {
                    address.append(reverseGeoCodeResult.getPoiList().get(0).address);
                    address.append(reverseGeoCodeResult.getPoiList().get(0).name);
                } else {
                    address.append("无法获取位置信息");
                }
                MainActivityInterfaceManger.getInstance().mapChangeFinish(mapStatusChangeLatLng, address.toString());
            }
        });

        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                List<SearchAddressBean> searchList = new ArrayList<SearchAddressBean>();
                for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
                    SearchAddressBean bean = new SearchAddressBean();
                    bean.setCity(info.city);
                    bean.setDistrict(info.district);
                    bean.setKey(info.key);
                    bean.setPt(info.pt);
                    searchList.add(bean);
                }
                MainActivityInterfaceManger.getInstance().searchResult(searchList);
            }
        });

    }

    String[] title;

    private void initMarKer() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                title = marker.getTitle().split(",");
                if (title.length > 0) {
                    switch (title[0]) {
                        case Constant.MarkerType.REDPACKET:
                            markerPresenter.getRedPacketMsg(title[3], title[1], title[2]);
                            break;
                    }
                }
                return true;
            }
        });
    }

    private void initTabSpace() {
        tabHost.setup();
        LayoutInflater inflater = LayoutInflater.from(context);
        tabHost.addTab(tabHost.newTabSpec("one").setContent(R.id.viewOne).
                setIndicator(inflater.inflate(R.layout.tab_rabbit_nest_tabhost, null)));
        tabHost.addTab(tabHost.newTabSpec("two").setContent(R.id.viewTwo).
                setIndicator(inflater.inflate(R.layout.tab_rabbit_billing_tabhost, null)));
        tabHost.addTab(tabHost.newTabSpec("three").setContent(R.id.viewThree).
                setIndicator(inflater.inflate(R.layout.tab_rabbit_orders_tabhost, null)));
        tabHost.addTab(tabHost.newTabSpec("four").setContent(R.id.viewFour).
                setIndicator(inflater.inflate(R.layout.tab_rabbit_say_tabhost, null)));
        tabHost.addTab(tabHost.newTabSpec("five").setContent(R.id.viewFive).
                setIndicator(inflater.inflate(R.layout.tab_rabbit_me_tabhost, null)));
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                switch (s) {
                    case "one":
                        ((RabbitNestFragment) getFragments().get(0)).showPager();
                        break;
                    case "two":
                        ((RabbitBillingFragment) getFragments().get(1)).showPager();
                        break;
                    case "three":
                        ((RabbitOrdersFragment) getFragments().get(2)).showPager();
                        break;
                    case "four":
                        ((RabbitSayFragment) getFragments().get(3)).showPager();
                        break;
                    case "five":
                        ((RabbitMeFragment) getFragments().get(4)).showPager();
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    List<Fragment> fragments;

    private boolean isFragmentNotNull() {
        return fragments != null && fragments.size() == 5;
    }

    private List<Fragment> getFragments() {
        if (isFragmentNotNull()) {
            return fragments;
        } else {
            fragments = new ArrayList<>();
            fragments.add(RabbitNestFragment.getInstance());
            fragments.add(RabbitBillingFragment.getInstance());
            fragments.add(RabbitOrdersFragment.getInstance());
            fragments.add(RabbitSayFragment.getInstance());
            fragments.add(RabbitMeFragment.getInstance());
            return fragments;
        }
    }

    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.viewOne, getFragments().get(0));
        fragmentTransaction.replace(R.id.viewTwo, getFragments().get(1));
        fragmentTransaction.replace(R.id.viewThree, getFragments().get(2));
        fragmentTransaction.replace(R.id.viewFour, getFragments().get(3));
        fragmentTransaction.replace(R.id.viewFive, getFragments().get(4));
        fragmentTransaction.commit();
    }

    /**
     * 定位
     */
    public void positioning(LatLng... lo) {
        MapStatus.Builder builder = new MapStatus.Builder();
        if (lo != null && lo.length > 0 && lo[0] != null)
            builder.target(lo[0]);
        else
            builder.target(location);
        builder.zoom(18f); // 支持缩放级别范围为3-18
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public void cleanMarks() {
        mBaiduMap.clear();
    }

    public BaiduMap getBaiDuMap() {
        return mBaiduMap;
    }

    public void showFriendsPosition(MarkerOptions option) {
        mBaiduMap.addOverlay(option);
    }

    public void searchAddress(String key) {
        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption().city(locationCity).keyword(key));
    }

    @Override
    public void getRedPacketSuccess(final int type, final String come, final String money) {
        String dialogMsg = "";
        switch (type) {
            case 1:   // 先去分享
                if (StringUtil.isEquals(come, Constant.MarkerType.REDPACKETCOME)) {
                    dialogMsg = "点击分享并领取";
                } else {
                    dialogMsg = "点击领取";
                }
                break;
            case 0:   // 抢光
                dialogMsg = "抢光了";
                break;
            case 2:   // 抢过了
                dialogMsg = "抢过了";
                break;
            case 3:   // 太远
                dialogMsg = "距离太远";
                break;
        }

        StyledDialogTools.showRedPacket(dialogMsg, context, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case 1:   // 先去分享
                        if (StringUtil.isEquals(come, Constant.MarkerType.REDPACKETCOME)) {
                            WechatShareManager shareManager = WechatShareManager.getInstance(context);
                            shareManager.shareByWebchat(shareManager.getShareContentPicture(
                                    R.layout.share_red_packet), 1, money);
                            shareManager.registerListener(new WechatShareManager.ShareInterface() {
                                @Override
                                public void shareSuccess() {
                                    CustomToast.customToast(true, "分享成功", context);
                                    markerPresenter.grabRedPacket(title[1]);
                                }
                                @Override
                                public void shareFails() {
                                    CustomToast.customToast(false, "分享失败", context);
                                }
                            });
                        } else {
                            markerPresenter.grabRedPacket(title[1]);
                        }
                        break;
                    case 0:   // 抢光
                        CustomToast.customToast(false, "已经抢光了", context);
                        break;
                    case 2:   // 抢过了
                        startActivity(new Intent(context, RedPacketDetailsActivity.class).putExtra("id", title[1]));
                        break;
                    case 3:   // 太远
                        CustomToast.customToast(false, "太远啦", context);
                        break;
                }
            }
        });
    }

    @Override
    public void getRedPacketFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void grabRedPacketFails(String err) {

    }

    @Override
    public void grabRedPacketSuccess() {
        startActivity(new Intent(context, RedPacketDetailsActivity.class).putExtra("id", title[1]));
    }


    @Override
    protected void onDestroy() {
        // 释放编码实例
        mSearch.destroy();
        mSearch.destroy();
        //退出时销毁定位
        if (mLocClient != null) {
            mLocClient.stop();
        }
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mapView.onDestroy();
        super.onDestroy();
    }
}
