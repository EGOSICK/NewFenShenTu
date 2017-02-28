package com.xiandong.fst.model;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.ContactBean;
import com.xiandong.fst.presenter.ContactPresenter;
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
 * Created by dell on 2017/01/19
 */

public class ContactModelImpl implements ContactModel {
    private JSONArray jsonArray = new JSONArray();

    @Override
    public void upDateContact(Context context, ContactPresenter presenter) {
        getPhoneContacts(context, presenter);
    }

    @Override
    public void getContactDate(final ContactPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "tongxunlu");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("ContactModelImpl", result);
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        List<ContactBean> list = new ArrayList<>();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            JSONArray ja = jsonObject.getJSONArray("tongxunlu");
                            for (int i = 0; i < ja.length(); i++) {
                                ContactBean contactBean = new ContactBean();
                                JSONObject u = (JSONObject) ja.get(i);
                                String id = "";
                                String img = "";
                                if (u.has("user")) {
                                    JSONObject user = u.getJSONObject("user");
                                    id = user.getString("id");
                                    img = user.getString("img");
                                }
                                String name = u.getString("contactName");
                                String phone = u.getString("phoneNumber");
                                String status = u.getString("status");
                                String haoyou = u.getString("haoyou");
                                contactBean.setName(name);
                                contactBean.setHaoyou(haoyou);
                                contactBean.setPhone(phone);
                                contactBean.setStatus(status);
                                contactBean.setId(id);
                                contactBean.setImg(img);
                                list.add(contactBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        presenter.getContactDateSuccess(list);
                        break;
                    default:
                        presenter.getContactDateFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                presenter.getContactDateFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 得到手机通讯录联系人信息
     **/
    private void getPhoneContacts(Context context, ContactPresenter presenter) {
        ContentResolver resolver = context.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                Constant.PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(Constant.PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                //得到联系人名称
                String contactName = phoneCursor.getString(Constant.PHONES_DISPLAY_NAME_INDEX);
                try {
                    JSONObject userMessage = new JSONObject();
                    userMessage.put("phoneNumber", phoneNumber);
                    userMessage.put("contactName", contactName);
                    jsonArray.put(userMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            phoneCursor.close();
            if (jsonArray != null && jsonArray.length() > 0)
                upDate(presenter);
        }
    }

    /**
     * 上传
     */
    private void upDate(final ContactPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "addtongxunlu");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("jsontel", jsonArray.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("ContactModelImpl", "up:"+result);
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        presenter.upContactDateSuccess();
                        break;
                    default:
                        presenter.upContactDateFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.upContactDateFails(ex.getMessage());
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