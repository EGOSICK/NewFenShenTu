package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.ContactBean;
import com.xiandong.fst.model.bean.NewFriendsBean;
import com.xiandong.fst.tools.XCircleImgTools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AddFriendsAdapter extends BaseAdapter {
    Context context;
    List<ContactBean> list = new ArrayList<>();
    private RightBtnClick click;
    List<NewFriendsBean.UserEntity> newUserList = new ArrayList<>();

    public interface RightBtnClick {
        void rightBtnClick(int type, String phone);
    }

    public void setRightBtnClick(RightBtnClick click) {
        this.click = click;
    }

    public AddFriendsAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<ContactBean> list) {
        this.list = list;
        newUserList.clear();
        notifyDataSetChanged();
    }

    public void addNewFriendsData(NewFriendsBean friendsBean) {
        this.newUserList = friendsBean.getUser();
        list.clear();
        notifyDataSetChanged();
    }

    private boolean isListNotNull() {
        return list != null && list.size() > 0;
    }

    private boolean isNewFriendsNotNull() {
        return newUserList != null && newUserList.size() > 0;
    }

    @Override
    public int getCount() {
        if (isListNotNull())
            return isListNotNull() ? list.size() : 0;
        if (isNewFriendsNotNull())
            return isNewFriendsNotNull() ? newUserList.size() : 0;
        return 0;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_add_friends, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (isNewFriendsNotNull()) {
            final NewFriendsBean.UserEntity entity = newUserList.get(i);
            holder.contactNameTv.setText(entity.getNicheng());
            holder.contactPhoneTv.setVisibility(View.GONE);
            XCircleImgTools.setCircleImg(holder.contactUserImg, entity.getImg());
            switch (entity.getHaoyou()) {
                case "1":
                    holder.contactUserAlreadyTv.setVisibility(View.VISIBLE);
                    holder.contactUserAlreadyImg.setVisibility(View.GONE);
                    break;
                default:
                    holder.contactUserAlreadyTv.setVisibility(View.GONE);
                    holder.contactUserAlreadyImg.setVisibility(View.VISIBLE);
                    holder.contactUserAlreadyImg.setImageResource(R.mipmap.add_friends_add_img);
                    holder.contactUserAlreadyImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (click != null) {
                                click.rightBtnClick(1, entity.getId());
                            }
                        }
                    });
                    break;
            }
        }
        if (isListNotNull()) {
            final ContactBean bean = list.get(i);
            holder.contactNameTv.setText(bean.getName());
            holder.contactPhoneTv.setText(bean.getPhone());
            XCircleImgTools.setCircleImg(holder.contactUserImg, bean.getImg());
            switch (bean.getStatus()) {
                case "1":
                    switch (bean.getHaoyou()) {
                        case "1":
                            holder.contactUserAlreadyTv.setVisibility(View.VISIBLE);
                            holder.contactUserAlreadyImg.setVisibility(View.GONE);
                            break;
                        default:
                            holder.contactUserAlreadyTv.setVisibility(View.GONE);
                            holder.contactUserAlreadyImg.setVisibility(View.VISIBLE);
                            holder.contactUserAlreadyImg.setImageResource(R.mipmap.add_friends_add_img);
                            holder.contactUserAlreadyImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (click != null) {
                                        click.rightBtnClick(1, bean.getId());
                                    }
                                }
                            });
                            break;
                    }
                    break;
                default:
                    holder.contactUserAlreadyTv.setVisibility(View.GONE);
                    holder.contactUserAlreadyImg.setVisibility(View.VISIBLE);
                    holder.contactUserAlreadyImg.setImageResource(R.mipmap.add_friends_msg_img);
                    holder.contactUserAlreadyImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (click != null) {
                                click.rightBtnClick(2, bean.getPhone());
                            }
                        }
                    });
                    break;
            }
        }
        return view;
    }

    private class ViewHolder {
        TextView contactUserAlreadyTv, contactPhoneTv, contactNameTv;
        ImageView contactUserImg, contactUserAlreadyImg;

        ViewHolder(View view) {
            contactUserAlreadyTv = (TextView) view.findViewById(R.id.contactUserAlreadyTv);
            contactPhoneTv = (TextView) view.findViewById(R.id.contactPhoneTv);
            contactNameTv = (TextView) view.findViewById(R.id.contactNameTv);
            contactUserImg = (ImageView) view.findViewById(R.id.contactUserImg);
            contactUserAlreadyImg = (ImageView) view.findViewById(R.id.contactUserAlreadyImg);
        }
    }
}
