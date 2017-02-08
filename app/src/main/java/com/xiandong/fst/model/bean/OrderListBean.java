package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/9.
 */

public class OrderListBean extends AbsBaseBean{

    /**
     * id : 4623
     * position : 45.804697;126.535477
     * pcontent : 黑龙江省哈尔滨市松北区天翔街
     * price : 4.25
     * time : 3
     * timeline : 1484110171
     * uid : 3775
     * info : 一束花➕一个加湿器，12日晚送至凯宾斯基酒店
     * title :
     * phone : 18656947101
     * act : 2
     * img : https://www.fenshentu.cn/Public/Home/images/default.png
     * nicheng : 18656947101
     * distance : 7298
     */

    private List<OrderEntity> order;

    public List<OrderEntity> getOrder() {
        return order;
    }

    public void setOrder(List<OrderEntity> order) {
        this.order = order;
    }

    public static class OrderEntity {
        private String id;
        private String position;
        private String pcontent;
        private String price;
        private String time;
        private String timeline;
        private String uid;
        private String info;
        private String title;
        private String phone;
        private String act;
        private String img;
        private String nicheng;
        private int distance;

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

        public String getPcontent() {
            return pcontent;
        }

        public void setPcontent(String pcontent) {
            this.pcontent = pcontent;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
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

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}
