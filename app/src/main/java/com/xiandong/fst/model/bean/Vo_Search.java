package com.xiandong.fst.model.bean;

/**
 * Created by dell on 2017/1/19.
 */

public class Vo_Search {
    private boolean  bflag = false; //true 接口搜索，false内部搜索
    private String sCode="";
    private String sType ="3"; //1code,2phone,3nicheng


    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public Vo_Search(boolean bflag) {
        super();
        this.bflag = bflag;
    }

    public Vo_Search() {
        super();
    }

    public boolean isBflag() {
        return bflag;
    }

    public void setBflag(boolean bflag) {
        this.bflag = bflag;
    }

    @Override
    public String toString() {
        return "Vo_Search [bflag=" + bflag + ", sCode=" + sCode + ", sType="
                + sType + "]";
    }


}
