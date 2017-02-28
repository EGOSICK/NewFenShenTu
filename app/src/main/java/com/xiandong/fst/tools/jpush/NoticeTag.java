package com.xiandong.fst.tools.jpush;

/**
 * Created by dell on 2016/8/17.
 */
public class NoticeTag {
    int tag;
    String id;
    String uid;
    String price;

    public NoticeTag() {
    }

    public NoticeTag(int tag) {
        this.tag = tag;
    }

    public NoticeTag(int tag, String id) {
        this.tag = tag;
        this.id = id;
    }

    public NoticeTag(int tag, String id, String uid) {
        this.tag = tag;
        this.id = id;
        this.uid = uid;
    }

    public NoticeTag(int tag, String uid, String id, String price) {
        this.tag = tag;
        this.uid = uid;
        this.id = id;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
