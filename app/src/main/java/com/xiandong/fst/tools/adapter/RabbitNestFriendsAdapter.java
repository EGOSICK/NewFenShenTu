package com.xiandong.fst.tools.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.model.bean.MeetBean;
import com.xiandong.fst.presenter.FriendsManagerPresenterImpl;
import com.xiandong.fst.presenter.MeetPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.DistanceTools;
import com.xiandong.fst.tools.PhoneTools;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.tools.navi.NaviHelpTools;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.utils.TimeUtil;
import com.xiandong.fst.view.FriendsManagerView;
import com.xiandong.fst.view.MeetView;
import com.xiandong.fst.view.activity.MeetChatActivity;
import com.xiandong.fst.view.activity.MyChatActivity;
import com.xiandong.fst.view.activity.UserCardActivity;
import com.xiandong.fst.view.activity.YueActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 首页好友适配器
 */

public class RabbitNestFriendsAdapter extends RecyclingPagerAdapter implements FriendsManagerView, MeetView {
    private LayoutInflater inflater;
    private Context context;
    private LatLng location;
    private FriendsBean friendsBean;
    private onMeCardClickListener listener;
    FriendsManagerPresenterImpl presenter =
            new FriendsManagerPresenterImpl(RabbitNestFriendsAdapter.this);

    @Override
    public void changeFriendMessageSuccess(String msg) {

    }

    @Override
    public void changeFriendsMessageFails(String err) {

    }

    @Override
    public void deleteFriendSuccess(String msg) {

    }

    @Override
    public void logoutMeetSuccess() {

    }

    @Override
    public void logoutMeetFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    public interface onMeCardClickListener {
        void clickListener(int type, String... s);
    }

    public void setMeListener(onMeCardClickListener listener) {
        this.listener = listener;
    }

    public RabbitNestFriendsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void getLocation(LatLng latLng) {
        location = latLng;
        notifyDataSetChanged();
    }

    public void addData(FriendsBean friendsBean) {
        this.friendsBean = friendsBean;
        notifyDataSetChanged();
    }

    public String getSelectId(int position) {
        int p = position - 100;
        int lp = p % (friendsBean.getFriend().size() + 1 + friendsBean.getMeet().size());
        if (lp < 0)
            lp = friendsBean.getFriend().size() + 1 + lp + friendsBean.getMeet().size();
        if (lp == 0) {
            return null;
        } else {
            if (lp <= friendsBean.getFriend().size()) {
                if (!StringUtil.isEmpty(friendsBean.getFriend().get(lp - 1).getId())) {
                    return friendsBean.getFriend().get(lp - 1).getId();
                }
            } else {
                if (!StringUtil.isEmpty(friendsBean.getMeet().get(lp - 1 - friendsBean.getFriend().size()).getId())) {
                    return friendsBean.getMeet().get(lp - 1 - friendsBean.getFriend().size()).getId();
                }
            }
        }
        return null;
    }

    public LatLng getSelectLocation(int position) {
        int p = position - 100;
        int lp = p % (friendsBean.getFriend().size() + 1 + friendsBean.getMeet().size());
        if (lp < 0)
            lp = friendsBean.getFriend().size() + 1 + lp + friendsBean.getMeet().size();
        if (lp == 0) {
            return null;
        } else {
            if (lp <= friendsBean.getFriend().size()) {
                if (!StringUtil.isEmpty(friendsBean.getFriend().get(lp - 1).getPosition())) {
                    String[] po = friendsBean.getFriend().get(lp - 1).getPosition().split(";");
                    if (po.length == 2) {
                        return new LatLng(Double.valueOf(po[0]), Double.valueOf(po[1]));
                    }
                }
            }
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_friend_rabbit_nest, container, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        if (friendsBean != null) {
            int p = position - 100;
            int lp = p % (friendsBean.getFriend().size() + 1 + friendsBean.getMeet().size());
            if (lp < 0)
                lp = friendsBean.getFriend().size() + 1 + lp + friendsBean.getMeet().size();

            if (lp == 0) {
                viewHolder.tag.setVisibility(View.GONE);
                viewHolder.itemFriendRNUserZuTv.setVisibility(View.INVISIBLE);
                viewHolder.itemFriendRNMeView.setVisibility(View.VISIBLE);
                viewHolder.itemFriendRNOtherView.setVisibility(View.GONE);
                viewHolder.itemFriendRNZuView.setVisibility(View.GONE);
                XCircleImgTools.setCircleImg(viewHolder.itemFriendRNUserPhotoImg, friendsBean.getUser().getImg());
                viewHolder.itemFriendRNUserDistanceTv.setVisibility(View.GONE);
                viewHolder.itemFriendRNUserNameTv.setText("我");
                View.OnClickListener meListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.itemFriendRNMeOrdersImg:
                                listener.clickListener(1);
                                break;
                            case R.id.itemFriendRNMePurseImg:
                                listener.clickListener(2);
                                break;
                            case R.id.itemFriendRNMeChatImg:
                                listener.clickListener(3);
                                break;
                            case R.id.itemFriendRNMeRabbitImg:
                                listener.clickListener(4);
                                break;
                        }
                    }
                };
                viewHolder.itemFriendRNMeOrdersImg.setOnClickListener(meListener); // 1
                viewHolder.itemFriendRNMePurseImg.setOnClickListener(meListener);  // 2
                viewHolder.itemFriendRNMeChatImg.setOnClickListener(meListener);  // 3
                viewHolder.itemFriendRNMeRabbitImg.setOnClickListener(meListener);  //4
            } else {
                if (lp <= friendsBean.getFriend().size()) {
                    viewHolder.tag.setVisibility(View.GONE);
                    viewHolder.itemFriendRNUserZuTv.setVisibility(View.INVISIBLE);
                    viewHolder.itemFriendRNZuView.setVisibility(View.GONE);
                    viewHolder.itemFriendRNMeView.setVisibility(View.GONE);
                    viewHolder.itemFriendRNOtherView.setVisibility(View.VISIBLE);
                    final FriendsBean.FriendEntity ff = friendsBean.getFriend().get(lp - 1);
                    if (!StringUtil.isEmpty(ff.getBz())) {
                        viewHolder.itemFriendRNUserNameTv.setText(ff.getBz());
                    } else {
                        viewHolder.itemFriendRNUserNameTv.setText(ff.getNicheng());
                    }
                    viewHolder.itemFriendRNUserDistanceTv.setVisibility(View.VISIBLE);
                    XCircleImgTools.setCircleImg(viewHolder.itemFriendRNUserPhotoImg, ff.getImg());
                    String distance = DistanceTools.changeDistance(ff.getPosition(), location);
                    viewHolder.itemFriendRNUserDistanceTv.setText(distance);
                    if (StringUtil.isEquals(ff.getTurnon(), "0")) {
                        viewHolder.itemFriendRNOtherLocationImg.setImageResource(R.mipmap.item_friends_rn_location);
                    } else {
                        viewHolder.itemFriendRNOtherLocationImg.setImageResource(R.mipmap.other_ping_bi);
                    }
                    View.OnClickListener viewClick = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.itemFriendRNOtherPhoneImg:
                                    StyledDialogTools.showCallDialog(context, ff.getPhone(), new MyDialogListener() {
                                        @Override
                                        public void onFirst() {
                                            PhoneTools.callNum(ff.getPhone(), context);
                                            StyledDialogTools.disMissStyleDialog();
                                        }

                                        @Override
                                        public void onSecond() {
                                            StyledDialogTools.disMissStyleDialog();
                                        }
                                    });

                                    break;
                                case R.id.itemFriendRNUserCardImg:
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("friend", ff);
                                    context.startActivity(new Intent(context,
                                            UserCardActivity.class).putExtra("friend", bundle));
                                    break;
                                case R.id.itemFriendRNOtherChatImg:
                                    context.startActivity(new Intent(context, MyChatActivity.class)
                                            .putExtra("id", ff.getUser_id()));
                                    break;
                                case R.id.itemFriendRNOtherLocationImg:
                                    if (StringUtil.isEquals(ff.getTurnon(), "0")) {
                                        StyledDialogTools.showIsClosePositionDialog(context, new MyDialogListener() {
                                            @Override
                                            public void onFirst() {
                                                viewHolder.itemFriendRNOtherLocationImg.setImageResource(R.mipmap.other_ping_bi);
                                                presenter.shieldingPosition(1, ff.getId());
                                                StyledDialogTools.disMissStyleDialog();
                                            }

                                            @Override
                                            public void onSecond() {
                                                StyledDialogTools.disMissStyleDialog();
                                            }
                                        });
                                    } else {
                                        presenter.shieldingPosition(0, ff.getId());
                                        viewHolder.itemFriendRNOtherLocationImg.setImageResource(R.mipmap.item_friends_rn_location);
                                    }
                                    break;
                                case R.id.itemFriendRNOtherYueImg:
                                    context.startActivity(new Intent(context, YueActivity.class));
                                    break;
                                case R.id.itemFriendRNUserPhotoImg:
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putParcelable("friend", ff);
                                    context.startActivity(new Intent(context,
                                            UserCardActivity.class).putExtra("friend", bundle1));
                                    break;
                            }
                        }
                    };
                    viewHolder.itemFriendRNOtherPhoneImg.setOnClickListener(viewClick);
                    viewHolder.itemFriendRNUserCardImg.setOnClickListener(viewClick);
                    viewHolder.itemFriendRNOtherChatImg.setOnClickListener(viewClick);
                    viewHolder.itemFriendRNOtherYueImg.setOnClickListener(viewClick);
                    viewHolder.itemFriendRNOtherLocationImg.setOnClickListener(viewClick);
                    viewHolder.itemFriendRNUserPhotoImg.setOnClickListener(viewClick);
                } else {
                    final MeetPresenterImpl meetPresenter = new MeetPresenterImpl(this);
                    viewHolder.tag.setVisibility(View.VISIBLE);
                    viewHolder.itemFriendRNMeView.setVisibility(View.GONE);
                    viewHolder.itemFriendRNOtherView.setVisibility(View.GONE);
                    viewHolder.itemFriendRNZuView.setVisibility(View.VISIBLE);
                    viewHolder.itemFriendRNUserZuTv.setVisibility(View.VISIBLE);
                    final FriendsBean.MeetEntity fm = friendsBean.getMeet().get(lp - 1 - friendsBean.getFriend().size());
                    XCircleImgTools.setCircleImg(viewHolder.itemFriendRNUserPhotoImg, fm.getUimg());
                    viewHolder.itemFriendRNUserNameTv.setText(fm.getContent());
                    viewHolder.itemFriendRNUserDistanceTv.setText(
                            TimeUtil.convertTimeToFormat(Long.parseLong(fm.getTimeline())));
                    viewHolder.itemFriendRNUserZuTv.setText(fm.getPcontent());
                    View.OnClickListener onClick = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.itemFriendRNZuChatImg:
                                    context.startActivity(new Intent(context, MeetChatActivity.class).putExtra("id", fm.getId()));
                                    break;
                                case R.id.itemFriendRNZuDetailsImg:
                                    StyledDialogTools.showLoding(context);
                                    initNetWork(fm.getId(), fm.getUimg(), fm.getUnicheng());
                                    break;
                                case R.id.itemFriendRNZuDaoHangImg:
                                    double distance = DistanceTools.calculationDistance(
                                            fm.getPosition(), location);
                                    if (distance >= 300) {
                                        StyledDialogTools.showLoding(context);
                                        listener.clickListener(5, fm.getPosition(), fm.getPcontent());

                                    }else {
                                        CustomToast.customToast(false,"距离太近,无法计算路线",context);
                                    }
                                    break;
                                case R.id.itemFriendRNZuLogoutImg:
                                    StyledDialogTools.showLogoutMeetDialog(context, new MyDialogListener() {
                                        @Override
                                        public void onFirst() {
                                            meetPresenter.logoutMeet(fm.getId());
                                            StyledDialogTools.disMissStyleDialog();
                                        }

                                        @Override
                                        public void onSecond() {
                                            StyledDialogTools.disMissStyleDialog();
                                        }
                                    });
                                    break;
                            }
                        }
                    };
                    viewHolder.itemFriendRNZuChatImg.setOnClickListener(onClick);
                    viewHolder.itemFriendRNZuDetailsImg.setOnClickListener(onClick);
                    viewHolder.itemFriendRNZuDaoHangImg.setOnClickListener(onClick);
                    viewHolder.itemFriendRNZuLogoutImg.setOnClickListener(onClick);
                }
            }
        }
        return convertView;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.999f;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    private class ViewHolder {
        ImageView itemFriendRNUserPhotoImg;
        TextView itemFriendRNUserNameTv, itemFriendRNUserDistanceTv, itemFriendRNUserZuTv;
        View itemFriendRNOtherView, itemFriendRNMeView, itemFriendRNZuView;
        ImageView itemFriendRNOtherPhoneImg, itemFriendRNUserCardImg, itemFriendRNOtherChatImg;
        ImageView itemFriendRNMeOrdersImg, itemFriendRNMePurseImg, itemFriendRNMeChatImg,
                itemFriendRNMeRabbitImg, itemFriendRNOtherLocationImg, itemFriendRNOtherYueImg,
                itemFriendRNZuChatImg, itemFriendRNZuDetailsImg, itemFriendRNZuDaoHangImg,
                itemFriendRNZuLogoutImg;
        ImageView tag;

        private ViewHolder(View view) {
            itemFriendRNUserPhotoImg = (ImageView) view.findViewById(R.id.itemFriendRNUserPhotoImg);
            itemFriendRNUserNameTv = (TextView) view.findViewById(R.id.itemFriendRNUserNameTv);
            itemFriendRNUserDistanceTv = (TextView) view.findViewById(R.id.itemFriendRNUserDistanceTv);
            itemFriendRNOtherView = view.findViewById(R.id.itemFriendRNOtherView);
            itemFriendRNMeView = view.findViewById(R.id.itemFriendRNMeView);
            itemFriendRNOtherPhoneImg = (ImageView) view.findViewById(R.id.itemFriendRNOtherPhoneImg);
            itemFriendRNUserCardImg = (ImageView) view.findViewById(R.id.itemFriendRNUserCardImg);
            itemFriendRNOtherChatImg = (ImageView) view.findViewById(R.id.itemFriendRNOtherChatImg);
            itemFriendRNMeOrdersImg = (ImageView) view.findViewById(R.id.itemFriendRNMeOrdersImg);
            itemFriendRNMePurseImg = (ImageView) view.findViewById(R.id.itemFriendRNMePurseImg);
            itemFriendRNMeChatImg = (ImageView) view.findViewById(R.id.itemFriendRNMeChatImg);
            itemFriendRNMeRabbitImg = (ImageView) view.findViewById(R.id.itemFriendRNMeRabbitImg);
            itemFriendRNOtherLocationImg = (ImageView) view.findViewById(R.id.itemFriendRNOtherLocationImg);
            itemFriendRNOtherYueImg = (ImageView) view.findViewById(R.id.itemFriendRNOtherYueImg);
            itemFriendRNUserZuTv = (TextView) view.findViewById(R.id.itemFriendRNUserZuTv);
            itemFriendRNZuView = view.findViewById(R.id.itemFriendRNZuView);

            itemFriendRNZuChatImg = (ImageView) view.findViewById(R.id.itemFriendRNZuChatImg);
            itemFriendRNZuDetailsImg = (ImageView) view.findViewById(R.id.itemFriendRNZuDetailsImg);
            itemFriendRNZuDaoHangImg = (ImageView) view.findViewById(R.id.itemFriendRNZuDaoHangImg);
            itemFriendRNZuLogoutImg = (ImageView) view.findViewById(R.id.itemFriendRNZuLogoutImg);
            tag = (ImageView) view.findViewById(R.id.tag);
        }
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }


    private void initNetWork(String id, final String img, final String nick) {
        RequestParams params = new RequestParams(Constant.APIURL + "showmeeting");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                StyledDialogTools.disMissStyleDialog();
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        MeetBean meetBean = GsonUtil.fromJson(result, MeetBean.class);
                        StyledDialogTools.showMeetDetailsDialog(context, meetBean, img, nick);
                        break;
                    default:
                        CustomToast.customToast(false, bean.getMessage(), context);
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                StyledDialogTools.disMissStyleDialog();
                CustomToast.customToast(false, "加载数据失败", context);
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
