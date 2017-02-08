package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/20.
 */

public class HotPintsBean extends AbsBaseBean {

    /**
     * forum : [{"id":"392","position":"45.738351;126.534085","content":"哈哈","nicheng":"Sahar ღ","img":"https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153","distance":"65"},{"id":"398","position":"45.738351;126.534085","content":"黄旭黄旭黄旭","nicheng":"黄旭","img":"https://www.fenshentu.cn/Public/Upload/images/14771171921.jpg?1477117192","distance":"65"}]
     * dis : 5000
     */

    private String dis;
    /**
     * id : 392
     * position : 45.738351;126.534085
     * content : 哈哈
     * nicheng : Sahar ღ
     * img : https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153
     * distance : 65
     */

    private List<ForumEntity> forum;

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public List<ForumEntity> getForum() {
        return forum;
    }

    public void setForum(List<ForumEntity> forum) {
        this.forum = forum;
    }

    public static class ForumEntity {
        private String id;
        private String position;
        private String content;
        private String nicheng;
        private String img;
        private String distance;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
