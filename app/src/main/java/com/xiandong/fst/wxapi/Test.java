package com.xiandong.fst.wxapi;


import android.util.Log;

import java.util.HashMap;

/**
 * Created by dell on 2017/1/16.
 */

public class Test {
    public static void main(String args[]){

        HashMap<Integer, String>  map = new HashMap<>();
        map.put(0,"1");
        map.put(1,"1");
        map.put(2,"2");

        for (String s :
                map.values()) {
            System.out.print(s);
        }

    }
}
