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
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.model.bean.SearchPintsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.view.activity.RabbitSayNeiActivity;

import java.util.List;

/**
 * 兔子说 适配器
 */

public class RabbitSaySearchAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<SearchPintsBean.ForumEntity> list;
    private Context context;
    private String position;

    public void setPosition(String position) {
        this.position = position;
    }

    public RabbitSaySearchAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<SearchPintsBean.ForumEntity> list) {
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
            view = inflater.inflate(R.layout.item_search_result, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (isListNotNull()) {
            final SearchPintsBean.ForumEntity sf = list.get(i);
            XCircleImgTools.setCircleImg(viewHolder.searchResultResultImg, sf.getImg());
            viewHolder.searchResultResultTitleTv.setText(sf.getNicheng());
            viewHolder.searchResultResultContentTv.setText(sf.getContent());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, RabbitSayNeiActivity.class)
                            .putExtra("id", sf.getId()).putExtra("position", position));
                }
            });
        }

        return view;
    }

    private class ViewHolder {
        ImageView searchResultResultImg;
        TextView searchResultResultTitleTv, searchResultResultContentTv;

        private ViewHolder(View view) {
            searchResultResultImg = (ImageView) view.findViewById(R.id.searchResultResultImg);
            searchResultResultTitleTv = (TextView) view.findViewById(R.id.searchResultResultTitleTv);
            searchResultResultContentTv = (TextView) view.findViewById(R.id.searchResultResultContentTv);
        }
    }
}
