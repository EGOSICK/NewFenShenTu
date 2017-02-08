package com.xiandong.fst.model.bean;

/**
 * 验证码
 */

public class CodeBean extends AbsBaseBean{

    /**
     * result : 1
     * message : 已发送验证码到您的手机，请注意查收
     * code : 9296
     */

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
