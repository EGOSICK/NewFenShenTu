package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.tools.XCircleImgTools;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 接单适配器
 */

public class RabbitOrdersListContentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<OrderListBean.OrderEntity> list;
    private Context context;
    private rabbitOrdersListContentAdapterListener listener;

    public interface rabbitOrdersListContentAdapterListener {
        void rabbitOrdersListContentRobBtnClick(String uid ,String sendId);
    }

    public void setRobBtnClick(rabbitOrdersListContentAdapterListener listener) {
        this.listener = listener;
    }

    public RabbitOrdersListContentAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<OrderListBean.OrderEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private boolean isListNotNull() {
        return list != null && list.size() > 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_rabbit_orders_list_content,
                    container, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (isListNotNull()) {
            OrderListBean.OrderEntity orderEntity = list.get(position);
            final String uid = orderEntity.getId();
            final String sendId = orderEntity.getUid();
            viewHolder.itemROLContentTitleTv.setSelected(true);
            viewHolder.itemROLContentTitleTv.setText(orderEntity.getTitle());
            viewHolder.itemROLContentAddressTv.setSelected(true);
            viewHolder.itemROLContentAddressTv.setText(orderEntity.getPcontent());
            int distance = orderEntity.getDistance();
            if (distance / 1000 > 1) {
                viewHolder.itemROLContentDistanceTv.setText(Double.valueOf(distance / 1000) + "km");
            } else {
                viewHolder.itemROLContentDistanceTv.setText(distance + "m");
            }
            viewHolder.itemROLContentContentTv.setText(orderEntity.getInfo());
            viewHolder.itemROLContentContentTv.setSelected(true);
            viewHolder.itemROLContentMoneyTv.setText(orderEntity.getPrice() + "元");
            XCircleImgTools.setCircleImg(viewHolder.itemROLContentUserImg, orderEntity.getImg());

            switch (orderEntity.getAct()){
                case "0":
                    viewHolder.itemRabbitOrdersRobBtn.setText("抢单");
                    viewHolder.itemRabbitOrdersRobBtn.setBackgroundResource(R.drawable.blue_btn_selector);
                    viewHolder.itemRabbitOrdersRobBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listener != null)
                                listener.rabbitOrdersListContentRobBtnClick(uid,sendId);
                        }
                    });
                    break;
                case "1":
                    viewHolder.itemRabbitOrdersRobBtn.setText("完成");
                    viewHolder.itemRabbitOrdersRobBtn.setBackgroundResource(R.drawable.btn_select_bg_gray);
                    break;
                case "2":
                    viewHolder.itemRabbitOrdersRobBtn.setText("进行中");
                    viewHolder.itemRabbitOrdersRobBtn.setBackgroundResource(R.drawable.btn_select_bg_gray);
                    break;
            }


        }
        return convertView;
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

    private class ViewHolder {
        TextView itemROLContentTitleTv, itemROLContentContentTv,
                itemROLContentAddressTv, itemROLContentDistanceTv, itemROLContentMoneyTv;
        ImageView itemROLContentUserImg, itemROLContentIsSmImg;
        Button itemRabbitOrdersRobBtn;

        ViewHolder(View view) {
            itemROLContentTitleTv = (TextView) view.findViewById(R.id.itemROLContentTitleTv);
            itemROLContentContentTv = (TextView) view.findViewById(R.id.itemROLContentContentTv);
            itemROLContentAddressTv = (TextView) view.findViewById(R.id.itemROLContentAddressTv);
            itemROLContentDistanceTv = (TextView) view.findViewById(R.id.itemROLContentDistanceTv);
            itemROLContentUserImg = (ImageView) view.findViewById(R.id.itemROLContentUserImg);
            itemROLContentIsSmImg = (ImageView) view.findViewById(R.id.itemROLContentIsSmImg);
            itemRabbitOrdersRobBtn = (Button) view.findViewById(R.id.itemRabbitOrdersRobBtn);
            itemROLContentMoneyTv = (TextView) view.findViewById(R.id.itemROLContentMoneyTv);
        }
    }
}
