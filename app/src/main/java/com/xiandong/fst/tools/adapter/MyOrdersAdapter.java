package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MyOrdersBean;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;

/**
 * Created by dell on 2017/1/18.
 */
public class MyOrdersAdapter extends BaseAdapter {
    Context context;
    MyOrdersBean myOrdersBean;
    MyOrdersInterface anInterface;

    public interface MyOrdersInterface {
        void clickListener(int type, String... msg);
    }

    public void setListener(MyOrdersInterface anInterface) {
        this.anInterface = anInterface;
    }

    public MyOrdersAdapter(Context context) {
        this.context = context;
    }

    public void addData(MyOrdersBean myOrdersBean) {
        this.myOrdersBean = myOrdersBean;
        notifyDataSetChanged();
    }

    private boolean isDataNotNull() {
        return myOrdersBean != null && myOrdersBean.getOrder() != null
                && myOrdersBean.getOrder().size() > 0;
    }

    @Override
    public int getCount() {
        return isDataNotNull() ? myOrdersBean.getOrder().size() : 0;
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
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_orders, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (isDataNotNull()) {
            final MyOrdersBean.OrderEntity entity = myOrdersBean.getOrder().get(i);
            XCircleImgTools.setCircleImg(holder.itemMyOrdersImg, entity.getImg());
            holder.itemMyOrdersMoneyTv.setText("赏金:" + entity.getTprice() + "元");
            holder.itemMyOrdersTitleTv.setSelected(true);
            holder.itemMyOrdersTitleTv.setText(entity.getTitle());
            holder.itemMyOrdersContentTv.setSelected(true);
            holder.itemMyOrdersContentTv.setText(entity.getPcontent());
            switch (entity.getAct()) {
                case "-1": // 已失效
                    holder.itemMyOrdersStateTv.setText("已失效");
                    holder.itemMyOrdersStateImg.setImageResource(R.mipmap.wei_wan_cheng);
                    break;
                case "0":  // 没人做
                    holder.itemMyOrdersStateTv.setText("");
                    holder.itemMyOrdersStateImg.setVisibility(View.INVISIBLE);
                    holder.itemMyOrdersOtherBtn.setVisibility(View.GONE);
                    break;
                case "1":  // 已完成
                    boolean isSendUser;
                    if (StringUtil.isEquals(AppDbManager.getUserId(), entity.getUid())) {
                        isSendUser = true;
                    } else {
                        isSendUser = false;
                    }

                    if (StringUtil.isEquals("1", entity.getUact()) &&
                            StringUtil.isEquals("1", entity.getUseract())) {
                        holder.itemMyOrdersStateTv.setText("已完成");
                        if (StringUtil.isEmpty(entity.getStar())){
                            if (isSendUser) {
                                holder.itemMyOrdersOtherBtn.setVisibility(View.VISIBLE);
                                holder.itemMyOrdersOtherBtn.setText("去评价");
                                holder.itemMyOrdersOtherBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (anInterface != null)
                                            anInterface.clickListener(1, entity.getImg(),
                                                    entity.getNicheng(), entity.getId());
                                    }
                                });
                            } else {
                                holder.itemMyOrdersOtherBtn.setVisibility(View.GONE);
                            }
                        }else {
                            holder.itemMyOrdersOtherBtn.setVisibility(View.GONE);
                        }
                    } else {
                        if (StringUtil.isEquals("1", entity.getUact())) {
                            holder.itemMyOrdersStateTv.setText("发单人已完成");
                            if (!isSendUser) {
                                holder.itemMyOrdersOtherBtn.setVisibility(View.VISIBLE);
                                holder.itemMyOrdersOtherBtn.setText("完成订单");
                                holder.itemMyOrdersOtherBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (anInterface != null)
                                            anInterface.clickListener(0, entity.getId());
                                    }
                                });
                            } else {
                                holder.itemMyOrdersOtherBtn.setVisibility(View.GONE);
                            }
                        }

                        if (StringUtil.isEquals("1", entity.getUseract())) {
                            holder.itemMyOrdersStateTv.setText("接单人已完成");
                            if (isSendUser) {
                                holder.itemMyOrdersOtherBtn.setVisibility(View.VISIBLE);
                                holder.itemMyOrdersOtherBtn.setText("完成订单");
                                holder.itemMyOrdersOtherBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (anInterface != null)
                                            anInterface.clickListener(0, entity.getId());
                                    }
                                });
                            } else {
                                holder.itemMyOrdersOtherBtn.setVisibility(View.GONE);
                            }
                        }
                    }
                    holder.itemMyOrdersStateImg.setImageResource(R.mipmap.yi_wan_cheng);
                    break;
                case "2":  // 进行中
                    holder.itemMyOrdersStateTv.setText("进行中");
                    holder.itemMyOrdersStateImg.setImageResource(R.mipmap.jin_xing_zhong);
                    holder.itemMyOrdersOtherBtn.setVisibility(View.VISIBLE);
                    holder.itemMyOrdersOtherBtn.setText("完成订单");
                    holder.itemMyOrdersOtherBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (anInterface != null)
                                anInterface.clickListener(0, entity.getId());
                        }
                    });
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (anInterface != null)
                                anInterface.clickListener(2, entity.getNicheng(), entity.getImg(),
                                        entity.getId(), StringUtil.isEquals(AppDbManager.getUserId()
                                                , entity.getUid()) ? entity.getUser_id() : entity.getUid());
                        }
                    });
                    break;
                case "3":  // 未完成


                    holder.itemMyOrdersStateTv.setText("未完成");
                    holder.itemMyOrdersStateImg.setImageResource(R.mipmap.wei_wan_cheng);
                    holder.itemMyOrdersOtherBtn.setVisibility(View.GONE);
                    break;
            }
            holder.itemMyOrdersDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StyledDialogTools.showOrderDetailsDialog(context, entity);
                }
            });


        }
        return view;
    }

    private class ViewHolder {
        ImageView itemMyOrdersImg, itemMyOrdersStateImg;
        TextView itemMyOrdersMoneyTv, itemMyOrdersTitleTv, itemMyOrdersContentTv,
                itemMyOrdersStateTv, itemMyOrdersDetailsBtn, itemMyOrdersOtherBtn;

        ViewHolder(View view) {
            itemMyOrdersImg = (ImageView) view.findViewById(R.id.itemMyOrdersImg);
            itemMyOrdersMoneyTv = (TextView) view.findViewById(R.id.itemMyOrdersMoneyTv);
            itemMyOrdersTitleTv = (TextView) view.findViewById(R.id.itemMyOrdersTitleTv);
            itemMyOrdersContentTv = (TextView) view.findViewById(R.id.itemMyOrdersContentTv);
            itemMyOrdersStateTv = (TextView) view.findViewById(R.id.itemMyOrdersStateTv);
            itemMyOrdersStateImg = (ImageView) view.findViewById(R.id.itemMyOrdersStateImg);
            itemMyOrdersDetailsBtn = (TextView) view.findViewById(R.id.itemMyOrdersDetailsBtn);
            itemMyOrdersOtherBtn = (TextView) view.findViewById(R.id.itemMyOrdersOtherBtn);

        }
    }
}
