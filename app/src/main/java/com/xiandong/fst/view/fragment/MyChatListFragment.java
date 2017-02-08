package com.xiandong.fst.view.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.mapapi.map.MarkerOptions;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.presenter.FriendsPresenterImpl;
import com.xiandong.fst.tools.adapter.MyChatListAdapter;
import com.xiandong.fst.view.FriendsView;
import com.xiandong.fst.view.activity.MyChatActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/25.
 */
@ContentView(R.layout.fragment_my_chat_list)
public class MyChatListFragment extends AbsBaseFragment implements FriendsView {
    @ViewInject(R.id.myChatLv)
    ListView myChatLv;
    MyChatListAdapter adapter;
    FriendsPresenterImpl friendsPresenter;
    MyChatActivity chatActivity;
    @Override
    protected void initialize() {
        friendsPresenter = new FriendsPresenterImpl(this);
        adapter = new MyChatListAdapter(getContext());
        myChatLv.setAdapter(adapter);
        friendsPresenter.getFriends();
        chatActivity = (MyChatActivity) getActivity();
        myChatLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectPosition(i);
                adapter.notifyDataSetChanged();
                chatActivity.replaceChat(adapter.getSelectId(),adapter.getSelectTitle());
            }
        });
    }

    @Override
    public void getFriendsFails(String msg) {}

    @Override
    public void getFriendsSuccess(FriendsBean friendsBean) {
        adapter.addData(friendsBean.getFriend());
        chatActivity.replaceChat(adapter.getSelectId(),adapter.getSelectTitle());
    }

    @Override
    public void friendsImgSuccess(MarkerOptions option) {}

    @Override
    public void friendsImgFails(String err) {}
}