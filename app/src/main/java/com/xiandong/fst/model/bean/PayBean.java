package com.xiandong.fst.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2017/1/10.
 */

public class PayBean implements Parcelable{
    private String uid;
    private String position;
    private String orderId;
    private String money;
    private String title;
    private String address;
    private String phone;
    private String detail;
    private String time;
    private int type;

    public PayBean(Parcel in) {
        money = in.readString();
        title = in.readString();
        address = in.readString();
        phone = in.readString();
        detail = in.readString();
        time = in.readString();
        type = in.readInt();
        position = in.readString();
        uid = in.readString();
        orderId = in.readString();
    }

    public static final Creator<PayBean> CREATOR = new Creator<PayBean>() {
        @Override
        public PayBean createFromParcel(Parcel in) {
            return new PayBean(in);
        }

        @Override
        public PayBean[] newArray(int size) {
            return new PayBean[size];
        }
    };

    public PayBean() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(money);
        parcel.writeString(title);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeString(detail);
        parcel.writeString(time);
        parcel.writeInt(type);
        parcel.writeString(position);
        parcel.writeString(uid);
        parcel.writeString(orderId);
    }
}
