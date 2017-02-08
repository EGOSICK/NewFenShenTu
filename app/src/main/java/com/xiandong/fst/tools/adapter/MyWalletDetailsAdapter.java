package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MoneyRecordBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.TimeUtil;

/**
 * Created by dell on 2017/1/21.
 */
public class MyWalletDetailsAdapter extends BaseAdapter {
    private Context context;
    private MoneyRecordBean bean;
    private String act ;

    public MyWalletDetailsAdapter(Context context) {
        this.context = context;
    }

    public void addData(String act, MoneyRecordBean bean) {
        this.act = act;
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (act != null)
            switch (act) {
                case "2":
                    return bean.getYJDetails() != null && bean.getYJDetails().size() > 0 ?
                            bean.getYJDetails().size() : 0;
                case "3":
                    return bean.getTXDetails() != null && bean.getTXDetails().size() > 0 ?
                            bean.getTXDetails().size() : 0;
                default:
                    return bean.getXFDetails() != null && bean.getXFDetails().size() > 0 ?
                            bean.getXFDetails().size() : 0;
            }
        return 0;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_my_wallet_detail, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        switch (act) {
            case "2":
                MoneyRecordBean.YJDetails yjDetail = bean.getYJDetails().get(i);
                viewHolder.itemMyWalletDetailMoneyTv.setText(yjDetail.getMoney());
                XCircleImgTools.setCircleImg(viewHolder.itemMyWalletDetailImg, yjDetail.getImg());
                viewHolder.itemMyWalletDetailYjNameTv.setText(yjDetail.getPhone());
                viewHolder.itemMyWalletDetailTimeTv.setText(TimeUtil.timeStamp2Date(yjDetail.getTimeLine(), ""));
                break;
            case "3":
                MoneyRecordBean.TXDetails txDetail = bean.getTXDetails().get(i);
                viewHolder.itemMyWalletDetailImg.setVisibility(View.GONE);
                viewHolder.itemMyWalletDetailTimeTv.setVisibility(View.GONE);
                viewHolder.itemMyWalletDetailYjNameTv.setText(TimeUtil.timeStamp2Date(txDetail.getTimeLine(), ""));
                viewHolder.itemMyWalletDetailMoneyTv.setText(txDetail.getMoney());
                break;
            default:
                MoneyRecordBean.XFDetails xfDetail = bean.getXFDetails().get(i);
                viewHolder.itemMyWalletDetailImg.setVisibility(View.GONE);
                viewHolder.itemMyWalletDetailTimeTv.setVisibility(View.GONE);
                viewHolder.itemMyWalletDetailMoneyTv.setText(xfDetail.getMoney());
                viewHolder.itemMyWalletDetailYjNameTv.setText(TimeUtil.timeStamp2Date(xfDetail.getTimeLine(), ""));
                viewHolder.itemMyWalletDetailTitleTv.setVisibility(View.VISIBLE);
                viewHolder.itemMyWalletDetailTitleTv.setText(xfDetail.getXfName());
                break;
        }

        return view;
    }

    private class ViewHolder {
        TextView itemMyWalletDetailMoneyTv, itemMyWalletDetailYjNameTv,
                itemMyWalletDetailTimeTv, itemMyWalletDetailTitleTv;

        ImageView itemMyWalletDetailImg;

        ViewHolder(View view) {
            itemMyWalletDetailMoneyTv = (TextView) view.findViewById(R.id.itemMyWalletDetailMoneyTv);
            itemMyWalletDetailImg = (ImageView) view.findViewById(R.id.itemMyWalletDetailImg);
            itemMyWalletDetailYjNameTv = (TextView) view.findViewById(R.id.itemMyWalletDetailYjNameTv);
            itemMyWalletDetailTimeTv = (TextView) view.findViewById(R.id.itemMyWalletDetailTimeTv);
            itemMyWalletDetailTitleTv = (TextView) view.findViewById(R.id.itemMyWalletDetailTitleTv);
        }
    }
}
