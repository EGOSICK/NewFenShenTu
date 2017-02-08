package com.xiandong.fst.tools.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.xiandong.fst.tools.RabbitOrdersListFragmentFactory;

/**
 * 列表 标题页面适配器
 */

public class RabbitOrdersListAdapter extends FragmentPagerAdapter {
    private String[] title = {"距离最近", "金额最高"};

    public RabbitOrdersListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return RabbitOrdersListFragmentFactory.creatFragment(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
