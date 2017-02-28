package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.MessageBean;
import com.xiandong.fst.tools.adapter.MessageAdapter;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by dell on 2017/2/15.
 */
@ContentView(R.layout.activity_message)
public class MessageActivity extends AbsBaseActivity {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.messageLv)
    ListView messageLv;

    MessageAdapter adapter;

    @Override
    protected void initialize() {
        titleTitleTv.setText("系统消息");
        adapter = new MessageAdapter(this);
        messageLv.setAdapter(adapter);
        messageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MessageActivity.this, MessageWebViewActivity.class)
                        .putExtra("url",adapter.getUrl(i)));
            }
        });
        initNetWork();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    private void initNetWork() {
        RequestParams params = new RequestParams(Constant.APIURL + "notice");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        MessageBean messageBean = GsonUtil.fromJson(result, MessageBean.class);
                        adapter.addData(messageBean.getNotice());
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
}
