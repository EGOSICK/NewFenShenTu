package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.easeui.EaseConstant;
import com.xiandong.fst.R;
import com.xiandong.fst.tools.ChatTools;
import com.xiandong.fst.view.fragment.ChatBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/22.
 */
@ContentView(R.layout.activity_single_chat)
public class SingleChatActivity extends AbsBaseActivity{
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;

    @Override
    protected void initialize() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String img = intent.getStringExtra("img");
        String title = intent.getStringExtra("title");
        titleTitleTv.setText(title);


        ChatBaseFragment fragment = new ChatBaseFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, id);
        fragment.setArguments(args);
        fragment.setMyAttributes(name, img);
        DemoHelper.getInstance().setMeAvatar(img);
        getSupportFragmentManager().
                beginTransaction().add(R.id.chatView, fragment).commit();
    }

    @Event(type = View.OnClickListener.class , value = {R.id.titleBackImg})
    private void onClickListener(View view){
        switch (view.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
