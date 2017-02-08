package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.CredibilityIntegralBean;
import com.xiandong.fst.utils.TimeUtil;

import java.util.List;

/**
 * Created by dell on 2017/1/21.
 */

public class CredibilityIntegralAdapter extends BaseAdapter {
    List<CredibilityIntegralBean.JfEntity> list;
    Context context;

    public CredibilityIntegralAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<CredibilityIntegralBean.JfEntity> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_ji_fen, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (isListNotNull()) {
            CredibilityIntegralBean.JfEntity one = list.get(i);
            viewHolder.credibilityIntegralFenTv.setText(one.getJifen());
            viewHolder.credibilityIntegralTimeTv.setText(TimeUtil.timeStamp2Date(one.getTimeline(),""));
        }

        return view;
    }

    private class ViewHolder {
        TextView credibilityIntegralFenTv, credibilityIntegralTimeTv;

        ViewHolder(View view) {
            credibilityIntegralTimeTv = (TextView) view.findViewById(R.id.credibilityIntegralTimeTv);
            credibilityIntegralFenTv = (TextView) view.findViewById(R.id.credibilityIntegralFenTv);
        }
    }
}
