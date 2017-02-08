package com.xiandong.fst.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dell on 2017/1/16.
 */

public class FriendsBean extends AbsBaseBean implements Parcelable {

    /**
     * uid : 202
     * phone : 18644077559
     * nicheng : 18644077559
     * position : 45.738988;126.534573
     * img : https://www.fenshentu.cn/Public/Upload/images/1471584827202.jpg?1471584827
     * flag : 1
     */

    private UserEntity user;
    /**
     * uid : 236
     * nicheng : 显东科技
     * img : https://www.fenshentu.cn/Public/Upload/images/1473058272236.jpg?1473058272
     * flag : 1
     * phone : 15104555842
     * jifen : 0
     * renzheng : 1
     * position : 45.738994;126.534681
     * id : 1105
     * act : 1
     * user_id : 236
     * bz :
     * showposition : 0
     * turnon : 0
     */

    private List<FriendEntity> friend;
    /**
     * id : 489
     * uid : 202
     * user_id : 202,236
     * pcontent : 123
     * time :
     * timeline : 1484538461
     * content : 123
     * position : 45.737767;126.535116
     * uimg : https://www.fenshentu.cn/Public/Upload/images/1471584827202.jpg?1471584827
     * unicheng : 18644077559
     */

    private List<MeetEntity> meet;

    protected FriendsBean(Parcel in) {
    }

    public static final Creator<FriendsBean> CREATOR = new Creator<FriendsBean>() {
        @Override
        public FriendsBean createFromParcel(Parcel in) {
            return new FriendsBean(in);
        }

        @Override
        public FriendsBean[] newArray(int size) {
            return new FriendsBean[size];
        }
    };

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<FriendEntity> getFriend() {
        return friend;
    }

    public void setFriend(List<FriendEntity> friend) {
        this.friend = friend;
    }

    public List<MeetEntity> getMeet() {
        return meet;
    }

    public void setMeet(List<MeetEntity> meet) {
        this.meet = meet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class UserEntity {
        private String uid;
        private String phone;
        private String nicheng;
        private String position;
        private String img;
        private String flag;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNicheng() {
            return nicheng;
        }

        public void setNicheng(String nicheng) {
            this.nicheng = nicheng;
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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

    public static class FriendEntity implements Parcelable {
        private String uid;
        private String nicheng;
        private String img;
        private String flag;
        private String phone;
        private String jifen;
        private String renzheng;
        private String position;
        private String id;
        private String act;
        private String user_id;
        private String bz;
        private String showposition;
        private String turnon;

        protected FriendEntity(Parcel in) {
            uid = in.readString();
            nicheng = in.readString();
            img = in.readString();
            flag = in.readString();
            phone = in.readString();
            jifen = in.readString();
            renzheng = in.readString();
            position = in.readString();
            id = in.readString();
            act = in.readString();
            user_id = in.readString();
            bz = in.readString();
            showposition = in.readString();
            turnon = in.readString();
        }

        public static final Creator<FriendEntity> CREATOR = new Creator<FriendEntity>() {
            @Override
            public FriendEntity createFromParcel(Parcel in) {
                return new FriendEntity(in);
            }

            @Override
            public FriendEntity[] newArray(int size) {
                return new FriendEntity[size];
            }
        };

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getRenzheng() {
            return renzheng;
        }

        public void setRenzheng(String renzheng) {
            this.renzheng = renzheng;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getShowposition() {
            return showposition;
        }

        public void setShowposition(String showposition) {
            this.showposition = showposition;
        }

        public String getTurnon() {
            return turnon;
        }

        public void setTurnon(String turnon) {
            this.turnon = turnon;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(uid);
            parcel.writeString(nicheng);
            parcel.writeString(img);
            parcel.writeString(flag);
            parcel.writeString(phone);
            parcel.writeString(jifen);
            parcel.writeString(renzheng);
            parcel.writeString(position);
            parcel.writeString(id);
            parcel.writeString(act);
            parcel.writeString(user_id);
            parcel.writeString(bz);
            parcel.writeString(showposition);
            parcel.writeString(turnon);
        }
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
        private String uimg;
        private String unicheng;

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
    }
}
