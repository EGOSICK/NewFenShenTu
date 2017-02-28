package com.xiandong.fst.tools.jpush;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JPushListenerManager<T> {
    /**
     * 单例模式
     */
    public static JPushListenerManager listenerManager;

    /**
     * 注册的接口集合，发送广播的时候都能收到
     */
    private List<IListener> iListenerList = new CopyOnWriteArrayList<IListener>();

    /**
     * 获得单例对象
     */
    public static JPushListenerManager getInstance() {
        if (listenerManager == null) {
            listenerManager = new JPushListenerManager();
        }
        return listenerManager;
    }

    /**
     * 注册监听
     */
    public void registerListtener(IListener iListener) {
        iListenerList.add(iListener);
    }

    /**
     * 注销监听
     */
    public void unRegisterListener(IListener iListener) {
        if (iListenerList.contains(iListener)) {
            iListenerList.remove(iListener);
        }
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(T t) {
        for (IListener iListener : iListenerList) {
            iListener.notifyAllActivity(t);
        }
    }

    public void changePager(int flag ,String id){
        for (IListener iListener : iListenerList) {
            iListener.changePager(flag ,id);
        }
    }

}
