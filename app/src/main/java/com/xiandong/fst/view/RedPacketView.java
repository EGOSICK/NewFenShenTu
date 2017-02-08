package com.xiandong.fst.view;

import com.baidu.mapapi.map.BaiduMap;
import com.xiandong.fst.model.bean.RedPacketBean;

import java.util.List;

/**
* Created by dell on 2017/01/21
*/

public interface RedPacketView{
    BaiduMap loadRedPacket();
    void loadRedPacketFails(String err);
}