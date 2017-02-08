package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MyRabbitSayBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.TimeUtil;

import java.util.List;

/**
 * Created by dell on 2017/1/24.
 */
public class MyRabbitSayAdapter extends BaseAdapter {
    private Context context;
    private List<MyRabbitSayBean.ForumEntity> list;
    private int position;

    public MyRabbitSayAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
    }

    public void addData(List<MyRabbitSayBean.ForumEntity> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_my_rabbit_say, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (isListNotNull()) {
            MyRabbitSayBean.ForumEntity entity = list.get(i);

            XCircleImgTools.setCircleImg(holder.itemForumImg , entity.getImg());
            holder.itemForumNameTv.setText(entity.getNicheng());
            holder.itemForumContentTv.setText(entity.getContent());
            holder.itemForumTimeTv.setText(TimeUtil.convertTimeToFormat(Long.valueOf(entity.getTimeline())));
            switch (position){
                case 0:
                    holder.itemForumHuiFuView.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.itemForumHuiFuView.setVisibility(View.VISIBLE);
                    holder.itemForumHFTv.setText(entity.getCount());
                    break;
            }
        }
        return view;
    }

    private class ViewHolder {
        ImageView itemForumImg;
        TextView itemForumNameTv, itemForumContentTv, itemForumTimeTv,itemForumHFTv;
        View itemForumHuiFuView;

        ViewHolder(View view) {
            itemForumImg = (ImageView) view.findViewById(R.id.itemForumImg);
            itemForumNameTv = (TextView) view.findViewById(R.id.itemForumNameTv);
            itemForumContentTv = (TextView) view.findViewById(R.id.itemForumContentTv);
            itemForumTimeTv = (TextView) view.findViewById(R.id.itemForumTimeTv);
            itemForumHuiFuView = view.findViewById(R.id.itemForumHuiFuView);
            itemForumHFTv = (TextView) view.findViewById(R.id.itemForumHFTv);
        }
    }
}
