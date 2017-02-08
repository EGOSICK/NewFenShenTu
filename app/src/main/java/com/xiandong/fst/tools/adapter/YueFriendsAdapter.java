package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.StringUtil;

import java.util.List;

/**
 * Created by dell on 2017/1/24.
 */
public class YueFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FriendsBean.FriendEntity> list;
    private itemClickListener listener;

    public interface itemClickListener {
        void clickListener(String id);
    }

    public void setItemClick(itemClickListener listener) {
        this.listener = listener;
    }

    public YueFriendsAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<FriendsBean.FriendEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_yue, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FriendsViewHolder viewHolder = (FriendsViewHolder) holder;
        if (list != null && list.size() > 0) {
            final FriendsBean.FriendEntity ff = list.get(position);
            viewHolder.itemFriendYueTv.setSelected(true);
            viewHolder.itemFriendYueTv.setText(StringUtil.isEmpty(ff.getBz()) ? ff.getNicheng() : ff.getBz());
            XCircleImgTools.setCircleImg(viewHolder.itemFriendYueImg, ff.getImg());
            viewHolder.itemFriendYueDianView.setBackgroundResource(R.color.appGray);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.clickListener(ff.getId());
                        viewHolder.itemFriendYueDianView.setBackgroundResource(R.color.appBlue);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    private class FriendsViewHolder extends RecyclerView.ViewHolder {
        ImageView itemFriendYueImg;
        TextView itemFriendYueTv;
        View itemFriendYueDianView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            itemFriendYueImg = (ImageView) itemView.findViewById(R.id.itemFriendYueImg);
            itemFriendYueTv = (TextView) itemView.findViewById(R.id.itemFriendYueTv);
            itemFriendYueDianView = itemView.findViewById(R.id.itemFriendYueDianView);
        }
    }
}
