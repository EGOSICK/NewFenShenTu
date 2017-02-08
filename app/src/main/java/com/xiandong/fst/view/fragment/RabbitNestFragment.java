package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.presenter.FriendsPresenterImpl;
import com.xiandong.fst.presenter.RedPacketPresenterImpl;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.adapter.RabbitNestFriendsAdapter;
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
        getMainActivity().cleanMarks();
        MarkMapTools.friends.clear();
        MarkMapTools.getRedPacket().clear();
        initNetWork();
        return rabbitNestFragment;
    }

    @ViewInject(R.id.friendsPc)
    PagerContainer friendsPc;
    Context context;
    ViewPager vp;
    RabbitNestFriendsAdapter adapter;
    FriendsPresenterImpl presenter;
    RedPacketPresenterImpl redPacketPresenter;

    @Override
    protected void initialize() {
        context = getContext();
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
                LatLng latLng = adapter.getSelectLocation(position);
                getMainActivity().positioning(latLng);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter.setMeListener(new RabbitNestFriendsAdapter.onMeCardClickListener() {
            @Override
            public void clickListener(int type) {
                switch (type) {
                    case 1:
                        startActivity(new Intent(context, MyOrdersActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, MyWalletActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context , MyChatActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context , MyRabbitSayActivity.class));
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
                    presenter.getFriends();
                    redPacketPresenter.loadRedPacket();
                    adapter.getLocation(location);
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
        adapter.addData(friendsBean);
        if (friendsBean.getFriend().size() > 0) {
            presenter.showFriendsPosition(context, friendsBean.getFriend());
        }
    }

    @Override
    public void friendsImgSuccess(MarkerOptions option) {
        getMainActivity().showFriendsPosition(option);
    }

    @Override
    public void friendsImgFails(String err) {
        CustomToast.customToast(false, err, context);
    }


    @Override
    public BaiduMap loadRedPacket() {
        return getMainActivity().getBaiDuMap();
    }

    @Override
    public void loadRedPacketFails(String err) {
    }
}
