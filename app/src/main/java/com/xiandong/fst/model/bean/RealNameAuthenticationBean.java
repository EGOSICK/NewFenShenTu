package com.xiandong.fst.model.bean;

/**
 * Created by dell on 2017/1/17.
 */

public class RealNameAuthenticationBean extends AbsBaseBean{

    /**
     * id : 143
     * uid : 3833
     * realname : 李白
     * cardid : 230184199303102634
     * timeline : 1484640983
     * renzheng : 1
     */

    private InfoEntity info;

    public InfoEntity getInfo() {
        return info;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public static class InfoEntity {
        private String id;
        private String uid;
        private String realname;
        private String cardid;
        private String timeline;
        private String renzheng;

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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getRenzheng() {
            return renzheng;
        }

        public void setRenzheng(String renzheng) {
            this.renzheng = renzheng;
        }
    }
}
