package com.xiandong.fst.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.view.fragment.ChatBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/2/8.
 */
@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends AbsBaseActivity{
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;


    @Override
    protected void initialize() {
        titleTitleTv.setText("留言");

        ChatBaseFragment fragment = new ChatBaseFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, "kefu");
        fragment.setArguments(args);
        fragment.setMyAttributes(AppDbManager.getLastUser().getUserName(), AppDbManager.getLastUser().getUserImg());
        DemoHelper.getInstance().setMeAvatar(AppDbManager.getLastUser().getUserImg());
        getSupportFragmentManager().
                beginTransaction().replace(R.id.feedBackChatView, fragment).commit();
    }

    @Event(type = View.OnClickListener.class , value = {R.id.titleBackImg})
    private void onClickListener(View v){
        switch (v.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
