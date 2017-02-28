package com.xiandong.fst.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.view.fragment.ChatBaseFragment;
import com.xiandong.fst.view.fragment.MyChatListFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/24.
 */
@ContentView(R.layout.activity_my_chat)
public class MyChatActivity extends AbsBaseActivity {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.chatListFg)
    View chatListFg;
    boolean isListOpen = true;
    MyChatListFragment chatListFragment;
    String friendId;

    @Override
    protected void initialize() {
        if (getIntent() != null) {
            friendId = getIntent().getStringExtra("id");
        }
        chatListFragment = new MyChatListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.chatListFg, chatListFragment);
        ft.commit();
    }

    public String getSelectFriendId() {
        return friendId;
    }

    public void replaceChat(String id, String title) {
        titleTitleTv.setText(title);
        ChatBaseFragment fragment = new ChatBaseFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, id);
        fragment.setArguments(args);
        fragment.setFriendId(AppDbManager.getUserId());
        fragment.setMyAttributes(AppDbManager.getLastUser().getUserName(),
                AppDbManager.getLastUser().getUserImg());
        DemoHelper.getInstance().setMeAvatar(AppDbManager.getLastUser().getUserImg());
        getSupportFragmentManager().
                beginTransaction().replace(R.id.chatContentFg, fragment).commit();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.chatListSwitch})
    private void onCliclkListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.chatListSwitch:
                if (isListOpen) {
                    chatListFg.setVisibility(View.GONE);
                    isListOpen = false;
                } else {
                    chatListFg.setVisibility(View.VISIBLE);
                    isListOpen = true;
                }
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            friendId = intent.getStringExtra("id");
        }
        chatListFragment = new MyChatListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.chatListFg, chatListFragment);
        ft.commit();
    }
}
