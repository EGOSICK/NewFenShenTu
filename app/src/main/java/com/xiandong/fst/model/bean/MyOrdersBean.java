package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/18.
 */

public class MyOrdersBean extends AbsBaseBean {

    /**
     * id : 4720
     * position : 45.73914092065743;126.53440000000016
     * pcontent : 江北
     * price : 0.85
     * tprice : 1.00
     * time : 1484720280000
     * timeline : 1484709757
     * uid : 233
     * user_id : 0
     * utime :
     * usertime :
     * uact : 0
     * useract : 0
     * phone : 15104555841
     * title :
     * info : 详情电联
     * act : 0
     * type : 1
     * payact : 1
     * orderid : 2c7c069302cd159d
     * extraprice :
     * extrauid :
     * extrauser_id :
     * come : android
     * flag : 0
     * img : http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691
     * nicheng : qwer
     * content :
     * star : 5
     * tags : ["好","很好","非常好"]
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
        private String tprice;
        private String time;
        private String timeline;
        private String uid;
        private String user_id;
        private String utime;
        private String usertime;
        private String uact;
        private String useract;
        private String phone;
        private String title;
        private String info;
        private String act;
        private String type;
        private String payact;
        private String orderid;
        private String extraprice;
        private String extrauid;
        private String extrauser_id;
        private String come;
        private int flag;
        private String img;
        private String nicheng;
        private String content;
        private String star;
        private String pimg;
        private String pnicheng;
        private List<String> tags;

        public String getPimg() {
            return pimg;
        }

        public void setPimg(String pimg) {
            this.pimg = pimg;
        }

        public String getPnicheng() {
            return pnicheng;
        }

        public void setPnicheng(String pnicheng) {
            this.pnicheng = pnicheng;
        }

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

        public String getTprice() {
            return tprice;
        }

        public void setTprice(String tprice) {
            this.tprice = tprice;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }

        public String getUsertime() {
            return usertime;
        }

        public void setUsertime(String usertime) {
            this.usertime = usertime;
        }

        public String getUact() {
            return uact;
        }

        public void setUact(String uact) {
            this.uact = uact;
        }

        public String getUseract() {
            return useract;
        }

        public void setUseract(String useract) {
            this.useract = useract;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPayact() {
            return payact;
        }

        public void setPayact(String payact) {
            this.payact = payact;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getExtraprice() {
            return extraprice;
        }

        public void setExtraprice(String extraprice) {
            this.extraprice = extraprice;
        }

        public String getExtrauid() {
            return extrauid;
        }

        public void setExtrauid(String extrauid) {
            this.extrauid = extrauid;
        }

        public String getExtrauser_id() {
            return extrauser_id;
        }

        public void setExtrauser_id(String extrauser_id) {
            this.extrauser_id = extrauser_id;
        }

        public String getCome() {
            return come;
        }

        public void setCome(String come) {
            this.come = come;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
