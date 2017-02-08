package com.xiandong.fst.model.bean;

import java.util.List;

/**
 * Created by dell on 2017/1/21.
 */

public class CredibilityIntegralBean {
    /**
     * star : 5
     * timeline : 1477448896
     * oid : 2811
     * jifen : +5
     */

    private List<JfEntity> jf;

    public List<JfEntity> getJf() {
        return jf;
    }

    public void setJf(List<JfEntity> jf) {
        this.jf = jf;
    }

    public static class JfEntity {
        private String star;
        private String timeline;
        private String oid;
        private String jifen;

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }
    }
}
