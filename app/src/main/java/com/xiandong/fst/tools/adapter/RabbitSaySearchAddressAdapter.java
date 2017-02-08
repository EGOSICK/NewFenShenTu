package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.SearchAddressBean;
import com.xiandong.fst.model.bean.SearchPintsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.view.activity.RabbitSayNeiActivity;

import java.util.List;

/**
 * 兔子说 适配器
 */

public class RabbitSaySearchAddressAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<SearchAddressBean> list;
    private searchResultListener listener;
    private Context context;

    public interface searchResultListener {
        void resultListener(LatLng latLng , String address);
    }

    public void setListener(searchResultListener listener) {
        this.listener = listener;
    }

    public RabbitSaySearchAddressAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<SearchAddressBean> list) {
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
            view = inflater.inflate(R.layout.item_search_address, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (isListNotNull()) {
            final SearchAddressBean sa = list.get(i);
            final StringBuffer sb = new StringBuffer();
            sb.append(sa.getCity()).append(sa.getDistrict()).append(sa.getKey());
            viewHolder.itemSearchAddressTv.setText(sb.toString());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.resultListener(sa.getPt() , sb.toString());
                }
            });
        }

        return view;
    }

    private class ViewHolder {
        TextView itemSearchAddressTv;

        private ViewHolder(View view) {
            itemSearchAddressTv = (TextView) view.findViewById(R.id.itemSearchAddressTv);
        }
    }
}
