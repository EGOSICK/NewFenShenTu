package com.xiandong.fst.tools.BaiDuTools;

import com.baidu.mapapi.map.Marker;

import java.util.HashMap;

/**
 * Created by dell on 2017/1/16.
 */

public class MarkMapTools {
    public static HashMap<String, String> friends = new HashMap<>();

    public static boolean isHaveFriend(String friendId) {
        return friends.containsKey(friendId);
    }

    private static HashMap<String, Marker> redPacket = new HashMap<>();

    public static HashMap<String, Marker> getRedPacket() {
        if (redPacket == null)
            redPacket = new HashMap<>();
        return redPacket;
    }

    public static boolean isHavaRedPcaket(String pid) {
        return redPacket.containsKey(pid);
    }

    public static String setTitle(String type, String... other) {
        StringBuffer sb = new StringBuffer();
        sb.append(type);
        for (int i = 0; i < other.length; i++) {
            sb.append(","+other[i]);
        }
        return sb.toString();
    }

    private static HashMap<String ,Marker> forum = new HashMap<>();

    public static HashMap<String ,Marker> getForum(){
        if (forum == null)
            forum = new HashMap<>();
        return forum;
    }


}
