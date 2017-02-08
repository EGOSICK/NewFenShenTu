package com.xiandong.fst.presenter;


import com.xiandong.fst.model.HotPintModel;
import com.xiandong.fst.model.HotPintModelImpl;
import com.xiandong.fst.model.bean.HotPintDetailBean;
import com.xiandong.fst.view.HotPintView;

/**
 * Created by dell on 2017/01/22
 */

public class HotPintPresenterImpl implements HotPintPresenter {
    HotPintView view;
    HotPintModel model;

    public HotPintPresenterImpl(HotPintView view) {
        this.view = view;
        this.model = new HotPintModelImpl();
    }

    public void getHotPintMsg(String id) {
        model.getHotPintMsg(id, this);
    }

    public void huiFu(String msg,String pid , String position){
        model.huiFu(msg,pid,position,this);
    }

    @Override
    public void getHotPintSuccess(HotPintDetailBean bean) {
        view.getHotPintSuccess(bean);
    }

    @Override
    public void fetHotPintFails(String err) {
        view.fetHotPintFails(err);
    }

    @Override
    public void huiFuSuccess() {
        view.huiFuSuccess();
    }
}