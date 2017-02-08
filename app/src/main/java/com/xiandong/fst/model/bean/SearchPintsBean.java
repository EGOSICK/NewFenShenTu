package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/22.
 */

public class SearchPintsBean extends AbsBaseBean{


    /**
     * forum : [{"id":"392","position":"45.738351;126.534085","content":"哈哈","pid":"392","nicheng":"Sahar ღ","img":"https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153"},{"id":"400","position":"45.738351;126.534085","content":"哈哈 回复","pid":"392","nicheng":"Sahar ღ","img":"https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153"},{"id":"405","position":"45.738351;126.534085","content":"哈哈\n","pid":"392","nicheng":"Sahar ღ","img":"https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153"},{"id":"442","position":"45.739088;126.534609","content":"哈哈哈","pid":"442","nicheng":"qwer","img":"http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691"},{"id":"433","position":"45.738351;126.534085","content":"哈哈","pid":"392","nicheng":"Sahar ღ","img":"https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153"},{"id":"438","position":"45.738351;126.534085","content":"哈哈","pid":"392","nicheng":"Sahar ღ","img":"https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153"}]
     * dis : 5000
     */

    private String dis;
    /**
     * id : 392
     * position : 45.738351;126.534085
     * content : 哈哈
     * pid : 392
     * nicheng : Sahar ღ
     * img : https://www.fenshentu.cn/Public/Upload/images/1480468153409.jpg?1480468153
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
        private String pid;
        private String nicheng;
        private String img;

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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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
