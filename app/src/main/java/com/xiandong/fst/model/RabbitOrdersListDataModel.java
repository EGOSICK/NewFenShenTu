package com.xiandong.fst.model;

import com.xiandong.fst.presenter.RabbitOrdersListDataPresenter;

/**
 * Created by dell on 2017/01/09
 */

public interface RabbitOrdersListDataModel {
    void getRabbitOrdersListData(String sort, RabbitOrdersListDataPresenter presenter);

}