package com.xiandong.fst.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.PayBean;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.presenter.BillingPresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CostomAnimation;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.utils.TimeUtil;
import com.xiandong.fst.utils.ViewUtils;
import com.xiandong.fst.view.BillingView;
import com.xiandong.fst.view.MainActivityInterface;
import com.xiandong.fst.view.MainActivityInterfaceManger;
import com.xiandong.fst.view.activity.PayActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.List;

import static com.xiandong.fst.utils.TimeUtil.pad;

/**
 * 发单
 */
@ContentView(R.layout.fragment_rabbit_billing)
public class RabbitBillingFragment extends AbsBaseFragment implements BillingView {
    static RabbitBillingFragment rabbitBillingFragment = null;

    public static RabbitBillingFragment getInstance() {
        rabbitBillingFragment = new RabbitBillingFragment();
        return rabbitBillingFragment;
    }

    public RabbitBillingFragment showPager() {
        getMainActivity().cleanMarks();

        initPosition();
        return rabbitBillingFragment;
    }

    private Calendar mCalendar = Calendar.getInstance();
    private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY) + 3;
    private int minute = mCalendar.get(Calendar.MINUTE);
    private int day = mCalendar.get(Calendar.DAY_OF_MONTH);
    private int month = mCalendar.get(Calendar.MONTH);
    private int year = mCalendar.get(Calendar.YEAR);
    private StringBuffer time = new StringBuffer();
    @ViewInject(R.id.rabbitBillingAddressEt)
    EditText rabbitBillingAddressEt;
    @ViewInject(R.id.rabbitBillingTitleEt)
    EditText rabbitBillingTitleEt;
    @ViewInject(R.id.rabbitBillingMoneyEt)
    EditText rabbitBillingMoneyEt;
    @ViewInject(R.id.rabbitBillingPhoneEt)
    EditText rabbitBillingPhoneEt;
    @ViewInject(R.id.rabbitBillingDetialEt)
    EditText rabbitBillingDetialEt;
    @ViewInject(R.id.rabbitBillingDateTv)
    TextView rabbitBillingDateTv;
    @ViewInject(R.id.rabbitBillingTimeTv)
    TextView rabbitBillingTimeTv;
    @ViewInject(R.id.rabbitBillingSubmitBtn)
    CircularProgressButton rabbitBillingSubmitBtn;
    @ViewInject(R.id.rabbitBillingView)
    View rabbitBillingView;
    BillingPresenterImpl billingPresenter;
    String position;

    int[] addressEtWH, billingViewWH;
    int nowHeight;

    @ViewInject(R.id.rabbitBillingAddressView)
    View rabbitBillingAddressView;

    @Override
    protected void initialize() {
        initView();
        initTime();
        nowHeight = billingViewWH[1];
    }

    private void initView() {
        getMainActivity().cleanMarks();
        billingPresenter = new BillingPresenterImpl(this);
        rabbitBillingSubmitBtn.setIdleText("提交");
        rabbitBillingSubmitBtn.setText("提交");
        rabbitBillingDateTv.setText(
                new StringBuffer().append(pad(year))
                        .append("年").append(pad(month + 1))
                        .append("月").append(pad(day))
                        .append("日"));
        rabbitBillingTimeTv.setText(
                new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));

        addressEtWH = ViewUtils.getViewWAndH(rabbitBillingAddressView);
        billingViewWH = ViewUtils.getViewWAndH(rabbitBillingView);

    }

    private void initTime() {
        time.append(year).append("-").append(month + 1).append("-").append(day)
                .append(" ").append(hourOfDay).append(":").append(minute);
    }

    private void initPosition() {
        MainActivityInterfaceManger.getInstance().registerListener(new MainActivityInterface() {
            @Override
            public void onMapChangeStart() {
                rabbitBillingAddressEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            if (nowHeight <= addressEtWH[1]+2) {
                                CostomAnimation.zoomAnimate(rabbitBillingView, rabbitBillingView,
                                        addressEtWH[1] + 2, ViewUtils.getViewWAndH(rabbitBillingView)[1],
                                        "height", Constant.EXECUTIONTIME * 2);
                                nowHeight = billingViewWH[1];
                            }
                        }
                    }
                });

                if (nowHeight > addressEtWH[1] + 2) {
                    CostomAnimation.zoomAnimate(rabbitBillingView, rabbitBillingView,
                            ViewUtils.getViewWAndH(rabbitBillingView)[1], addressEtWH[1] + 2, "height",
                            Constant.EXECUTIONTIME * 2);
                    rabbitBillingAddressEt.setFocusable(false);
                    rabbitBillingAddressEt.setFocusableInTouchMode(false);
                    rabbitBillingAddressEt.setFocusableInTouchMode(true);
                    nowHeight = addressEtWH[1] + 2;
                }
            }

            @Override
            public void onMapChanging() {

            }

            @Override
            public void OnMapChangeFinish(LatLng mapStatusChangeLatLng, String address) {
                position = mapStatusChangeLatLng.latitude + ";" + mapStatusChangeLatLng.longitude;
                rabbitBillingAddressEt.setText(address);
            }

            @Override
            public void onRefresh(LatLng location) {
            }

            @Override
            public void onSearchResult(List<SearchAddressBean> list) {

            }
        });
    }

    @Event(type = View.OnClickListener.class, value = {R.id.rabbitBillingTimeView, R.id.rabbitBillingSubmitBtn})
    private void rabbitBillingOnClick(View view) {
        switch (view.getId()) {
            case R.id.rabbitBillingTimeView:
                StyledDialogTools.datePickerDialog(
                        getActivity().getFragmentManager(),
                        rabbitBillingDateTv, rabbitBillingTimeTv,
                        new StyledDialogTools.PickerDialogListener() {
                            @Override
                            public void pickerDate(int day, int month, int year) {
                                time.append(year).append("-").append(month + 1).append("-").append(day);
                            }

                            @Override
                            public void pickerTime(int minute, int hour) {
                                time.append(" ").append(hour).append(":").append(minute);
                            }
                        });
                break;
            case R.id.rabbitBillingSubmitBtn:
                String address = rabbitBillingAddressEt.getText().toString().trim();
                String title = rabbitBillingTitleEt.getText().toString().trim();
                String money = rabbitBillingMoneyEt.getText().toString().trim();
                String phone = rabbitBillingPhoneEt.getText().toString().trim();
                String detail = rabbitBillingDetialEt.getText().toString().trim();
                String time = String.valueOf((TimeUtil.getLongFromTime(this.time.toString(), "")/100));
                billingPresenter.billing(position, address, title, money, time, phone, detail);
                CircularProgressButtonTools.showLoding(rabbitBillingSubmitBtn);
                break;
        }
    }

    @Override
    public void billingMsgErr(String err) {
        CustomToast.customToast(false, err, getContext());
        CircularProgressButtonTools.showErr(rabbitBillingSubmitBtn);
    }

    @Override
    public void billingMsgTrue(PayBean payBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payBean", payBean);
        CircularProgressButtonTools.showTrue(rabbitBillingSubmitBtn);
        startActivity(new Intent(getContext(), PayActivity.class).putExtra("payBean", bundle)
                .setAction(Constant.BILLINGPAY));
    }
}
