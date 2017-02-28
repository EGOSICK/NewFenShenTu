package com.xiandong.fst.view.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.tools.dbmanager.AppDbManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/2/15.
 */
@ContentView(R.layout.activity_qian_dao)
public class QianDaoActivity extends AbsBaseActivity{
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.qianDaoWv)
    WebView webView;
    @Override
    protected void initialize() {
        titleTitleTv.setText("签到");
        loadHtml();
    }

    @Event(type = View.OnClickListener.class , value = {R.id.titleBackImg})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    public void loadHtml() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings wSet = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        wSet.setJavaScriptEnabled(true);

        webView.loadUrl("http://www.fenshentu.com/qiandao.php/Index/index/id/"+ AppDbManager.getUserId());
    }
}
