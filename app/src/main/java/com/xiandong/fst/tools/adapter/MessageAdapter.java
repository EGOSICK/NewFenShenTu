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
import com.xiandong.fst.model.bean.MessageBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.TimeUtil;

import org.xutils.x;

import java.util.List;

/**
 * Created by dell on 2017/2/15.
 */
public class MessageAdapter extends BaseAdapter {
    List<MessageBean.NoticeEntity> list;
    Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<MessageBean.NoticeEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private boolean isListNotNull() {
        return list != null && list.size() > 0;
    }

    public String getUrl(int position){
        return list.get(position).getUrl();
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
        MessageViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false);
            holder = new MessageViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MessageViewHolder) view.getTag();
        }
        if (isListNotNull()) {
            MessageBean.NoticeEntity item = list.get(i);
            switch (item.getType()) {
                case "1":
                    holder.messageTypeImg.setImageResource(R.mipmap.message_one);
                    break;
                case "2":
                    holder.messageTypeImg.setImageResource(R.mipmap.message_two);
                    break;
            }
            holder.messageTv.setText(item.getContent());
            holder.messageTimeTv.setText(TimeUtil.convertTimeToFormat(Long.parseLong(item.getTimeline())));
            if (item.getImg() != null && item.getImg().size() > 0) {
                if (item.getImg().size() > 2) {
                    holder.messageContentFirstIv.setVisibility(View.VISIBLE);
                    holder.messageContentSecondIv.setVisibility(View.VISIBLE);
                    holder.messageContentThreadIv.setVisibility(View.VISIBLE);
                    x.image().bind(holder.messageContentFirstIv, item.getImg().get(0));
                    x.image().bind(holder.messageContentSecondIv, item.getImg().get(1));
                    x.image().bind(holder.messageContentThreadIv, item.getImg().get(2));
                }else if (item.getImg().size() > 1){
                    holder.messageContentFirstIv.setVisibility(View.VISIBLE);
                    holder.messageContentSecondIv.setVisibility(View.VISIBLE);
                    holder.messageContentThreadIv.setVisibility(View.INVISIBLE);
                    x.image().bind(holder.messageContentFirstIv, item.getImg().get(0));
                    x.image().bind(holder.messageContentSecondIv, item.getImg().get(1));
                }else {
                    holder.messageContentFirstIv.setVisibility(View.VISIBLE);
                    holder.messageContentSecondIv.setVisibility(View.INVISIBLE);
                    holder.messageContentThreadIv.setVisibility(View.INVISIBLE);
                    x.image().bind(holder.messageContentFirstIv, item.getImg().get(0));
                }
            }else {
                holder.messageContentImgSView.setVisibility(View.GONE);
            }

        }
        return view;
    }

    private class MessageViewHolder {
        TextView messageTv, messageTimeTv;
        ImageView messageTypeImg, messageContentFirstIv, messageContentSecondIv, messageContentThreadIv;
        LinearLayout messageContentImgSView;

        MessageViewHolder(View view) {
            messageTv = (TextView) view.findViewById(R.id.messageTv);
            messageTimeTv = (TextView) view.findViewById(R.id.messageTimeTv);
            messageTypeImg = (ImageView) view.findViewById(R.id.messageTypeImg);
            messageContentImgSView = (LinearLayout) view.findViewById(R.id.messageContentImgSView);
            messageContentFirstIv = (ImageView) view.findViewById(R.id.messageContentFirstIv);
            messageContentSecondIv = (ImageView) view.findViewById(R.id.messageContentSecondIv);
            messageContentThreadIv = (ImageView) view.findViewById(R.id.messageContentThreadIv);
        }
    }
}
