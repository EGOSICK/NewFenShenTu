package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.presenter.AcceptOrderPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.view.AcceptOrderView;
import com.xiandong.fst.view.fragment.RabbitOrdersFragment;

import java.util.List;

/**
 * Created by dell on 2017/1/20.
 */
public class RabbitOrdersVpAdapter extends RecyclingPagerAdapter implements AcceptOrderView {
    Context context;
    List<OrderListBean.OrderEntity> list;
    FragmentTransaction ft ;

    public RabbitOrdersVpAdapter(Context context,FragmentTransaction ft) {
        this.ft = ft;
        this.context = context;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_rabbit_orders_list_content, container, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (isListNotNull()) {
            holder.haveOrdersView.setVisibility(View.VISIBLE);
            holder.noOrdersView.setVisibility(View.GONE);
            int realPosition = position - 100;
            int lp = realPosition % (list.size());
            if (lp < 0)
                lp = list.size() + lp;
            OrderListBean.OrderEntity orderEntity = list.get(lp);
            final String uid = orderEntity.getId();
            holder.itemROLContentTitleTv.setSelected(true);
            holder.itemROLContentTitleTv.setText(orderEntity.getTitle());
            holder.itemROLContentAddressTv.setSelected(true);
            holder.itemROLContentAddressTv.setText(orderEntity.getPcontent());
            int distance = orderEntity.getDistance();
            if (distance / 1000 > 1) {
                holder.itemROLContentDistanceTv.setText(Double.valueOf(distance / 1000) + "km");
            } else {
                holder.itemROLContentDistanceTv.setText(distance + "m");
            }
            holder.itemROLContentContentTv.setText(orderEntity.getInfo());
            holder.itemROLContentContentTv.setSelected(true);
            holder.itemROLContentMoneyTv.setText(orderEntity.getPrice() + "元");
            XCircleImgTools.setCircleImg(holder.itemROLContentUserImg, orderEntity.getImg());
            holder.itemRabbitOrdersRobBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AcceptOrderPresenterImpl acceptOrderPresenter =
                            new AcceptOrderPresenterImpl(RabbitOrdersVpAdapter.this);
                    acceptOrderPresenter.acceptOrder(uid);
                }
            });
        }else {
            holder.haveOrdersView.setVisibility(View.GONE);
            holder.noOrdersView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return isListNotNull() ? Integer.MAX_VALUE : 1;
    }

    @Override
    public void acceptOrderSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
    }

    @Override
    public void acceptOrderFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    private class ViewHolder {
        TextView itemROLContentTitleTv, itemROLContentContentTv,
                itemROLContentAddressTv, itemROLContentDistanceTv, itemROLContentMoneyTv;
        ImageView itemROLContentUserImg, itemROLContentIsSmImg;
        Button itemRabbitOrdersRobBtn;
        View noOrdersView , haveOrdersView;
        int startX, startY;
        boolean isDown;
        ViewHolder(View view) {
            itemROLContentTitleTv = (TextView) view.findViewById(R.id.itemROLContentTitleTv);
            itemROLContentContentTv = (TextView) view.findViewById(R.id.itemROLContentContentTv);
            itemROLContentAddressTv = (TextView) view.findViewById(R.id.itemROLContentAddressTv);
            itemROLContentDistanceTv = (TextView) view.findViewById(R.id.itemROLContentDistanceTv);
            itemROLContentUserImg = (ImageView) view.findViewById(R.id.itemROLContentUserImg);
            itemROLContentIsSmImg = (ImageView) view.findViewById(R.id.itemROLContentIsSmImg);
            itemRabbitOrdersRobBtn = (Button) view.findViewById(R.id.itemRabbitOrdersRobBtn);
            itemROLContentMoneyTv = (TextView) view.findViewById(R.id.itemROLContentMoneyTv);
            noOrdersView = view.findViewById(R.id.noOrdersView);
            haveOrdersView = view.findViewById(R.id.haveOrdersView);

            view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = x;
                        startY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (y - startY > 100) {
                            isDown = false;
                        }

                        if (y - startY < -100) {
                            isDown = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isDown){
                            ft.replace(R.id.viewThree , RabbitOrdersFragment.getInstance().showPager());
                            ft.commit();
                        }
                        break;
                }
                return true;
            }
        });
        }
    }
}
