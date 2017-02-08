package com.xiandong.fst.presenter;


import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.HotPintsModel;
import com.xiandong.fst.model.HotPintsModelImpl;
import com.xiandong.fst.model.bean.HotPintsBean;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.model.bean.SearchPintsBean;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.view.HotPintsView;

import java.util.List;

/**
* Created by dell on 2017/01/20
*/

public class HotPintsPresenterImpl implements HotPintsPresenter{
    HotPintsModel hotPintsModel;
    HotPintsView hotPintsView;

    public HotPintsPresenterImpl (HotPintsView hotPintsView){
        this.hotPintsView = hotPintsView;
        this.hotPintsModel = new HotPintsModelImpl();
    }

    public void getHotPints(){
        this.hotPintsModel.getHotPints(this);
    }

    public void searchPints(String search, String name){
        hotPintsModel.searchPints(search , name , this);
    }

    @Override
    public void getHotPintsSuccess(List<HotPintsBean.ForumEntity> list) {
        for (HotPintsBean.ForumEntity hf: list){
            String[] position = hf.getPosition().split(";");
            LatLng latLng = new LatLng(Double.valueOf(position[0]), Double.valueOf(position[1]));
            MarkerOptions option = new MarkerOptions();
            option.animateType(MarkerOptions.MarkerAnimateType.drop);
            option.title(MarkMapTools.setTitle(Constant.MarkerType.FORUM, hf.getId()));
            option.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.froum_icon));
            if (MarkMapTools.getRedPacket() != null && MarkMapTools.getRedPacket().size() > 0) {
                if (MarkMapTools.isHavaRedPcaket(hf.getId())) {
                    if (MarkMapTools.getRedPacket().get(hf.getId()).getPosition() == latLng) {
                        return;
                    } else {
                        MarkMapTools.getRedPacket().get(hf.getId()).setPosition(latLng);
                    }
                } else {
                    MarkMapTools.getRedPacket().put(hf.getId(), (Marker) hotPintsView.loadForum().addOverlay(option));
                }
            } else {
                MarkMapTools.getRedPacket().put(hf.getId(), (Marker) hotPintsView.loadForum().addOverlay(option));
            }
        }
        hotPintsView.getHotPintsSuccess(list);
    }

    @Override
    public void fetHotPintsFails(String err) {
        hotPintsView.fetHotPintsFails(err);
    }

    @Override
    public void searchSuccess(SearchPintsBean friendsBean) {
        hotPintsView.searchSuccess(friendsBean);
    }

    @Override
    public void searchFails(String err) {
        hotPintsView.searchFails(err);
    }
}