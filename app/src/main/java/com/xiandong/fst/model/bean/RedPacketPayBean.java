package com.xiandong.fst.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 红包支付
 */

public class RedPacketPayBean implements Parcelable {
    private int type;
    private String uid;
    private String orderId;
    private String totalFee;
    private String distance;
    private String totalCount;

    public RedPacketPayBean() {

    }

    public RedPacketPayBean(Parcel in) {
        orderId = in.readString();
        uid = in.readString();
        type = in.readInt();
        totalFee = in.readString();
        totalCount = in.readString();
        distance = in.readString();
    }

    public static final Creator<RedPacketPayBean> CREATOR = new Creator<RedPacketPayBean>() {
        @Override
        public RedPacketPayBean createFromParcel(Parcel in) {
            return new RedPacketPayBean(in);
        }

        @Override
        public RedPacketPayBean[] newArray(int size) {
            return new RedPacketPayBean[size];
        }
    };

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderId);
        parcel.writeString(uid);
        parcel.writeInt(type);
        parcel.writeString(totalFee);
        parcel.writeString(totalCount);
        parcel.writeString(distance);
    }
}
