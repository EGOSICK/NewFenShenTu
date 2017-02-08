package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/2/7.
 */

public class RedPacketDetailsBean extends AbsBaseBean {

    /**
     * id : 850
     * totalfee : 1.00
     * totalcount : 1
     * restfee : 0.00
     * restcount : 0
     * timeline : 1486457632
     * pid : 0
     * position : 45.738835;126.534564
     * user : fenshentu
     * payact : 1
     * img : http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691
     * nicheng : 15104555841
     * ufee : 1.00
     * uid : 233
     * children : [{"uid":"233","ufee":"1.00","timeline":"1486457837","nicheng":"15104555841","img":"http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691"}]
     */

    private RedbagEntity redbag;

    public RedbagEntity getRedbag() {
        return redbag;
    }

    public void setRedbag(RedbagEntity redbag) {
        this.redbag = redbag;
    }

    public static class RedbagEntity {
        private String id;
        private String totalfee;
        private String totalcount;
        private String restfee;
        private String restcount;
        private String timeline;
        private String pid;
        private String position;
        private String user;
        private String payact;
        private String img;
        private String nicheng;
        private String ufee;
        private String uid;
        /**
         * uid : 233
         * ufee : 1.00
         * timeline : 1486457837
         * nicheng : 15104555841
         * img : http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691
         */

        private List<ChildrenEntity> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(String totalfee) {
            this.totalfee = totalfee;
        }

        public String getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(String totalcount) {
            this.totalcount = totalcount;
        }

        public String getRestfee() {
            return restfee;
        }

        public void setRestfee(String restfee) {
            this.restfee = restfee;
        }

        public String getRestcount() {
            return restcount;
        }

        public void setRestcount(String restcount) {
            this.restcount = restcount;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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

        public String getPayact() {
            return payact;
        }

        public void setPayact(String payact) {
            this.payact = payact;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNicheng() {
            return nicheng;
        }

        public void setNicheng(String nicheng) {
            this.nicheng = nicheng;
        }

        public String getUfee() {
            return ufee;
        }

        public void setUfee(String ufee) {
            this.ufee = ufee;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public List<ChildrenEntity> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenEntity> children) {
            this.children = children;
        }

        public static class ChildrenEntity {
            private String uid;
            private String ufee;
            private String timeline;
            private String nicheng;
            private String img;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUfee() {
                return ufee;
            }

            public void setUfee(String ufee) {
                this.ufee = ufee;
            }

            public String getTimeline() {
                return timeline;
            }

            public void setTimeline(String timeline) {
                this.timeline = timeline;
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
        }
    }
}
