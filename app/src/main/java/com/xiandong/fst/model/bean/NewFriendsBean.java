package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/19.
 */

public class NewFriendsBean extends AbsBaseBean {

    /**
     * id : 1413
     * uid : 1509
     * act : 0
     * nicheng : 比香港还港。
     * position : 45.739109;126.534734
     * img : https://www.fenshentu.cn/Public/Upload/images/14794576811509.jpg?1479457681
     * flag : 1
     * phone : 18646570534
     */

    private List<UserEntity> user;

    public List<UserEntity> getUser() {
        return user;
    }

    public void setUser(List<UserEntity> user) {
        this.user = user;
    }

    public static class UserEntity {
        private String id;
        private String uid;
        private String act;
        private String nicheng;
        private String position;
        private String img;
        private String flag;
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getNicheng() {
            return nicheng;
        }

        public void setNicheng(String nicheng) {
            this.nicheng = nicheng;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
