package com.xiandong.fst.tools.jpush;

/**
 * Created by dell on 2016/8/17.
 */
public class NotifaceTag {
    int tag;
    boolean isNoti;
    String id ;

    public NotifaceTag(int tag, boolean isNoti) {
        this.tag = tag;
        this.isNoti = isNoti;
    }

    public NotifaceTag(int tag, boolean isNoti, String id) {
        this.tag = tag;
        this.isNoti = isNoti;
        this.id = id;
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

    public boolean isNoti() {
        return isNoti;
    }

    public void setIsNoti(boolean isNoti) {
        this.isNoti = isNoti;
    }
}
