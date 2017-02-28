package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/2/15.
 */

public class MessageBean extends AbsBaseBean {


    private List<NoticeEntity> notice;

    public List<NoticeEntity> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeEntity> notice) {
        this.notice = notice;
    }

    public static class NoticeEntity {
        /**
         * id : 2
         * title : 系统消息
         * content : 系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息系统消息
         * img : ["http://www.fenshentu.com/Public/Upload/images2017-02-14/58a25a4022002.jpg"]
         * timeline : 1487034944
         * type : 1
         * url : http://www.fenshentu.com/wxarticle.php/Index/notice/id/2
         */

        private String id;
        private String title;
        private String content;
        private String timeline;
        private String type;
        private String url;
        private List<String> img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
