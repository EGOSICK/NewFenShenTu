package com.xiandong.fst.model.bean;

/**
 * Created by dell on 2017/1/23.
 */

public class OrderDetailsMsgBean extends AbsBaseBean{

    /**
     * id : 4730
     * position : 45.86092701605404;126.57751212705944
     * pcontent : 松浦镇哈尔滨西庙台子站
     * price : 0.85
     * tprice : 1.00
     * time : 1485150720000
     * timeline : 1485140226
     * uid : 233
     * user_id : 0
     * utime :
     * usertime :
     * uact : 0               发单人 完成状态
     * useract : 0            接单人 完成状态
     * phone : 15104555841
     * title : 帮忙
     * info : 有5个人要杀我。我已经在草丛蹲了10分钟了，在不快点老家要没了
     * act : 0
     * type : 1
     * payact : 1
     * orderid : 4703cdd0af0ff6c7
     * extraprice :     支付完成
     * extrauid :       发单人支付完成状态
     * extrauser_id :   接单人提示金额
     * come : android
     * uimg : http://www.fenshentu.com/Public/Upload/images/1484643691233.jpg?1484643691
     * unicheng : qwer
     * uphone : 15104555841
     * urenzheng : -1
     * userimg :
     * usernicheng :
     * userphone :
     */

    private OrderEntity order;

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
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
        private String uimg;
        private String unicheng;
        private String uphone;
        private String urenzheng;
        private String userimg;
        private String usernicheng;
        private String userphone;

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

        public String getUimg() {
            return uimg;
        }

        public void setUimg(String uimg) {
            this.uimg = uimg;
        }

        public String getUnicheng() {
            return unicheng;
        }

        public void setUnicheng(String unicheng) {
            this.unicheng = unicheng;
        }

        public String getUphone() {
            return uphone;
        }

        public void setUphone(String uphone) {
            this.uphone = uphone;
        }

        public String getUrenzheng() {
            return urenzheng;
        }

        public void setUrenzheng(String urenzheng) {
            this.urenzheng = urenzheng;
        }

        public String getUserimg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public String getUsernicheng() {
            return usernicheng;
        }

        public void setUsernicheng(String usernicheng) {
            this.usernicheng = usernicheng;
        }

        public String getUserphone() {
            return userphone;
        }

        public void setUserphone(String userphone) {
            this.userphone = userphone;
        }
    }
}
