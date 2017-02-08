package com.xiandong.fst.tools.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiandong.fst.view.fragment.AddFriendsFragment;

/**
 * Created by dell on 2017/1/19.
 */

public class AddFriendsTitleAdapter extends FragmentPagerAdapter {
    String[] title = {"通讯录", "新好友"};
    public AddFriendsTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return AddFriendsFragment.getInstance(position);
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
