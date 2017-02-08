package com.xiandong.fst.view;

import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.model.bean.SearchAddressBean;

import java.util.List;

/**
 * Created by dell on 2017/1/11.
 */

public interface MainActivityInterface {
    void onMapChangeStart();

    void onMapChanging();

    void OnMapChangeFinish(LatLng mapStatusChangeLatLng, String address);

    void onRefresh(LatLng location);

    void onSearchResult(List<SearchAddressBean> list);
}
