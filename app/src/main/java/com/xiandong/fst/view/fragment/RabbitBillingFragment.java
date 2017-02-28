package com.xiandong.fst.view.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
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
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CostomAnimation;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.DensityUtil;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.utils.TimeUtil;
import com.xiandong.fst.utils.ViewUtils;
import com.xiandong.fst.view.BillingView;
import com.xiandong.fst.view.MainActivityInterface;
import com.xiandong.fst.view.MainActivityInterfaceManger;
import com.xiandong.fst.view.activity.PayActivity;
import com.xiandong.fst.view.activity.SearchAddressActivity;

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
        MarkMapTools.choosePager(false, true, false, false, false);

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
    TextView rabbitBillingAddressEt;
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
    Context context;
    int[] billingViewWH;
    int nowHeight, addressEtWH;

    @ViewInject(R.id.rabbitBillingAddressView)
    View rabbitBillingAddressView;

    @Override
    protected void initialize() {
        initView();
        initTime();
        initPosition();
    }

    private void initView() {
        context = getContext();
        getMainActivity().cleanMarks();
        billingPresenter = new BillingPresenterImpl(this);
        rabbitBillingSubmitBtn.setIdleText("提交");
        rabbitBillingSubmitBtn.setText("提交");
        rabbitBillingAddressEt.setText(getMainActivity().getLocationAddress());
        rabbitBillingPhoneEt.setText(AppDbManager.getLastUser().getUserPhone());
        addressEtWH = DensityUtil.dip2px(context, 40);
        billingViewWH = ViewUtils.getViewWAndH(rabbitBillingView);
        nowHeight = billingViewWH[1];
        rabbitBillingMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().length() > 0) {
                    CharSequence str = s.toString().subSequence(0, 1);
                    if (StringUtil.isEquals(str.toString(), "0") ||
                            StringUtil.isEquals(str.toString(), ".")) {
                        rabbitBillingMoneyEt.setText("");
                        rabbitBillingMoneyEt.setSelection(0);
                    } else {
                        if (s.toString().contains(".")) {
                            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                                s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                                rabbitBillingMoneyEt.setText(s);
                                rabbitBillingMoneyEt.setSelection(s.length());
                            }
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        rabbitBillingAddressEt.setSelected(true);
    }

    private void initTime() {
        rabbitBillingDateTv.setText(
                new StringBuffer().append(pad(year))
                        .append("年").append(pad(month + 1))
                        .append("月").append(pad(day))
                        .append("日"));
        rabbitBillingTimeTv.setText(
                new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));
        time.append(year).append("-").append(month + 1).append("-").append(day)
                .append(" ").append(hourOfDay).append(":").append(minute);
    }

    private void initPosition() {
        MainActivityInterfaceManger.getInstance().registerListener(new MainActivityInterface() {
            @Override
            public void onMapChangeStart() {
                if (nowHeight > addressEtWH) {
                    CostomAnimation.zoomAnimate(rabbitBillingView, rabbitBillingView,
                            ViewUtils.getViewWAndH(rabbitBillingView)[1], addressEtWH, "height",
                            Constant.EXECUTIONTIME * 2);
                    nowHeight = addressEtWH;
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

    @Event(type = View.OnClickListener.class, value = {R.id.rabbitBillingTimeView,
            R.id.rabbitBillingSubmitBtn, R.id.rabbitBillingAddressView})
    private void rabbitBillingOnClick(View view) {
        switch (view.getId()) {
            case R.id.rabbitBillingAddressView:
                if (nowHeight <= addressEtWH) {
                    CostomAnimation.zoomAnimate(rabbitBillingView, rabbitBillingView,
                            addressEtWH, ViewUtils.getViewWAndH(rabbitBillingView)[1],
                            "height", Constant.EXECUTIONTIME * 2);
                    nowHeight = billingViewWH[1];
                } else {
                    Intent intent = new Intent(context, SearchAddressActivity.class)
                            .putExtra("city", getMainActivity().getLocationCity());
//                    getMainActivity().startActivityForResult(intent, 0, ActivityOptions.makeSceneTransitionAnimation(
//                            getMainActivity(), rabbitBillingAddressView, "shareName").toBundle());
                    startActivityForResult(intent, 0);
                }
                break;
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
                String time = String.valueOf((TimeUtil.getLongFromTime(this.time.toString(), "") / 100));
                billingPresenter.billing(position, address, title, money, time, phone, detail);
                break;
        }
    }

    @Override
    public void billingMsgErr(String err) {
        CustomToast.customToast(false, err, getContext());
    }

    @Override
    public void billingMsgTrue(PayBean payBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payBean", payBean);
        startActivity(new Intent(getContext(), PayActivity.class).putExtra("payBean", bundle)
                .setAction(Constant.BILLINGPAY));
        rabbitBillingMoneyEt.setText("");
        rabbitBillingDetialEt.setText("");
        rabbitBillingTitleEt.setText("");
        initTime();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 0) {
                String searchAddress = data.getStringExtra("address");
                rabbitBillingAddressEt.setText(searchAddress);
                LatLng latLng = data.getParcelableExtra("position");
                getMainActivity().positioning(latLng);
            }
        }
    }
}
