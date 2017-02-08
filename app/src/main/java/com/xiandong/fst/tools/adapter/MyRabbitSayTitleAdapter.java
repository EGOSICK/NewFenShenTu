package com.xiandong.fst.tools.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiandong.fst.view.fragment.AddFriendsFragment;
import com.xiandong.fst.view.fragment.MyRabbitSayFragment;

/**
 * Created by dell on 2017/1/19.
 */

public class MyRabbitSayTitleAdapter extends FragmentPagerAdapter {
    String[] title = {"我的动态", "我的发表"};
    public MyRabbitSayTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MyRabbitSayFragment.getInstance(position);
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
