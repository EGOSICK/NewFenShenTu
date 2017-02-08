package com.xiandong.fst.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dell on 2017/1/18.
 */

public class MoneyRecordBean implements Parcelable {

    private String act;
    private List<XFDetails> XFDetails;
    private List<TXDetails> TXDetails;
    private List<YJDetails> YJDetails;

    public MoneyRecordBean(){

    }

    protected MoneyRecordBean(Parcel in) {
        act = in.readString();
    }

    public static final Creator<MoneyRecordBean> CREATOR = new Creator<MoneyRecordBean>() {
        @Override
        public MoneyRecordBean createFromParcel(Parcel in) {
            return new MoneyRecordBean(in);
        }

        @Override
        public MoneyRecordBean[] newArray(int size) {
            return new MoneyRecordBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(act);
    }

    public static class XFDetails implements Parcelable{
        private String xfName;
        private String timeLine;
        private String money;
        private String uid;
        private String user_id;
        private String pid;

        public XFDetails(){

        }

        protected XFDetails(Parcel in) {
            xfName = in.readString();
            timeLine = in.readString();
            money = in.readString();
            uid = in.readString();
            user_id = in.readString();
            pid = in.readString();
        }

        public static final Creator<XFDetails> CREATOR = new Creator<XFDetails>() {
            @Override
            public XFDetails createFromParcel(Parcel in) {
                return new XFDetails(in);
            }

            @Override
            public XFDetails[] newArray(int size) {
                return new XFDetails[size];
            }
        };

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getXfName() {
            return xfName;
        }

        public void setXfName(String xfName) {
            this.xfName = xfName;
        }

        public String getTimeLine() {
            return timeLine;
        }

        public void setTimeLine(String timeLine) {
            this.timeLine = timeLine;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(xfName);
            parcel.writeString(timeLine);
            parcel.writeString(money);
            parcel.writeString(uid);
            parcel.writeString(user_id);
            parcel.writeString(pid);
        }
    }

    public static class TXDetails implements Parcelable{
        private String timeLine;
        private String money;
        private String uid;
        private String user_id;

        public TXDetails(){

        }

        protected TXDetails(Parcel in) {
            timeLine = in.readString();
            money = in.readString();
            uid = in.readString();
            user_id = in.readString();
        }

        public static final Creator<TXDetails> CREATOR = new Creator<TXDetails>() {
            @Override
            public TXDetails createFromParcel(Parcel in) {
                return new TXDetails(in);
            }

            @Override
            public TXDetails[] newArray(int size) {
                return new TXDetails[size];
            }
        };

        public String getTimeLine() {
            return timeLine;
        }

        public void setTimeLine(String timeLine) {
            this.timeLine = timeLine;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(timeLine);
            parcel.writeString(money);
            parcel.writeString(uid);
            parcel.writeString(user_id);
        }
    }

    public static class YJDetails implements Parcelable{
        private String img;
        private String phone;
        private String money;
        private String timeLine;
        private String uid;
        private String user_id;
        public YJDetails (){

        }

        protected YJDetails(Parcel in) {
            img = in.readString();
            phone = in.readString();
            money = in.readString();
            timeLine = in.readString();
            uid = in.readString();
            user_id = in.readString();
        }

        public static final Creator<YJDetails> CREATOR = new Creator<YJDetails>() {
            @Override
            public YJDetails createFromParcel(Parcel in) {
                return new YJDetails(in);
            }

            @Override
            public YJDetails[] newArray(int size) {
                return new YJDetails[size];
            }
        };

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTimeLine() {
            return timeLine;
        }

        public void setTimeLine(String timeLine) {
            this.timeLine = timeLine;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(img);
            parcel.writeString(phone);
            parcel.writeString(money);
            parcel.writeString(timeLine);
            parcel.writeString(uid);
            parcel.writeString(user_id);
        }
    }

    public List<MoneyRecordBean.XFDetails> getXFDetails() {
        return XFDetails;
    }

    public void setXFDetails(List<MoneyRecordBean.XFDetails> XFDetails) {
        this.XFDetails = XFDetails;
    }

    public List<MoneyRecordBean.TXDetails> getTXDetails() {
        return TXDetails;
    }

    public void setTXDetails(List<MoneyRecordBean.TXDetails> TXDetails) {
        this.TXDetails = TXDetails;
    }

    public List<MoneyRecordBean.YJDetails> getYJDetails() {
        return YJDetails;
    }

    public void setYJDetails(List<MoneyRecordBean.YJDetails> YJDetails) {
        this.YJDetails = YJDetails;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
