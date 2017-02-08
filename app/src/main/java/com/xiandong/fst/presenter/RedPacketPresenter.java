package com.xiandong.fst.presenter;

import com.xiandong.fst.model.bean.RedPacketBean;

import java.util.List;

/**
 * Created by dell on 2017/1/21.
 */

public interface RedPacketPresenter  {
    void loadRedPacketSuccess(List<RedPacketBean.RedbagEntity> list);
    void loadRedPacketFails(String err);
}
