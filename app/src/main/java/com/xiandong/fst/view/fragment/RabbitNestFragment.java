package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.presenter.FriendsPresenterImpl;
import com.xiandong.fst.presenter.RedPacketPresenterImpl;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.adapter.RabbitNestFriendsAdapter;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.RedPacketView;
import com.xiandong.fst.view.activity.MyChatActivity;
import com.xiandong.fst.view.activity.MyOrdersActivity;
import com.xiandong.fst.view.activity.MyRabbitSayActivity;
import com.xiandong.fst.view.activity.MyWalletActivity;
import com.xiandong.fst.view.FriendsView;
import com.xiandong.fst.view.MainActivityInterface;
import com.xiandong.fst.view.MainActivityInterfaceManger;
import com.xiandong.fst.view.activity.AddFriendsActivity;
import com.xiandong.fst.view.activity.SendRedPacketActivity;
import com.xiandong.fst.view.customview.PagerContainer;



import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * 兔子窝
 */

@ContentView(R.layout.fragment_rabbit_nest)
public class RabbitNestFragment extends AbsBaseFragment implements FriendsView, RedPacketView {
    static RabbitNestFragment rabbitNestFragment = null;

    public static RabbitNestFragment getInstance() {
        return rabbitNestFragment = new RabbitNestFragment();
    }

    public RabbitNestFragment showPager() {
        MarkMapTools.choosePager(true, false, false, false, false);
        getMainActivity().cleanMarks();
        initNetWork();
        return rabbitNestFragment;
    }

    @ViewInject(R.id.friendsPc)
    PagerContainer friendsPc;
    Context context;
    LatLng location;
    ViewPager vp;
    RabbitNestFriendsAdapter adapter;
    FriendsPresenterImpl presenter;
    RedPacketPresenterImpl redPacketPresenter;

    @Override
    protected void initialize() {
        MarkMapTools.choosePager(true, false, false, false, false);
        context = getContext();
        StyledDialogTools.showLoding(context);
        presenter = new FriendsPresenterImpl(this);
        redPacketPresenter = new RedPacketPresenterImpl(this);
        adapter = new RabbitNestFriendsAdapter(context);
        vp = friendsPc.getViewPager();
        vp.setAdapter(adapter);

        vp.setPageMargin(20);
        vp.setCurrentItem(100);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int p = position - 100;
                int lp = p % (MarkMapTools.friends.size() + 1 + MarkMapTools.getMeets().size());
                if (lp < 0)
                    lp = MarkMapTools.friends.size() + 1 + lp + MarkMapTools.getMeets().size();
                if (lp == 0) {
                    getMainActivity().positioning();
                } else {
                    if (lp <= MarkMapTools.friends.size()) {
                        Marker marker = MarkMapTools.friends.get(adapter.getSelectId(position));
                        if (marker == null)
                            return;
                        marker.setToTop();
                        getMainActivity().positioning(marker.getPosition());
                    } else {
                        Marker marker = MarkMapTools.getMeets().get(adapter.getSelectId(position));
                        if (marker == null)
                            return;
                        marker.setToTop();
                        getMainActivity().positioning(marker.getPosition());
                    }
                }



//                if (position == 0){
//                    getMainActivity().positioning();
//                }else if (position <= MarkMapTools.friends.size() ){
//                    Marker marker = MarkMapTools.friends.get(adapter.getSelectId(position));
//                    if (marker == null)
//                        return;
//                    marker.setToTop();
//                    getMainActivity().positioning(marker.getPosition());
//                }else {
//                    Marker marker = MarkMapTools.getMeets().get(adapter.getSelectId(position));
//                    if (marker == null)
//                        return;
//                    marker.setToTop();
//                    getMainActivity().positioning(marker.getPosition());
//                }

//                if (adapter.getSelectId(position) != null) {
//                    Marker marker = MarkMapTools.friends.get(adapter.getSelectId(position));
//                    if (marker == null)
//                        return;
//                    marker.setToTop();
//                    getMainActivity().positioning(marker.getPosition());
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter.setMeListener(new RabbitNestFriendsAdapter.onMeCardClickListener() {
            @Override
            public void clickListener(int type,String[] s) {
                switch (type) {
                    case 1:
                        startActivity(new Intent(context, MyOrdersActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, MyWalletActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, MyChatActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, MyRabbitSayActivity.class));
                        break;
                    case 5:
                        getMainActivity().getNaviHelpTools().startNavi(location, s[0], s[1]);
                        break;
                }
            }
        });
        initNetWork();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.locationImg,
            R.id.plusImg, R.id.redPacketImg})
    private void rabbitNestOnClick(View view) {
        switch (view.getId()) {
            case R.id.locationImg:
                getMainActivity().positioning();
                break;
            case R.id.redPacketImg:
                startActivity(new Intent(context, SendRedPacketActivity.class));
                break;
            case R.id.plusImg:
                startActivity(new Intent(context, AddFriendsActivity.class));
                break;
        }
    }

    private void initNetWork() {
        MainActivityInterfaceManger.getInstance().registerListener(new MainActivityInterface() {

            @Override
            public void onMapChangeStart() {

            }

            @Override
            public void onMapChanging() {

            }

            @Override
            public void OnMapChangeFinish(LatLng mapStatusChangeLatLng, String address) {

            }

            @Override
            public void onRefresh(LatLng location) {
                if (location != null) {
                    RabbitNestFragment.this.location = location;
                    if (MarkMapTools.isNestPager) {
                        presenter.getFriends();
                        redPacketPresenter.loadRedPacket();
                        adapter.getLocation(location);
                    }
                }
            }

            @Override
            public void onSearchResult(List<SearchAddressBean> list) {

            }
        });
    }

    @Override
    public void getFriendsFails(String msg) {
    }

    @Override
    public void getFriendsSuccess(FriendsBean friendsBean) {
        if (MarkMapTools.isNestPager) {
            adapter.addData(friendsBean);
            adapter.setLocationCity(getMainActivity().getLocationCity());
            if (friendsBean.getFriend().size() > 0) {
                presenter.showFriendsPosition(context, friendsBean.getFriend());
                presenter.showMeetsPosition(context , friendsBean.getMeet());
            }
            StyledDialogTools.disMissStyleDialog();
        }
    }

    @Override
    public void friendsImgSuccess(MarkerOptions option) {
//        getMainActivity().showFriendsPosition(option);
    }

    @Override
    public void friendsImgFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public BaiduMap getBaiDuMap() {
        return getMainActivity().getBaiDuMap();
    }


    @Override
    public BaiduMap loadRedPacket() {
        return getMainActivity().getBaiDuMap();
    }

    @Override
    public void loadRedPacketFails(String err) {
    }
}
