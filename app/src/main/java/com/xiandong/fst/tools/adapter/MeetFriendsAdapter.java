package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MeetBean;
import com.xiandong.fst.tools.XCircleImgTools;

import java.util.List;

/**
 * Created by dell on 2017/2/8.
 */
public class MeetFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MeetBean.MeetEntity.ChildEntity> list;
    OnAddItemClickListener listener;

    public interface OnAddItemClickListener {
        void clickListener();
    }

    public void setOnAddItemClickListener(OnAddItemClickListener listener) {
        this.listener = listener;
    }

    public MeetFriendsAdapter(Context context) {
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
        return new MeetViewHolder(LayoutInflater.from(context).inflate(R.layout.item_meet_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MeetViewHolder viewHolder = (MeetViewHolder) holder;
        if (isListNotNull()) {
            if (position == list.size()) {
                viewHolder.itemMeetFriendNameTv.setText("");
                viewHolder.itemMeetFriendImg.setImageResource(R.mipmap.meet_add_friend_icon);
                viewHolder.itemMeetFriendImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.clickListener();
                        }
                    }
                });
            } else {
                MeetBean.MeetEntity.ChildEntity f = list.get(position);
                viewHolder.itemMeetFriendNameTv.setSelected(true);
                viewHolder.itemMeetFriendNameTv.setText(f.getNicheng());
                XCircleImgTools.setCircleImg(viewHolder.itemMeetFriendImg, f.getImg());
            }
        }
    }

    @Override
    public int getItemCount() {
        return isListNotNull() ? list.size() + 1 : 0;
    }

    private class MeetViewHolder extends RecyclerView.ViewHolder {
        ImageView itemMeetFriendImg;
        TextView itemMeetFriendNameTv;

        public MeetViewHolder(View itemView) {
            super(itemView);
            itemMeetFriendImg = (ImageView) itemView.findViewById(R.id.itemMeetFriendImg);
            itemMeetFriendNameTv = (TextView) itemView.findViewById(R.id.itemMeetFriendNameTv);
        }
    }
}
