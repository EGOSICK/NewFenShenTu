package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.RedPacketDetailsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.TimeUtil;

import java.util.List;

/**
 * Created by dell on 2017/2/7.
 */
public class RedPacketDetailsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<RedPacketDetailsBean.RedbagEntity.ChildrenEntity> list;

    public RedPacketDetailsAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<RedPacketDetailsBean.RedbagEntity.ChildrenEntity> list) {
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
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_red_packet_details, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (isListNotNull()) {
            RedPacketDetailsBean.RedbagEntity.ChildrenEntity entity = list.get(i);
            XCircleImgTools.setCircleImg(viewHolder.itemRedPacketDetailsImg , entity.getImg());
            viewHolder.itemRedPacketDetailsMoneyTv.setText(entity.getUfee()+"元");
            viewHolder.itemRedPacketDetailsNameTv.setText(entity.getNicheng());
            viewHolder.itemRedPacketDetailsTimeTv.setText(
                    TimeUtil.getFormatTimeFromTimestamp(Long.parseLong(entity.getTimeline()),null));

        }
        return view;
    }

    private class ViewHolder {
        ImageView itemRedPacketDetailsImg;
        TextView itemRedPacketDetailsNameTv, itemRedPacketDetailsTimeTv, itemRedPacketDetailsMoneyTv;

        ViewHolder(View view) {
            itemRedPacketDetailsImg = (ImageView) view.findViewById(R.id.itemRedPacketDetailsImg);
            itemRedPacketDetailsNameTv = (TextView) view.findViewById(R.id.itemRedPacketDetailsNameTv);
            itemRedPacketDetailsTimeTv = (TextView) view.findViewById(R.id.itemRedPacketDetailsTimeTv);
            itemRedPacketDetailsMoneyTv = (TextView) view.findViewById(R.id.itemRedPacketDetailsMoneyTv);
        }
    }
}
