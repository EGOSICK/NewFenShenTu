package com.xiandong.fst.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.MeetBean;
import com.xiandong.fst.tools.chat.ChatTools;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.adapter.MeetAddFriendsAdapter;
import com.xiandong.fst.tools.adapter.MeetFriendsAdapter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.view.fragment.ChatBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by dell on 2017/2/8.
 */
@ContentView(R.layout.activity_meet_chat)
public class MeetChatActivity extends AbsBaseActivity {
    @ViewInject(R.id.titleOtherImg)
    ImageView titleOtherImg;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.meetFriendsView)
    View meetFriendsView;
    @ViewInject(R.id.meetFriendsAddView)
    View meetFriendsAddView;
    @ViewInject(R.id.meetFriendsRv)
    RecyclerView meetFriendsRv;
    MeetFriendsAdapter adapter;
    @ViewInject(R.id.meetFriendsAddRv)
    RecyclerView meetFriendsAddRv;
    MeetAddFriendsAdapter meetAdapter;
    @ViewInject(R.id.meetAddFriendsBtn)
    CircularProgressButton meetAddFriendsBtn;
    boolean isFrist = true;
    String chatId;

    @Override
    protected void initialize() {
        StyledDialogTools.showLoding(this);
        titleOtherImg.setImageResource(R.mipmap.meet_right_top_icon);
        titleTitleTv.setText("群聊");
        String id = getIntent().getStringExtra("id");
        meetAddFriendsBtn.setIndeterminateProgressMode(true);
        meetAddFriendsBtn.setIdleText("提交");
        meetAddFriendsBtn.setText("提交");
        chatId = id;
        adapter = new MeetFriendsAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        meetFriendsRv.setLayoutManager(manager);
        meetFriendsRv.setAdapter(adapter);
        adapter.setOnAddItemClickListener(new MeetFriendsAdapter.OnAddItemClickListener() {
            @Override
            public void clickListener() {
                if (meetFriendsAddView.getVisibility() == View.VISIBLE) {
                    meetFriendsAddView.setVisibility(View.GONE);
                } else {
                    meetFriendsAddView.setVisibility(View.VISIBLE);
                }
            }
        });
        meetAdapter = new MeetAddFriendsAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        meetFriendsAddRv.setLayoutManager(layoutManager);
        meetFriendsAddRv.setAdapter(meetAdapter);
        initNetWork(id);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.titleOtherImg,
            R.id.meetAddFriendsBtn})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.titleOtherImg:
                if (meetFriendsView.getVisibility() == View.VISIBLE) {
                    meetFriendsView.setVisibility(View.GONE);
                } else {
                    meetFriendsView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.meetAddFriendsBtn:
                CircularProgressButtonTools.showLoding(meetAddFriendsBtn);
                addMeetUser(meetAdapter.getIdList());
                break;
        }
    }

    private void initNetWork(final String id) {
        RequestParams params = new RequestParams(Constant.APIURL + "showmeeting");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        MeetBean meetBean = GsonUtil.fromJson(result, MeetBean.class);
                        adapter.addData(meetBean.getMeet().getChild());
                        meetAdapter.setTag(1);
                        meetAdapter.addData(meetBean.getMeet().getDif());
                        if (isFrist){
                            String[] user = new String[meetBean.getMeet().getChild().size() + 1];
                            for (int i = 0; i < meetBean.getMeet().getChild().size(); i++) {
                                if (i == 0) {
                                    user[0] = AppDbManager.getUserId();
                                } else {
                                    user[i] = meetBean.getMeet().getChild().get(i - 1).getUid();
                                }
                            }
                            ChatTools.chooseGroup("meet" + id, new ChatTools.chooseGroupComplete() {
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
                                            fragment.setOrderId("meet" + id);
                                            fragment.setMyAttributes(AppDbManager.getLastUser().getUserName(),
                                                    AppDbManager.getLastUser().getUserImg());
                                            DemoHelper.getInstance().setMeAvatar(AppDbManager.getLastUser().getUserImg());
                                            getSupportFragmentManager().
                                                    beginTransaction().add(R.id.meetChatView, fragment).commit();
                                        }
                                    });
                                }
                            }, user);
                            StyledDialogTools.disMissStyleDialog();
                            isFrist = false;
                        }
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

    private void addMeetUser(String userId) {
        RequestParams params = new RequestParams(Constant.APIURL + "addmember");
        params.addBodyParameter("id", chatId);
        params.addBodyParameter("user_id", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (meetFriendsView.getVisibility() == View.VISIBLE) {
                    meetFriendsView.setVisibility(View.GONE);
                }
                CircularProgressButtonTools.showTrue(meetAddFriendsBtn);
                meetAddFriendsBtn.setProgress(0);
                meetAdapter.notifyMapData();
                initNetWork(chatId);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CircularProgressButtonTools.showErr(meetAddFriendsBtn);
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
