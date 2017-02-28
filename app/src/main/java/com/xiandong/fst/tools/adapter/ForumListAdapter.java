package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.HotPintDetailBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.TimeUtil;

import java.util.List;

/**
 * Created by dell on 2017/1/22.
 */
public class ForumListAdapter extends BaseAdapter {
    private Context context;
    private List<HotPintDetailBean.ForumEntity> list;

    public ForumListAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<HotPintDetailBean.ForumEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private boolean isListNotNull() {
        return list != null && list.size() > 0;
    }

    public String getForumId(int position){
        return list.get(position - 1).getId();
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
//        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_hui_fu_list, viewGroup, false);
            viewHolder = new ViewHolder(view);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
        if (isListNotNull()) {
            HotPintDetailBean.ForumEntity hf = list.get(i);
            XCircleImgTools.setCircleImg(viewHolder.itemHuiFuImg ,hf.getImg());
            viewHolder.itemHuiFuContentTv.setText(hf.getContent());
            viewHolder.itemHuiFuNameTv.setText(hf.getNicheng());
            viewHolder.itemHuiFuTimeTv.setText(TimeUtil.convertTimeToFormat(Long.parseLong(hf.getTimeline())));
            for (int j = 0; j < hf.getChild().size(); j++) {
                View v = LayoutInflater.from(context).inflate(R.layout.item_hui_fu_hui_fu,null);
                TextView itemHuiFuHuiFuTv = (TextView) v.findViewById(R.id.itemHuiFuHuiFuTv);
                TextView itemHuiFuHuiFuContentTv = (TextView) v.findViewById(R.id.itemHuiFuHuiFuContentTv);
                itemHuiFuHuiFuTv.setText(hf.getChild().get(j).getNicheng()+":");
                itemHuiFuHuiFuContentTv.setText(hf.getChild().get(j).getContent());
                viewHolder.itemHuiFuView.addView(v);
            }
        }
        return view;
    }

    private class ViewHolder {
        TextView itemHuiFuNameTv, itemHuiFuContentTv, itemHuiFuTimeTv;
        ImageView itemHuiFuImg;
        LinearLayout itemHuiFuView;

        ViewHolder(View view) {
            itemHuiFuImg = (ImageView) view.findViewById(R.id.itemHuiFuImg);
            itemHuiFuNameTv = (TextView) view.findViewById(R.id.itemHuiFuNameTv);
            itemHuiFuContentTv = (TextView) view.findViewById(R.id.itemHuiFuContentTv);
            itemHuiFuTimeTv = (TextView) view.findViewById(R.id.itemHuiFuTimeTv);
            itemHuiFuView = (LinearLayout) view.findViewById(R.id.itemHuiFuView);
        }
    }
}
