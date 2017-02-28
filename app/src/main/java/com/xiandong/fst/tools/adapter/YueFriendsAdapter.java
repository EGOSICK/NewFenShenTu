package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/1/24.
 */
public class YueFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FriendsBean.FriendEntity> list;
    private itemClickListener listener;
    Map<Integer, Boolean> map = new HashMap<>();

    public interface itemClickListener {
        void clickListener(List<String> ids);
    }

    public void setItemClick(itemClickListener listener) {
        this.listener = listener;
    }

    public YueFriendsAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<FriendsBean.FriendEntity> list) {
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            map.put(i, false);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_meet_add_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FriendsViewHolder viewHolder = (FriendsViewHolder) holder;
        if (list != null && list.size() > 0) {

            viewHolder.check.setVisibility(View.VISIBLE);
            viewHolder.itemMeetAddFriendNameTv.setSelected(true);
            viewHolder.itemMeetAddFriendNameTv.setText(list.get(position).getNicheng());
            XCircleImgTools.setCircleImg(viewHolder.itemMeetAddFriendImg, list.get(position).getImg());
            viewHolder.check.setChecked(map.get(position));
            viewHolder.itemMeetAddFriendView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (map.get(position)) {
                        map.put(position, false);
                    } else {
                        map.put(position, true);
                    }
                    viewHolder.check.setChecked(map.get(position));
                    if (listener != null) {
                        List<String> ids = new ArrayList<String>();
                        for (int i = 0; i < map.size(); i++) {
                            if (map.get(i)) {
                                ids.add(list.get(i).getUid());
                            }
                        }
                        listener.clickListener(ids);
                    }
                }
            });


//            final FriendsBean.FriendEntity ff = list.get(position);
//            viewHolder.itemFriendYueTv.setSelected(true);
//            viewHolder.itemFriendYueTv.setText(StringUtil.isEmpty(ff.getBz()) ? ff.getNicheng() : ff.getBz());
//            XCircleImgTools.setCircleImg(viewHolder.itemFriendYueImg, ff.getImg());
//            viewHolder.itemFriendYueDianView.setBackgroundResource(R.color.appGray);
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        listener.clickListener(ff.getId());
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    private class FriendsViewHolder extends RecyclerView.ViewHolder {
        ImageView itemMeetAddFriendImg;
        TextView itemMeetAddFriendNameTv;
        CheckBox check;
        View itemMeetAddFriendView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            itemMeetAddFriendImg = (ImageView) itemView.findViewById(R.id.itemMeetAddFriendImg);
            itemMeetAddFriendNameTv = (TextView) itemView.findViewById(R.id.itemMeetAddFriendNameTv);
            check = (CheckBox) itemView.findViewById(R.id.check);
            itemMeetAddFriendView = itemView.findViewById(R.id.itemMeetAddFriendView);
        }
    }
}
