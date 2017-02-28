package com.xiandong.fst.tools.chat;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dell on 2017/2/27.
 */
public class GetMessageManager {

    /**
     * 单例模式
     */
    public static GetMessageManager listenerManager;

    /**
     * 注册的接口集合，发送广播的时候都能收到
     */
    private List<GetMessageInterface> iListenerList = new CopyOnWriteArrayList<GetMessageInterface>();

    /**
     * 获得单例对象
     */
    public static GetMessageManager getInstance() {
        if (listenerManager == null) {
            listenerManager = new GetMessageManager();
        }
        return listenerManager;
    }

    /**
     * 注册监听
     */
    public void registerListener(GetMessageInterface iListener) {
        iListenerList.add(iListener);
    }

    /**
     * 注销监听
     */
    public void unRegisterListener(GetMessageInterface iListener) {
        if (iListenerList.contains(iListener)) {
            iListenerList.remove(iListener);
        }
    }

    /**
     * 收到单聊
     */
    public void getSingleMessage(String friendId) {
        for (GetMessageInterface listener : iListenerList) {
            if (listener != null)
                listener.getSingleMessage(friendId);
        }
    }

    /**
     * 收到群聊
     */
    public void getGroupMessage(String groupId) {
        for (GetMessageInterface listener : iListenerList) {
            if (listener != null)
                listener.getGroupMessage(groupId);
        }
    }
}
