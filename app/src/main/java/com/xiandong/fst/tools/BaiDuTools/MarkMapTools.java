package com.xiandong.fst.tools.BaiDuTools;

import com.baidu.mapapi.map.Marker;

import java.util.HashMap;

/**
 * Created by dell on 2017/1/16.
 */

public class MarkMapTools {

    public static boolean isNestPager = false;
    public static boolean isBillingPager = false;
    public static boolean isOrderPager = false;
    public static boolean isSayPager = false;
    public static boolean isMePager = false;

    public static HashMap<String, Marker> friends = new HashMap<>();

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
            sb.append("," + other[i]);
        }
        return sb.toString();
    }

    private static HashMap<String, Marker> forum = new HashMap<>();

    public static HashMap<String, Marker> getForum() {
        if (forum == null)
            forum = new HashMap<>();
        return forum;
    }
    public static boolean isHavaForm(String pid) {
        return forum.containsKey(pid);
    }

    private static HashMap<String, Marker> orders = new HashMap<>();

    public static HashMap<String, Marker> getOrders() {
        if (orders == null)
            orders = new HashMap<>();
        return orders;
    }
    public static boolean isHaveOrder(String id){
        return orders.containsKey(id);
    }

    public static void choosePager(boolean isNP, boolean isBP
            , boolean isOP, boolean isSP, boolean isMP) {
        isNestPager = isNP;
        isBillingPager = isBP;
        isOrderPager = isOP;
        isSayPager = isSP;
        isMePager = isMP;
    }

}
