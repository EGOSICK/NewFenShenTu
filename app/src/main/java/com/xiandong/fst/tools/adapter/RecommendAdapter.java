package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.PromoteUserBean;
import com.xiandong.fst.tools.XCircleImgTools;

import java.util.List;

/**
 * Created by dell on 2017/1/6.
 */

public class RecommendAdapter extends BaseAdapter {
    List<PromoteUserBean.ChildEntity> list;
    Context context;

    public RecommendAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<PromoteUserBean.ChildEntity> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_recommend, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (isListNotNull()) {
            PromoteUserBean.ChildEntity entity = list.get(i);
            XCircleImgTools.setCircleImg(viewHolder.itemRecommendImg, entity.getImg());
            viewHolder.itemRecommendNameTv.setText(entity.getNicheng());
            viewHolder.itemRecommendPhoneTv.setText(entity.getPhone());
        }
        return view;
    }

    private class ViewHolder {
        ImageView itemRecommendImg;
        TextView itemRecommendNameTv, itemRecommendPhoneTv;

        ViewHolder(View v) {
            itemRecommendImg = (ImageView) v.findViewById(R.id.itemRecommendImg);
            itemRecommendNameTv = (TextView) v.findViewById(R.id.itemRecommendNameTv);
            itemRecommendPhoneTv = (TextView) v.findViewById(R.id.itemRecommendPhoneTv);
        }
    }
}
