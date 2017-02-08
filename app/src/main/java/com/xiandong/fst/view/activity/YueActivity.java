package com.xiandong.fst.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
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
import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.presenter.FriendsPresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.adapter.YueFriendsAdapter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.TimeUtil;
import com.xiandong.fst.view.FriendsView;
import com.xiandong.fst.view.MainActivityInterfaceManger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.xiandong.fst.utils.TimeUtil.pad;

/**
 * Created by dell on 2017/1/24.
 */
@ContentView(R.layout.activity_yue)
public class YueActivity extends AbsBaseActivity implements FriendsView {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.yueCommitBtn)
    CircularProgressButton yueCommitBtn;
    @ViewInject(R.id.mMapView)
    MapView mMapView;
    BaiduMap mBaiduMap;
    @ViewInject(R.id.yueAddressEt)
    EditText yueAddressEt;

    @ViewInject(R.id.yueFriendsNumTv)
    TextView yueFriendsNumTv;
    private Calendar mCalendar = Calendar.getInstance();
    private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
    private int minute = mCalendar.get(Calendar.MINUTE);
    private int day = mCalendar.get(Calendar.DAY_OF_MONTH);
    private int month = mCalendar.get(Calendar.MONTH);
    private int year = mCalendar.get(Calendar.YEAR);
    private StringBuffer time = new StringBuffer();
    @ViewInject(R.id.yueDateTimeTv)
    TextView yueDateTimeTv;
    @ViewInject(R.id.yueTimeTimeTv)
    TextView yueTimeTimeTv;
    @ViewInject(R.id.yueBzEt)
    EditText yueBzEt;

    @ViewInject(R.id.yueFriendsRv)
    RecyclerView yueFriendsRv;

    YueFriendsAdapter adapter;
    FriendsPresenterImpl presenter;

    List<String> chooseFriends = new ArrayList<>();

    @Override
    protected void initialize() {
        titleTitleTv.setText("约一下");
        yueCommitBtn.setIndeterminateProgressMode(true);
        yueCommitBtn.setText("提交");
        yueCommitBtn.setIdleText("提交");
        presenter = new FriendsPresenterImpl(this);
        presenter.getFriends();
        initViews();
        initTime();
        initMapView();
        initLocation();
        initSearch();
    }

    private void initViews() {
        yueDateTimeTv.setText(new StringBuffer().append(pad(year))
                .append("年").append(pad(month + 1))
                .append("月").append(pad(day))
                .append("日"));
        yueTimeTimeTv.setText(
                new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));
        yueFriendsNumTv.setText("0/0");
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        yueFriendsRv.setLayoutManager(manager);
        adapter = new YueFriendsAdapter(this);
        yueFriendsRv.setAdapter(adapter);
        adapter.setItemClick(new YueFriendsAdapter.itemClickListener() {
            @Override
            public void clickListener(String id) {
                chooseFriends.add(id);
                yueFriendsNumTv.setText(chooseFriends.size() + "/" + adapter.getItemCount());
            }
        });
    }

    private void initTime() {
        time.append(year).append("-").append(month + 1).append("-").append(day)
                .append(" ").append(hourOfDay).append(":").append(minute);
    }

    protected void initMapView() {
        mBaiduMap = mMapView.getMap();
        // 比例尺控件
        mMapView.showScaleControl(false);
        // 缩放控件
        mMapView.showZoomControls(false);
        // 百度地图LoGo -> 正式版切记不能这么做，本人只是觉得logo丑了
        mMapView.removeViewAt(1);
        // 百度地图logo显示位置
        // mapView.setLogoPosition(LogoPosition.logoPostionRightTop);
        // 不倾斜
        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(false);
        // 不旋转
        mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
        // 设置缩放层级
        mBaiduMap.setMaxAndMinZoomLevel(19, 10);
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
                if (bdLocation == null || mMapView == null)
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

    GeoCoder mSearch;

    LatLng mapStatusChangeLatLng;
    SuggestionSearch mSuggestionSearch;

    private void initSearch() {

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

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
                yueAddressEt.setText(address);
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

            }
        });


    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.yueCommitBtn,
            R.id.yueTimeView})
    private void onCLickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.yueCommitBtn:
                CircularProgressButtonTools.showLoding(yueCommitBtn);
                String t = String.valueOf((TimeUtil.getLongFromTime(this.time.toString(), "") / 100));
                String c = yueBzEt.getText().toString().trim();
                String a = yueAddressEt.getText().toString().trim();

                StringBuffer sb = new StringBuffer();
                if (chooseFriends != null && chooseFriends.size() > 0){
                    for (int i = 0; i < chooseFriends.size(); i++) {
                        if (i == chooseFriends.size()-1){
                            sb.append(chooseFriends.get(i));
                        }else {
                            sb.append(chooseFriends.get(i)+",");
                        }
                    }
                }

                creatZu(sb.toString(), c, a, t);
                break;
            case R.id.yueTimeView:
                StyledDialogTools.datePickerDialog(
                        getFragmentManager(), yueDateTimeTv, yueTimeTimeTv,
                        new StyledDialogTools.PickerDialogListener() {
                            @Override
                            public void pickerDate(int day, int month, int year) {
                                time.append(year).append("-").append(month + 1).append("-").append(day);
                            }

                            @Override
                            public void pickerTime(int minute, int hour) {
                                time.append(" ").append(hour).append(":").append(minute);
                            }
                        });
                break;
        }
    }

    private void creatZu(String ids, String content, String p, String t) {
        RequestParams params = new RequestParams(Constant.APIURL + "addmeeting");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("user_id", ids);
        params.addBodyParameter("position", location.latitude + ";" + location.longitude);
        params.addBodyParameter("content", content);
        params.addBodyParameter("pcontent", p);
        params.addBodyParameter("time", t);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        success(bean.getMessage());
                        break;
                    default:
                        err(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                err(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void err(String err) {
        CustomToast.customToast(false, err, this);
        CircularProgressButtonTools.showErr(yueCommitBtn);
    }

    private void success(String msg) {
        CustomToast.customToast(true , msg , this);
        finish();
    }

    @Override
    public void getFriendsFails(String msg) {

    }

    @Override
    public void getFriendsSuccess(FriendsBean friendsBean) {
        adapter.addData(friendsBean.getFriend());
        yueFriendsNumTv.setText("0/" + adapter.getItemCount());
    }

    @Override
    public void friendsImgSuccess(MarkerOptions option) {

    }

    @Override
    public void friendsImgFails(String err) {

    }
}
