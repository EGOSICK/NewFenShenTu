package com.xiandong.fst.view;

import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.model.bean.SearchAddressBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dell on 2017/1/16.
 */

public class MainActivityInterfaceManger {
    private static MainActivityInterfaceManger manger;

    private List<MainActivityInterface> list = new CopyOnWriteArrayList<>();
    private MainActivityInterfaceManger(){

    }

    public static MainActivityInterfaceManger getInstance(){
        if (manger == null){
            manger = new MainActivityInterfaceManger();
        }
        return manger;
    }

    public void registerListener(MainActivityInterface anInterface){
        list.add(anInterface);
    }

    public void unRegisterListener(MainActivityInterface anInterface){
        if (list.contains(anInterface)) {
            list.remove(anInterface);
        }
    }

    public void refresh(LatLng latLng){
        for (MainActivityInterface anInterface : list) {
            anInterface.onRefresh(latLng);
        }
    }

    public void mapChangeStart(){
        for (MainActivityInterface anInterface : list) {
            anInterface.onMapChangeStart();
        }
    }

    public void mapChanging(){
        for (MainActivityInterface anInterface : list) {
            anInterface.onMapChanging();
        }
    }

    public void mapChangeFinish(LatLng latLng ,String address){
        for (MainActivityInterface anInterface : list) {
            anInterface.OnMapChangeFinish(latLng , address);
        }
    }

    public void searchResult(List<SearchAddressBean> searchList){
        for (MainActivityInterface anInterface : list) {
            anInterface.onSearchResult(searchList);
        }
    }
}
