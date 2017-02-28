package com.xiandong.fst.view.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.xiandong.fst.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/2/15.
 */
@ContentView(R.layout.activity_message_webview)
public class MessageWebViewActivity extends AbsBaseActivity{
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.messageWv)
    WebView messageWv;
    @Override
    protected void initialize() {
        titleTitleTv.setText("系统消息");
        messageWv.loadUrl(getIntent().getStringExtra("url"));
    }

    @Event(type = View.OnClickListener.class,value = R.id.titleBackImg)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
