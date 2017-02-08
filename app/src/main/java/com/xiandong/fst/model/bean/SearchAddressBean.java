package com.xiandong.fst.model.bean;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by dell on 2017/1/22.
 */

public class SearchAddressBean {

    private String city ;
    private String district;
    private String key;
    private LatLng pt;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LatLng getPt() {
        return pt;
    }

    public void setPt(LatLng pt) {
        this.pt = pt;
    }
}
