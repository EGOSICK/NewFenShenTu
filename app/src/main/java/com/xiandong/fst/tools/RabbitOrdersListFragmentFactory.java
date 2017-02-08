package com.xiandong.fst.tools;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.xiandong.fst.view.fragment.RabbitOrdersListFragment;
import com.xiandong.fst.view.fragment.RabbitSayFragment;


/**
 * 接单Fragment管理工厂
 */

public class RabbitOrdersListFragmentFactory {
    private static SparseArray<Fragment> fragmentMap = new SparseArray<>();

    public static Fragment creatFragment(int position) {
        Fragment f = fragmentMap.get(position);
        if (f == null) {
            f = RabbitOrdersListFragment.getInstance(position);
            fragmentMap.put(position, f);
        }
        return f;
    }

}
