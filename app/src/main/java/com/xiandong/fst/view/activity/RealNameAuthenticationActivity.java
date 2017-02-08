package com.xiandong.fst.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.presenter.RealNamePresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.view.RealNameView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/17.
 */
@ContentView(R.layout.activity_real_name_authentication)
public class RealNameAuthenticationActivity extends AbsBaseActivity implements RealNameView {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.realNameAuthenticationBtn)
    CircularProgressButton realNameAuthenticationBtn;
    @ViewInject(R.id.realNameNameEt)
    EditText realNameNameEt;
    @ViewInject(R.id.realNameCardNumEt)
    EditText realNameCardNumEt;

    Context context;

    @Override
    protected void initialize() {
        context = this;
        titleTitleTv.setText("身份认证");
        realNameAuthenticationBtn.setIndeterminateProgressMode(true);
        realNameAuthenticationBtn.setIdleText("确认");
        realNameAuthenticationBtn.setText("确认");
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg,
            R.id.realNameAuthenticationBtn})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.realNameAuthenticationBtn:
                String name = realNameNameEt.getText().toString().trim();
                String cardNum = realNameCardNumEt.getText().toString().trim();
                RealNamePresenterImpl presenter = new RealNamePresenterImpl(this);
                presenter.certification(name, cardNum);
                CircularProgressButtonTools.showLoding(realNameAuthenticationBtn);
                break;
        }
    }

    @Override
    public void certificationSuccess() {
        CustomToast.customToast(true, "提交成功,请耐心等待审核", context);
        CircularProgressButtonTools.showTrue(realNameAuthenticationBtn);
        setResult(0);
        finish();
    }

    @Override
    public void certificationFails(String err) {
        CustomToast.customToast(false, err, context);
        CircularProgressButtonTools.showErr(realNameAuthenticationBtn);
    }
}
