package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.presenter.ContactPresenterImpl;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.StringUtil;

import java.util.List;

/**
 * Created by dell on 2017/1/20.
 */

public class SearchFriendsAdapter extends BaseAdapter {
    private Context context;
    private List<SearchFriendsBean> list;
    private AddFriendsListener listener;

    public interface AddFriendsListener {
        void addFriends(String id);
    }

    public void setListener(AddFriendsListener listener) {
        this.listener = listener;
    }

    public SearchFriendsAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<SearchFriendsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private boolean isListNotNull() {
        return list != null && list.size() > 0;
    }

    @Override
    public int getCount() {
        return isListNotNull() ? list.size() : 0;
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

        if (isListNotNull()) {
            final SearchFriendsBean bean = list.get(i);
            holder.contactPhoneTv.setVisibility(View.GONE);
            if (StringUtil.isEmpty(bean.getNicheng())) {
                holder.contactNameTv.setText(bean.getPhone());
            }
            if (StringUtil.isEmpty(bean.getPhone())) {
                holder.contactNameTv.setText(bean.getNicheng());
            }
            XCircleImgTools.setCircleImg(holder.contactUserImg, bean.getImg());
            switch (bean.getHaoyou()) {
                case 1:
                    holder.contactUserAlreadyImg.setVisibility(View.GONE);
                    holder.contactUserAlreadyTv.setVisibility(View.VISIBLE);
                    break;
                default:
                    holder.contactUserAlreadyImg.setVisibility(View.VISIBLE);
                    holder.contactUserAlreadyImg.setImageResource(R.mipmap.add_friends_add_img);
                    holder.contactUserAlreadyTv.setVisibility(View.GONE);
                    holder.contactUserAlreadyImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listener != null)
                                listener.addFriends(bean.getId());
                        }
                    });
                    break;
            }
        }
        return view;
    }

    private class ViewHolder {
        ImageView contactUserImg, contactUserAlreadyImg;
        TextView contactNameTv, contactPhoneTv, contactUserAlreadyTv;

        ViewHolder(View view) {
            contactUserImg = (ImageView) view.findViewById(R.id.contactUserImg);
            contactNameTv = (TextView) view.findViewById(R.id.contactNameTv);
            contactPhoneTv = (TextView) view.findViewById(R.id.contactPhoneTv);
            contactUserAlreadyImg = (ImageView) view.findViewById(R.id.contactUserAlreadyImg);
            contactUserAlreadyTv = (TextView) view.findViewById(R.id.contactUserAlreadyTv);
        }
    }
}
