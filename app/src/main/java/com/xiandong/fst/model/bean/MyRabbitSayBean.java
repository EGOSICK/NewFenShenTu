package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/24.
 */

public class MyRabbitSayBean extends AbsBaseBean {

    /**
     * id : 442
     * content : 哈哈哈
     * uid : 233
     * timeline : 1485066394
     * position : 45.739088;126.534609
     * parentid : 0
     * nicheng : qwer
     * img : http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691
     * fid : 442
     * count : 1
     */

    private List<ForumEntity> forum;

    public List<ForumEntity> getForum() {
        return forum;
    }

    public void setForum(List<ForumEntity> forum) {
        this.forum = forum;
    }

    public static class ForumEntity {
        private String id;
        private String content;
        private String uid;
        private String timeline;
        private String position;
        private String parentid;
        private String nicheng;
        private String img;
        private String fid;
        private String count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
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

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
