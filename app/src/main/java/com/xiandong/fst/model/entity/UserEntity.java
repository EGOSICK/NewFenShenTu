package com.xiandong.fst.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * 用户数据库表
 *
 * @Check check约束
 * @Column 列名
 * @Finder 一对多、多对一、多对多关系(见sample的Parent、Child中的使用)
 * @Foreign 外键
 * @Id 主键，当为int类型时，默认自增。 非自增时，需要设置id的值
 * @NoAutoIncrement 不自增
 * @NotNull 不为空
 * @Table 表名
 * @Transient 不写入数据库表结构
 * @Unique 唯一约束
 */

@Table(name = "user")
public class UserEntity {

    //  登录信息
    @Column(name = "isUserLogIn")
    private boolean isUserLogIn;
    //  用户信息
    @Column(name = "userId" ,isId = true,autoGen = false)
    private String userId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "userPassword")
    private String userPassword;
    @Column(name = "userPhone")
    private String userPhone;
    @Column(name = "userImg")
    private String userImg;
    @Column(name = "userPosition")
    private String userPosition;
    @Column(name = "userFlag")
    private String userFlag;
    @Column(name = "userBalance")
    private String userBalance;
    @Column(name = "userJiFen")
    private String userJiFen;
    @Column(name = "userPayPsw")
    private String userPayPsw;
    @Column(name = "userTime")
    private String userTime;
    @Column(name = "userRenZheng")
    private String userRenZheng;
    @Column(name = "userReferer")
    private String userReferer;
    @Column(name = "userRestTime")
    private String userRestTime;
    @Column(name = "userTongXunLu")
    private String userTongXunLu;
    @Column(name = "userTags")
    private String userTags;
    @Column(name = "userCome")
    private String userCome;
    //  微信信息
    @Column(name = "userWXNickName")
    private String userWXNickName;
    @Column(name = "userWXOpenId")
    private String userWXOpenId;
    @Column(name = "userWXSex")
    private String userWXSex;
    @Column(name = "userWXProvince")
    private String userWXProvince;
    @Column(name = "userWXCity")
    private String userWXCity;
    @Column(name = "userWXCountry")
    private String userWXCountry;
    @Column(name = "userWXHeadimgurl")
    private String userWXHeadimgurl;
    @Column(name = "userWXUnionid")
    private String userWXUnionid;
    @Column(name = "userWXPrivilege")
    private List<String> userWXPrivilege;

    public boolean isUserLogIn() {
        return isUserLogIn;
    }

    public void setUserLogIn(boolean userLogIn) {
        isUserLogIn = userLogIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTongXunLu() {
        return userTongXunLu;
    }

    public void setUserTongXunLu(String userTongXunLu) {
        this.userTongXunLu = userTongXunLu;
    }

    public String getUserTags() {
        return userTags;
    }

    public void setUserTags(String userTags) {
        this.userTags = userTags;
    }

    public String getUserCome() {
        return userCome;
    }

    public void setUserCome(String userCome) {
        this.userCome = userCome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserJiFen() {
        return userJiFen;
    }

    public void setUserJiFen(String userJiFen) {
        this.userJiFen = userJiFen;
    }

    public String getUserPayPsw() {
        return userPayPsw;
    }

    public void setUserPayPsw(String userPayPsw) {
        this.userPayPsw = userPayPsw;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getUserRenZheng() {
        return userRenZheng;
    }

    public void setUserRenZheng(String userRenZheng) {
        this.userRenZheng = userRenZheng;
    }

    public String getUserReferer() {
        return userReferer;
    }

    public void setUserReferer(String userReferer) {
        this.userReferer = userReferer;
    }

    public String getUserRestTime() {
        return userRestTime;
    }

    public void setUserRestTime(String userRestTime) {
        this.userRestTime = userRestTime;
    }

    public String getUserWXNickName() {
        return userWXNickName;
    }

    public void setUserWXNickName(String userWXNickName) {
        this.userWXNickName = userWXNickName;
    }

    public String getUserWXOpenId() {
        return userWXOpenId;
    }

    public void setUserWXOpenId(String userWXOpenId) {
        this.userWXOpenId = userWXOpenId;
    }

    public String getUserWXSex() {
        return userWXSex;
    }

    public void setUserWXSex(String userWXSex) {
        this.userWXSex = userWXSex;
    }

    public String getUserWXProvince() {
        return userWXProvince;
    }

    public void setUserWXProvince(String userWXProvince) {
        this.userWXProvince = userWXProvince;
    }

    public String getUserWXCity() {
        return userWXCity;
    }

    public void setUserWXCity(String userWXCity) {
        this.userWXCity = userWXCity;
    }

    public String getUserWXCountry() {
        return userWXCountry;
    }

    public void setUserWXCountry(String userWXCountry) {
        this.userWXCountry = userWXCountry;
    }

    public String getUserWXHeadimgurl() {
        return userWXHeadimgurl;
    }

    public void setUserWXHeadimgurl(String userWXHeadimgurl) {
        this.userWXHeadimgurl = userWXHeadimgurl;
    }

    public String getUserWXUnionid() {
        return userWXUnionid;
    }

    public void setUserWXUnionid(String userWXUnionid) {
        this.userWXUnionid = userWXUnionid;
    }

    public List<String> getUserWXPrivilege() {
        return userWXPrivilege;
    }

    public void setUserWXPrivilege(List<String> userWXPrivilege) {
        this.userWXPrivilege = userWXPrivilege;
    }
}
