package com.xiandong.fst.view.activity;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.presenter.BingDingPhoneNumPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.BingDingPhoneNumView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 绑定手机 activity
 */
@ContentView(R.layout.activity_bingdin_phone_num)
public class BingDinPhoneNumActivity extends AbsBaseActivity implements BingDingPhoneNumView {

    @ViewInject(R.id.logInPhoneEt)
    EditText logInPhoneEt;
    @ViewInject(R.id.logInPasswordEt)
    EditText logInPasswordEt;
    @ViewInject(R.id.logInCodeEt)
    EditText logInCodeEt;
    @ViewInject(R.id.getCodeMsgTv)
    TextView getCodeMsgTv;
    @ViewInject(R.id.logInPhoneView)
    View logInPhoneView;
    @ViewInject(R.id.logInCodeView)
    View logInCodeView;
    @ViewInject(R.id.bdPhoneNumCompleteBtn)
    CircularProgressButton bdPhoneNumCompleteBtn;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;

    BingDingPhoneNumPresenterImpl bingDingPhoneNumPresenter;
    Context context;

    @Override
    protected void initialize() {
        context = this;
        bingDingPhoneNumPresenter = new BingDingPhoneNumPresenterImpl(this);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        logInPhoneView.measure(w, h);
        int height = logInPhoneView.getMeasuredHeight();
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) logInCodeView.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = height;

        bdPhoneNumCompleteBtn.setIndeterminateProgressMode(true);
        bdPhoneNumCompleteBtn.setText("完成");
        titleTitleTv.setText("绑定手机");
    }

    @Event(type = View.OnClickListener.class, value = {R.id.bdPhoneNumCompleteBtn,
            R.id.getCodeMsgTv, R.id.titleBackImg})
    private void completeBtnClick(View v) {
        switch (v.getId()) {
            case R.id.bdPhoneNumCompleteBtn:
                bdPhoneNumCompleteBtn.setProgress(0);
                bdPhoneNumCompleteBtn.setProgress(1);
                bingDingPhoneNumPresenter.bingDingPhoneNum(
                        logInPhoneEt.getText().toString().trim(),
                        logInCodeEt.getText().toString().trim(),
                        logInPasswordEt.getText().toString().trim(), this);
                break;
            case R.id.getCodeMsgTv:
                String phone = logInPhoneEt.getText().toString().trim();
                if (StringUtil.isTelPhone(phone)) {
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long l) {
                            getCodeMsgTv.setClickable(false);
                            getCodeMsgTv.setText(l / 1000 + "秒后重新获取");
                        }

                        @Override
                        public void onFinish() {
                            getCodeMsgTv.setClickable(true);
                            getCodeMsgTv.setText("重新获取");
                        }
                    }.start();
                    bingDingPhoneNumPresenter.sendCodeMessage(phone, context);
                } else {
                    getCodeMsgFails(getString(R.string.phone_err));
                }
                break;
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    @Override
    public void bingDingSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
        bdPhoneNumCompleteBtn.setProgress(100);
        finish();
    }

    @Override
    public void showErrMessage(String err) {
        CustomToast.customToast(true, err, context);
        bdPhoneNumCompleteBtn.setProgress(-1);
    }

    @Override
    public void getCodeMsgSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
    }

    @Override
    public void getCodeMsgFails(String err) {
        CustomToast.customToast(false, err, context);
    }

}
