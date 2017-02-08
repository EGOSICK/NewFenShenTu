package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.HotPintsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.view.activity.RabbitSayNeiActivity;

import java.util.List;

/**
 * 兔子说 适配器
 */

public class RabbitSayAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<HotPintsBean.ForumEntity> list;
    private Context context;
    private String position;

    public void setPosition(String position){
        this.position = position;
    }

    public RabbitSayAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<HotPintsBean.ForumEntity> list) {
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

    public String getForumId(int i){
        return list.get(i).getId();
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_rabbit_say, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        if (isListNotNull()){
            final HotPintsBean.ForumEntity bean = list.get(i);

            viewHolder.itemRabbitSayTitleTv.setText(bean.getNicheng());
            XCircleImgTools.setCircleImg(viewHolder.itemRabbitSayUserImg , bean.getImg());
            viewHolder.itemRabbitSayContentTv.setSelected(true);
            viewHolder.itemRabbitSayContentTv.setText(bean.getContent());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, RabbitSayNeiActivity.class)
                            .putExtra("id", bean.getId()).putExtra("position",position));
                }
            });
        }

        return view;
    }

    private class ViewHolder {
        TextView itemRabbitSayTitleTv, itemRabbitSayContentTv;
        ImageView itemRabbitSayUserImg;

        private ViewHolder(View view) {
            itemRabbitSayTitleTv = (TextView) view.findViewById(R.id.itemRabbitSayTitleTv);
            itemRabbitSayContentTv = (TextView) view.findViewById(R.id.itemRabbitSayContentTv);
            itemRabbitSayUserImg = (ImageView) view.findViewById(R.id.itemRabbitSayUserImg);
        }
    }
}
