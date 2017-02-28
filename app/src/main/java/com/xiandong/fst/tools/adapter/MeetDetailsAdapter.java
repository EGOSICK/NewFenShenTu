package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MeetBean;
import com.xiandong.fst.tools.XCircleImgTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/2/8.
 */
public class MeetDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MeetBean.MeetEntity.ChildEntity> list;

    public MeetDetailsAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<MeetBean.MeetEntity.ChildEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private boolean isListNotNull() {
        return list != null && list.size() > 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeetAddViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item_meet_add_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MeetAddViewHolder viewHolder = (MeetAddViewHolder) holder;
        if (isListNotNull()) {
            MeetBean.MeetEntity.ChildEntity item = list.get(position);
            viewHolder.check.setVisibility(View.GONE);
            viewHolder.itemMeetAddFriendNameTv.setSelected(true);
            viewHolder.itemMeetAddFriendNameTv.setText(item.getNicheng());
            XCircleImgTools.setCircleImg(viewHolder.itemMeetAddFriendImg, item.getImg());

        }
    }

    @Override
    public int getItemCount() {
        return isListNotNull() ? list.size() : 0;
    }

    private class MeetAddViewHolder extends RecyclerView.ViewHolder {
        ImageView itemMeetAddFriendImg;
        TextView itemMeetAddFriendNameTv;
        CheckBox check;
        View itemMeetAddFriendView;

        public MeetAddViewHolder(View itemView) {
            super(itemView);
            itemMeetAddFriendImg = (ImageView) itemView.findViewById(R.id.itemMeetAddFriendImg);
            itemMeetAddFriendNameTv = (TextView) itemView.findViewById(R.id.itemMeetAddFriendNameTv);
            check = (CheckBox) itemView.findViewById(R.id.check);
            itemMeetAddFriendView = itemView.findViewById(R.id.itemMeetAddFriendView);
        }
    }
}
