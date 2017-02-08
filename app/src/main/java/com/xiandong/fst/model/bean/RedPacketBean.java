package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/21.
 */

public class RedPacketBean extends AbsBaseBean{


    /**
     * id : 88
     * position : 39.065051;121.804095
     * user : fenshentu
     * totalfee : 5.00
     */

    private List<RedbagEntity> redbag;

    public List<RedbagEntity> getRedbag() {
        return redbag;
    }

    public void setRedbag(List<RedbagEntity> redbag) {
        this.redbag = redbag;
    }

    public static class RedbagEntity {
        private String id;
        private String position;
        private String user;
        private String totalfee;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(String totalfee) {
            this.totalfee = totalfee;
        }
    }
}
