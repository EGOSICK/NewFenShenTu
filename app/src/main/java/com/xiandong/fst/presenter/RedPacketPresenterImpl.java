package com.xiandong.fst.presenter;


import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.RedPacketModel;
import com.xiandong.fst.model.RedPacketModelImpl;
import com.xiandong.fst.model.bean.RedPacketBean;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.RedPacketView;

import java.util.List;

/**
 * Created by dell on 2017/01/21
 */

public class RedPacketPresenterImpl implements RedPacketPresenter {
    RedPacketModel model;
    RedPacketView view;

    public RedPacketPresenterImpl(RedPacketView view) {
        this.view = view;
        this.model = new RedPacketModelImpl();
    }

    public void loadRedPacket() {
        model.loadRedPacket(this);
    }

    @Override
    public void loadRedPacketSuccess(List<RedPacketBean.RedbagEntity> list) {
        for (RedPacketBean.RedbagEntity redPacket : list) {
            String[] position = redPacket.getPosition().split(";");
            LatLng latLng = new LatLng(Double.valueOf(position[0]), Double.valueOf(position[1]));
            MarkerOptions option = new MarkerOptions();
            option.animateType(MarkerOptions.MarkerAnimateType.drop);
            option.title(MarkMapTools.setTitle(Constant.MarkerType.REDPACKET, redPacket.getId(), redPacket.getTotalfee()
                    , redPacket.getUser()));
            option.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_red_packet));
            if (MarkMapTools.getRedPacket() != null && MarkMapTools.getRedPacket().size() > 0) {
                if (MarkMapTools.isHavaRedPcaket(redPacket.getId())) {
                    if (MarkMapTools.getRedPacket().get(redPacket.getId()).getPosition() == latLng) {
                        return;
                    } else {
                        MarkMapTools.getRedPacket().get(redPacket.getId()).setPosition(latLng);
                    }
                } else {
                    MarkMapTools.getRedPacket().put(redPacket.getId(), (Marker) view.loadRedPacket().addOverlay(option));
                }
            } else {
                MarkMapTools.getRedPacket().put(redPacket.getId(), (Marker) view.loadRedPacket().addOverlay(option));
            }
        }
    }

    @Override
    public void loadRedPacketFails(String err) {
        view.loadRedPacketFails(err);
    }
}