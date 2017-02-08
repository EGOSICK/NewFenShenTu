package com.xiandong.fst.model.bean;

/**
 * Created by dell on 2017/1/20.
 */

public class SearchFriendsBean extends AbsBaseBean{

   /**
         * id : 3665
         * phone : 13836464289
         * password : 12345
         * position : 45.738557;126.534301
         * nicheng : 呵呵
         * img :
         * flag : 0
         * haoyou : 0
         */

        private String id;
        private String phone;
        private String password;
        private String position;
        private String nicheng;
        private String img;
        private String flag;
        private int haoyou;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNicheng() {
        return nicheng;
    }

    public void setNicheng(String nicheng) {
        this.nicheng = nicheng;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getHaoyou() {
        return haoyou;
    }

    public void setHaoyou(int haoyou) {
        this.haoyou = haoyou;
    }
}
