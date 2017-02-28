package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.HotPintDetailBean;
import com.xiandong.fst.model.bean.HotPintsBean;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.model.bean.SearchPintsBean;
import com.xiandong.fst.presenter.HotPintPresenterImpl;
import com.xiandong.fst.presenter.HotPintsPresenterImpl;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.adapter.RabbitSayAdapter;
import com.xiandong.fst.tools.adapter.RabbitSaySearchAdapter;
import com.xiandong.fst.tools.adapter.RabbitSaySearchAddressAdapter;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.HotPintView;
import com.xiandong.fst.view.HotPintsView;
import com.xiandong.fst.view.MainActivityInterface;
import com.xiandong.fst.view.MainActivityInterfaceManger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 兔子说
 */

@ContentView(R.layout.fragment_rabbit_say)
public class RabbitSayFragment extends AbsBaseFragment implements HotPintsView, HotPintView {
    private static RabbitSayFragment rabbitSayFragment = null;
    private Context context;

    public static RabbitSayFragment getInstance() {
        rabbitSayFragment = new RabbitSayFragment();
        return rabbitSayFragment;
    }

    public RabbitSayFragment showPager() {
        getMainActivity().cleanMarks();
        MarkMapTools.choosePager(false, false, false, true, false);
        initHotPints();
        return rabbitSayFragment;
    }

    @ViewInject(R.id.rabbitSayChooseKOPView)
    View rabbitSayChooseKOPView;

    @ViewInject(R.id.rabSaySearchKOPTv)
    TextView rabSaySearchKOPTv;

    @ViewInject(R.id.rabSaySearchUODImg)
    ImageView rabSaySearchUODImg;

    @ViewInject(R.id.rabbitSaySearchResultLv)
    ListView rabbitSaySearchResultLv;
    @ViewInject(R.id.rabbitSayLv)
    ListView rabbitSayLv;
    @ViewInject(R.id.rabbitSaySendMsgView)
    View rabbitSaySendMsgView;
    @ViewInject(R.id.rabbitSaySendMsgImg)
    ImageView rabbitSaySendMsgImg;
    @ViewInject(R.id.rabbitSaySearchEt)
    EditText rabbitSaySearchEt;
    @ViewInject(R.id.rabbitSaySearchResultView)
    View rabbitSaySearchResultView;
    @ViewInject(R.id.rabbitSaySearchQXTv)
    TextView rabbitSaySearchQXTv;
    @ViewInject(R.id.rabbitSaySearchNeiView)
    ImageView rabbitSaySearchNeiView;

    @ViewInject(R.id.rabbitSaySendMsgEt)
    EditText rabbitSaySendMsgEt;
    HotPintsPresenterImpl presenter;
    RabbitSayAdapter adapter;
    HotPintPresenterImpl hfPresenter;
    String locationPosition;
    int searchType = 0;

    RabbitSaySearchAdapter searchAdapter;
    RabbitSaySearchAddressAdapter searchAddressAdapter;

    @Override
    protected void initialize() {
        context = getContext();
        adapter = new RabbitSayAdapter(context);
        rabbitSayLv.setAdapter(adapter);
        presenter = new HotPintsPresenterImpl(this);
        hfPresenter = new HotPintPresenterImpl(this);
        rabbitSaySearchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    rabbitSaySearchQXTv.setVisibility(View.VISIBLE);
                    rabbitSaySearchResultView.setVisibility(View.VISIBLE);
                    rabbitSaySearchNeiView.setVisibility(View.GONE);

                    rabbitSaySearchEt.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable != null && !StringUtil.isEmpty(editable.toString()))
                                switch (searchType) {
                                    case 0:
                                        presenter.searchPints("", editable.toString());
                                        break;
                                    case 1:
                                        getMainActivity().searchAddress(editable.toString());
                                        break;
                                }
                        }
                    });
                } else {
                    rabbitSaySearchResultView.setVisibility(View.GONE);
                    rabbitSaySearchQXTv.setVisibility(View.GONE);
                    rabbitSaySearchNeiView.setVisibility(View.VISIBLE);
                }
            }
        });


//        rabbitSaySendMsgEt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                    Rect r = new Rect();
//                    rabbitSaySendMsgEt.getWindowVisibleDisplayFrame(r);
//                    int screenHeight = rabbitSaySendMsgEt.getRootView().getHeight();
//                    softHeight = screenHeight - (r.bottom - r.top);
////                    boolean visible = softHeight > screenHeight / 3;
////                    if (!visible) {
////                        oh = softHeight;
////                    } else {
////                        ah = softHeight;
////                    }
//                Log.d("RabbitSayFragment", "softHeight:" + softHeight);
//
//            }
//        });

    }
//    int softHeight, oh, ah;
    @Event(type = View.OnClickListener.class, value = {R.id.rabbitSayChooseKOPView,
            R.id.rabbitSaySendMsgImg, R.id.rabbitSaySearchNeiView,
            R.id.rabbitSaySendMsgBtn, R.id.rabbitSaySearchQXTv})
    private void RabSayOnClick(View view) {
        switch (view.getId()) {
            case R.id.rabbitSaySearchQXTv:
                rabbitSaySearchEt.setText("");
                rabbitSaySearchEt.setFocusable(false);
                rabbitSaySearchEt.setFocusableInTouchMode(false);
                rabbitSaySearchEt.setFocusableInTouchMode(true);
                break;
            case R.id.rabbitSayChooseKOPView:
                final PopupWindow popupWindow = new PopupWindow();
                if (popupWindow.isShowing()) {
                    return;
                }
                View popView = LayoutInflater.from(context).inflate(R.layout.pop_rabbit_say_choose, null);
                View key = popView.findViewById(R.id.popRabbitSayChooseKeyTv);
                View position = popView.findViewById(R.id.popRabbitSayChoosePositionTv);
                popupWindow.setContentView(popView);
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(view, 0, 10);

                key.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rabSaySearchKOPTv.setText("关键字");
                        searchType = 0;
                        popupWindow.dismiss();
                    }
                });

                position.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rabSaySearchKOPTv.setText("位置");
                        searchType = 1;
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.rabbitSaySendMsgImg:
                rabbitSaySendMsgView.setVisibility(View.VISIBLE);
                rabbitSaySendMsgImg.setVisibility(View.GONE);
                break;
            case R.id.rabbitSaySendMsgBtn:
                rabbitSaySendMsgImg.setVisibility(View.VISIBLE);
                rabbitSaySendMsgView.setVisibility(View.GONE);

                if (StringUtil.isEmpty(rabbitSaySendMsgEt.getText().toString().trim()))
                    return;
                else
                    hfPresenter.huiFu(rabbitSaySendMsgEt.getText().toString().trim(),
                            "0", locationPosition);
                break;
            case R.id.rabbitSaySearchNeiView:
                if (rabbitSayLv.getVisibility() == View.GONE) {
                    rabbitSayLv.setVisibility(View.VISIBLE);
                } else {
                    rabbitSayLv.setVisibility(View.GONE);
                }
                break;

        }
    }

    private void initHotPints() {
        MainActivityInterfaceManger.getInstance().registerListener(new MainActivityInterface() {
            @Override
            public void onMapChangeStart() {

            }

            @Override
            public void onMapChanging() {

            }

            @Override
            public void OnMapChangeFinish(LatLng mapStatusChangeLatLng, String address) {

            }

            @Override
            public void onRefresh(LatLng location) {
                if (location != null && MarkMapTools.isSayPager) {
                    locationPosition = location.latitude + ";" + location.longitude;
                    presenter.getHotPints();
                    adapter.setPosition(locationPosition);
                }
            }

            @Override
            public void onSearchResult(List<SearchAddressBean> list) {
                if (list != null && list.size() > 0 && MarkMapTools.isSayPager) {
                    searchAddressAdapter = new RabbitSaySearchAddressAdapter(context);
                    rabbitSaySearchResultLv.setAdapter(searchAddressAdapter);
                    searchAddressAdapter.addData(list);
                    searchAddressAdapter.setListener(new RabbitSaySearchAddressAdapter.searchResultListener() {
                        @Override
                        public void resultListener(LatLng latLng, String address) {
                            presenter.searchPints(latLng.latitude + ";" + latLng.longitude, "");
                            rabbitSaySearchEt.setText(address);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getHotPintsSuccess(List<HotPintsBean.ForumEntity> list) {
        adapter.addData(list);
    }

    @Override
    public void getHotPintsFails(String err) {

    }

    @Override
    public BaiduMap loadForum() {
        return getMainActivity().getBaiDuMap();
    }

    @Override
    public void searchSuccess(SearchPintsBean searchBean) {
        searchAdapter = new RabbitSaySearchAdapter(context);
        rabbitSaySearchResultLv.setAdapter(searchAdapter);
        searchAdapter.addData(searchBean.getForum());
    }

    @Override
    public void searchFails(String err) {

    }

    @Override
    public void getHotPintSuccess(HotPintDetailBean bean) {

    }

    @Override
    public void getHotPintFails(String err) {

    }

    @Override
    public void huiFuSuccess() {
        rabbitSaySendMsgEt.setText("");
    }
}
