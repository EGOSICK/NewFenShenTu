package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MarkerOptions;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.presenter.FriendsPresenterImpl;
import com.xiandong.fst.tools.adapter.MyChatListAdapter;
import com.xiandong.fst.tools.chat.GetMessageInterface;
import com.xiandong.fst.tools.chat.GetMessageManager;
import com.xiandong.fst.utils.StringUtil;
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
    String friendId;
    int selectPosition = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyChatActivity) {
            chatActivity = (MyChatActivity) context;
        }
    }

    @Override
    protected void initialize() {
        friendsPresenter = new FriendsPresenterImpl(this);
        adapter = new MyChatListAdapter(getContext());
        myChatLv.setAdapter(adapter);
        friendsPresenter.getFriends();
        friendId = chatActivity.getSelectFriendId();
        adapter.setOnMyItemSelectListener(new MyChatListAdapter.OnMyItemSelectListener() {
            @Override
            public void onMyItemSelect(int position) {
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
                chatActivity.replaceChat(adapter.getSelectId(), adapter.getSelectTitle());
            }
        });

        GetMessageManager.getInstance().registerListener(messageInterface);
    }

    @Override
    public void getFriendsFails(String msg) {
    }

    @Override
    public void getFriendsSuccess(FriendsBean friendsBean) {
        adapter.addData(friendsBean.getFriend());

        for (FriendsBean.FriendEntity friend : friendsBean.getFriend()) {
            if (StringUtil.isEquals(friend.getUser_id(), friendId)) {
                selectPosition = friendsBean.getFriend().indexOf(friend);
            }
        }
        adapter.setSelectPosition(selectPosition);
        chatActivity.replaceChat(adapter.getSelectId(), adapter.getSelectTitle());

    }

    @Override
    public void friendsImgSuccess(MarkerOptions option) {}

    @Override
    public void friendsImgFails(String err) {}

    @Override
    public BaiduMap getBaiDuMap() {
        return null;
    }

    public GetMessageInterface messageInterface = new GetMessageInterface() {
        @Override
        public void getSingleMessage(String friendId) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        }

        @Override
        public void getGroupMessage(String groupId) {

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        GetMessageManager.getInstance().unRegisterListener(messageInterface);
    }
}