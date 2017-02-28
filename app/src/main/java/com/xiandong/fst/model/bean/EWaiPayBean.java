package com.xiandong.fst.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2017/2/24.
 */
public class EWaiPayBean implements Parcelable {
    int type;
    String id ;
    String money;
    String orderId;

    public EWaiPayBean() {
    }

    protected EWaiPayBean(Parcel in) {
        type = in.readInt();
        id = in.readString();
        money = in.readString();
        orderId = in.readString();
    }

    public static final Creator<EWaiPayBean> CREATOR = new Creator<EWaiPayBean>() {
        @Override
        public EWaiPayBean createFromParcel(Parcel in) {
            return new EWaiPayBean(in);
        }

        @Override
        public EWaiPayBean[] newArray(int size) {
            return new EWaiPayBean[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(id);
        parcel.writeString(money);
        parcel.writeString(orderId);
    }
}
