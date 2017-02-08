package com.xiandong.fst.model;


import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.presenter.SearchFriendsPresenter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/01/20
 */

public class SearchFriendsModelImpl implements SearchFriendsModel {

    @Override
    public void searchFriends(String search, final SearchFriendsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "search");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("searchname", search);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("SearchFriendsModelImpl", result);
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        List<SearchFriendsBean> list = new ArrayList<>();
                        try {
                            JSONObject searchObj = new JSONObject(result);
                            JSONArray phoneArray = searchObj.getJSONArray("phone");
                            if (phoneArray != null && phoneArray.length() > 0){
                                for (int i = 0; i < phoneArray.length(); i++) {
                                    JSONObject phoneObj = phoneArray.getJSONObject(i);
                                    SearchFriendsBean searchFriendsBean = new SearchFriendsBean();
                                    searchFriendsBean.setImg(phoneObj.getString("img"));
                                    searchFriendsBean.setFlag(phoneObj.getString("flag"));
                                    searchFriendsBean.setHaoyou(phoneObj.getInt("haoyou"));
                                    searchFriendsBean.setId(phoneObj.getString("id"));
                                    searchFriendsBean.setNicheng(phoneObj.getString("phone"));
                                    searchFriendsBean.setPosition(phoneObj.getString("position"));
                                    searchFriendsBean.setNicheng(phoneObj.getString("nicheng"));
                                    list.add(searchFriendsBean);
                                }
                            }
                            JSONArray nameArray = searchObj.getJSONArray("nicheng");
                            if (nameArray != null && nameArray.length() > 0){
                                for (int i = 0; i < nameArray.length(); i++) {
                                    JSONObject nameObj = nameArray.getJSONObject(i);
                                    SearchFriendsBean searchFriendsBean = new SearchFriendsBean();
                                    searchFriendsBean.setImg(nameObj.getString("img"));
                                    searchFriendsBean.setFlag(nameObj.getString("flag"));
                                    searchFriendsBean.setHaoyou(nameObj.getInt("haoyou"));
                                    searchFriendsBean.setId(nameObj.getString("id"));
                                    searchFriendsBean.setNicheng(nameObj.getString("phone"));
                                    searchFriendsBean.setPosition(nameObj.getString("position"));
                                    searchFriendsBean.setNicheng(nameObj.getString("nicheng"));
                                    list.add(searchFriendsBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        presenter.searchFriendsSuccess(list);
                        break;
                    default:
                        presenter.searchFriendsFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.searchFriendsFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}