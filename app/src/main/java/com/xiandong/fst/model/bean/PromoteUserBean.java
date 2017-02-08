package com.xiandong.fst.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dell on 2017/1/18.
 */

public class PromoteUserBean implements Parcelable {

    private String id;
    private String nicheng;
    private String phone;
    private String img;
    private String position;
    private int childnum;
    private int children;


    private List<ChildEntity> child;

    protected PromoteUserBean(Parcel in) {
        id = in.readString();
        nicheng = in.readString();
        phone = in.readString();
        img = in.readString();
        position = in.readString();
        childnum = in.readInt();
        children = in.readInt();
    }

    public static final Creator<PromoteUserBean> CREATOR = new Creator<PromoteUserBean>() {
        @Override
        public PromoteUserBean createFromParcel(Parcel in) {
            return new PromoteUserBean(in);
        }

        @Override
        public PromoteUserBean[] newArray(int size) {
            return new PromoteUserBean[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setNicheng(String nicheng) {
        this.nicheng = nicheng;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setChildnum(int childnum) {
        this.childnum = childnum;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setChild(List<ChildEntity> child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public String getNicheng() {
        return nicheng;
    }

    public String getPhone() {
        return phone;
    }

    public String getImg() {
        return img;
    }

    public String getPosition() {
        return position;
    }

    public int getChildnum() {
        return childnum;
    }

    public int getChildren() {
        return children;
    }

    public List<ChildEntity> getChild() {
        return child;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nicheng);
        parcel.writeString(phone);
        parcel.writeString(img);
        parcel.writeString(position);
        parcel.writeInt(childnum);
        parcel.writeInt(children);
    }

    public static class ChildEntity {
        private String id;
        private String nicheng;
        private String phone;
        private String img;
        private String position;
        private String dl;

        public void setId(String id) {
            this.id = id;
        }

        public void setNicheng(String nicheng) {
            this.nicheng = nicheng;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setDl(String dl) {
            this.dl = dl;
        }

        public String getId() {
            return id;
        }

        public String getNicheng() {
            return nicheng;
        }

        public String getPhone() {
            return phone;
        }

        public String getImg() {
            return img;
        }

        public String getPosition() {
            return position;
        }

        public String getDl() {
            return dl;
        }
    }
}
