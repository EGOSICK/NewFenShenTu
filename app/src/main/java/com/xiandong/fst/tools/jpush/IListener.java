package com.xiandong.fst.tools.jpush;

/**
 * 发送广播接口
 */
public interface IListener<T> {
    void notifyAllActivity(T t);

}
