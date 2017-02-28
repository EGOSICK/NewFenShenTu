package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/2/8.
 */

public class MeetBean extends AbsBaseBean {

    /**
     * id : 494
     * uid : 233
     * user_id : 233,1544
     * pcontent : 群力新区群力大道与丽江路交汇处银泰城
     * time : 14852514000
     * timeline : 1485251445
     * content :
     * position : 45.739404;126.534492
     * dif : [{"uid":"11","img":"http://wx.qlogo.cn/mmopen/5QEQumzhYsI6bJRCy2wEAjI63Sw1dsrLbaNMMibpYVbJkEtfe6HRp8ILdeia3FiayoQYyicibL0ic3ATEoGNGEicrmt6w/0","nicheng":"我的"},{"uid":"789","img":"http://www.fenshentu.com/Public/Upload/images/1486519074789.jpg?1486519074","nicheng":"我"},{"uid":"1509","img":"https://www.fenshentu.cn/Public/Upload/images/14794576811509.jpg?1479457681","nicheng":"比香港还港。"},{"uid":"3665","img":"http://www.fenshentu.com/Public/Upload/images/14850531933665.jpg?1485053193","nicheng":"哈哈哈哈哈"},{"uid":"3902","img":"http://www.fenshentu.com/Public/Upload/images/14851385613902.jpg?1485138561","nicheng":"18644077559"}]
     * child : [{"uid":"233","position":"45.738835;126.534564","img":"http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691","nicheng":"15104555841","flag":"1","showposition":"0"},{"uid":"1544","position":"21.624383;110.274781","img":"https://www.fenshentu.cn/Public/Home/images/default.png","nicheng":"爱的是你","flag":"1","showposition":"0"}]
     */

    private MeetEntity meet;

    public MeetEntity getMeet() {
        return meet;
    }

    public void setMeet(MeetEntity meet) {
        this.meet = meet;
    }

    public static class MeetEntity {
        private String id;
        private String uid;
        private String user_id;
        private String pcontent;
        private String time;
        private String timeline;
        private String content;
        private String position;
        /**
         * uid : 11
         * img : http://wx.qlogo.cn/mmopen/5QEQumzhYsI6bJRCy2wEAjI63Sw1dsrLbaNMMibpYVbJkEtfe6HRp8ILdeia3FiayoQYyicibL0ic3ATEoGNGEicrmt6w/0
         * nicheng : 我的
         */

        private List<DifEntity> dif;
        /**
         * uid : 233
         * position : 45.738835;126.534564
         * img : http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691
         * nicheng : 15104555841
         * flag : 1
         * showposition : 0
         */

        private List<ChildEntity> child;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPcontent() {
            return pcontent;
        }

        public void setPcontent(String pcontent) {
            this.pcontent = pcontent;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public List<DifEntity> getDif() {
            return dif;
        }

        public void setDif(List<DifEntity> dif) {
            this.dif = dif;
        }

        public List<ChildEntity> getChild() {
            return child;
        }

        public void setChild(List<ChildEntity> child) {
            this.child = child;
        }

        public static class DifEntity {
            private String uid;
            private String img;
            private String nicheng;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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
        }

        public static class ChildEntity {
            private String uid;
            private String position;
            private String img;
            private String nicheng;
            private String flag;
            private String showposition;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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

            public String getNicheng() {
                return nicheng;
            }

            public void setNicheng(String nicheng) {
                this.nicheng = nicheng;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getShowposition() {
                return showposition;
            }

            public void setShowposition(String showposition) {
                this.showposition = showposition;
            }
        }
    }
}
