package com.xiandong.fst.model.bean;

/**
 * Created by dell on 2016/12/20.
 */

public class UserBean extends AbsBaseBean {


    /**
     * id : 3441
     * phone :
     * referer :
     * password :
     * zfpassword :
     * position : 45.677550;126.562778
     * time : 1482974423
     * nicheng : why？
     * img : http://wx.qlogo.cn/mmopen/92G53JIVpzjv3uA8gYgVJY3MdqicHmibCOO1pOm0833UPrTrZYyetMzqqytyuD0daCOqdwruqRFxhRKyHtEILia9ic3lJ8bgIHNL/0
     * accesstoken : o2QhzwvIzy5f8QMkaFl1pk_Dsf4E
     * openid : oRwfEs9F2tNEp6dQ7t1p8Y4D-3aA
     * code : 9741B159-F4C6-49F0-88D0-C1E2F4CC3915
     * flag : 1
     * tongxunlu :
     * usetimes : 0
     * yue : 0.00
     * tags : HDQ3441
     * jifen : 0
     * renzheng : -1
     * referertime :
     * come :
     */

    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public static class UserEntity {
        private String id;
        private String phone;
        private String referer;
        private String password;
        private String zfpassword;
        private String position;
        private String time;
        private String nicheng;
        private String img;
        private String accesstoken;
        private String openid;
        private String code;
        private String flag;
        private String tongxunlu;
        private String usetimes;
        private String yue;
        private String tags;
        private String jifen;
        private String renzheng;
        private String referertime;
        private String come;

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

        public String getReferer() {
            return referer;
        }

        public void setReferer(String referer) {
            this.referer = referer;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getZfpassword() {
            return zfpassword;
        }

        public void setZfpassword(String zfpassword) {
            this.zfpassword = zfpassword;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public String getAccesstoken() {
            return accesstoken;
        }

        public void setAccesstoken(String accesstoken) {
            this.accesstoken = accesstoken;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getTongxunlu() {
            return tongxunlu;
        }

        public void setTongxunlu(String tongxunlu) {
            this.tongxunlu = tongxunlu;
        }

        public String getUsetimes() {
            return usetimes;
        }

        public void setUsetimes(String usetimes) {
            this.usetimes = usetimes;
        }

        public String getYue() {
            return yue;
        }

        public void setYue(String yue) {
            this.yue = yue;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getRenzheng() {
            return renzheng;
        }

        public void setRenzheng(String renzheng) {
            this.renzheng = renzheng;
        }

        public String getReferertime() {
            return referertime;
        }

        public void setReferertime(String referertime) {
            this.referertime = referertime;
        }

        public String getCome() {
            return come;
        }

        public void setCome(String come) {
            this.come = come;
        }
    }
}
