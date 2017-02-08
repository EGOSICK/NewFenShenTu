package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.exceptions.HyphenateException;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.fragment.MyChatListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/2/6.
 */
public class MyChatListAdapter extends BaseAdapter {
    Context context;
    int selectPosition = 0;
    List<FriendsBean.FriendEntity> list;

    public MyChatListAdapter(Context context) {
        this.context = context;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }

    public void addData(List<FriendsBean.FriendEntity> list ){
        this.list = list;
        notifyDataSetChanged();
    }

    public String getSelectId() {
        return list.get(selectPosition).getUid();
    }

    public String getSelectTitle() {
        return StringUtil.isEmpty(list.get(selectPosition).getBz()) ?
                list.get(selectPosition).getNicheng() : list.get(selectPosition).getBz();
    }

    private boolean isMapNotNull() {
        return list != null && list.size() > 0;
    }

    @Override
    public int getCount() {
        return isMapNotNull() ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_my_chat, viewGroup, false);
        ImageView chatListImg = (ImageView) view.findViewById(R.id.chatListImg);
        if (isMapNotNull()) {
            XCircleImgTools.setCircleImg(chatListImg, list.get(i).getImg());
        }
        if (selectPosition == i) {
            view.setBackgroundResource(R.color.white);
        } else {
            view.setBackgroundResource(R.color.appBlue);
        }
        return view;
    }
}
