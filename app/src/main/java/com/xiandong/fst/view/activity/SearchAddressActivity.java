package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.tools.adapter.RabbitSaySearchAddressAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/2/20.
 */
@ContentView(R.layout.activity_search_address)
public class SearchAddressActivity extends AbsBaseActivity {
    @ViewInject(R.id.searchAddressEt)
    EditText searchAddressEt;
    @ViewInject(R.id.searchAddressLv)
    ListView searchAddressLv;
    SuggestionSearch mSuggestionSearch;
    String locationCity;
    RabbitSaySearchAddressAdapter adapter;

    @Override
    protected void initialize() {
        locationCity = getIntent().getStringExtra("city");
        adapter = new RabbitSaySearchAddressAdapter(this);
        searchAddressLv.setAdapter(adapter);
        adapter.setListener(new RabbitSaySearchAddressAdapter.searchResultListener() {
            @Override
            public void resultListener(LatLng latLng, String address) {
                Intent intent = new Intent();
                intent.putExtra("address", address);
                intent.putExtra("position", latLng);
                setResult(0, intent);
                finish();
            }
        });

        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResult != null && suggestionResult.getAllSuggestions() != null &&
                        suggestionResult.getAllSuggestions().size() > 0) {
                    List<SearchAddressBean> searchList = new ArrayList<SearchAddressBean>();
                    for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
                        SearchAddressBean bean = new SearchAddressBean();
                        bean.setCity(info.city);
                        bean.setDistrict(info.district);
                        bean.setKey(info.key);
                        bean.setPt(info.pt);
                        searchList.add(bean);
                    }
                    adapter.addData(searchList);
                }
            }
        });

        searchAddressEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    mSuggestionSearch.requestSuggestion(new
                            SuggestionSearchOption().city(locationCity).keyword(editable.toString()));
                }
            }
        });
    }

    @Event(type = View.OnClickListener.class, value = R.id.searchAddressCancelBtn)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchAddressCancelBtn:
                finish();
                break;
        }
    }
}
