package com.xiandong.fst.tools.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.exceptions.HyphenateException;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.customview.pointview.DragIndicatorView;
import com.xiandong.fst.view.fragment.MyChatListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/2/6.
 */
public class MyChatListAdapter extends BaseAdapter {
    private Context context;
    private int selectPosition = 0;
    private List<FriendsBean.FriendEntity> list;
    private OnMyItemSelectListener listener;

    public interface OnMyItemSelectListener {
        void onMyItemSelect(int position);
    }

    public void setOnMyItemSelectListener(OnMyItemSelectListener listener) {
        this.listener = listener;
    }

    public MyChatListAdapter(Context context) {
        this.context = context;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }

    public void addData(List<FriendsBean.FriendEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public String getSelectId() {
        return list.get(selectPosition).getUid();
    }

    public String getSelectTitle() {
        return StringUtil.isEmpty(list.get(selectPosition).getBz()) ?
                list.get(selectPosition).getNicheng() : list.get(selectPosition).getBz();
    }

    private boolean isMapNotNull() {
        return list != null && list.size() > 0;
    }

    @Override
    public int getCount() {
        return isMapNotNull() ? list.size() : 0;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_my_chat, viewGroup, false);
        ImageView chatListImg = (ImageView) view.findViewById(R.id.chatListImg);
        TextView chatListTv = (TextView) view.findViewById(R.id.chatListTv);
        final DragIndicatorView indicator = (DragIndicatorView) view.findViewById(R.id.indicator);
        if (isMapNotNull()) {
            final EMConversation conversation = EMClient.getInstance().chatManager()
                    .getConversation(list.get(i).getUser_id());
            XCircleImgTools.setCircleImg(chatListImg, list.get(i).getImg());
            String s = StringUtil.isEmpty(list.get(i).getBz()) ? list.get(i).getNicheng() : list.get(i).getBz();
            chatListTv.setSelected(true);
            chatListTv.setText(s);
            if (selectPosition == i) {
                view.setBackgroundResource(R.color.appBlue);
                indicator.setVisibility(View.INVISIBLE);
            } else {
                view.setBackgroundColor(0xFF77D7FF);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        if (selectPosition == i)
                            return;
                        listener.onMyItemSelect(i);
                        EMConversation conversation = EMClient.getInstance().chatManager()
                                .getConversation(list.get(i).getUser_id());
                        if (conversation != null) {
                            if (indicator.getVisibility() == View.VISIBLE)
                                indicator.dismissView();
                        } else {
                            if (indicator.getVisibility() == View.VISIBLE)
                                indicator.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });

            if (conversation != null) {
                if (conversation.getUnreadMsgCount() > 0) {
                    if (selectPosition == i) {
                        indicator.setVisibility(View.INVISIBLE);
                    } else {
                        indicator.setText(String.valueOf(conversation.getUnreadMsgCount()));
                        indicator.setVisibility(View.VISIBLE);
                    }
                } else {
                    indicator.setVisibility(View.INVISIBLE);
                }
                indicator.setOnDismissAction(new DragIndicatorView.OnIndicatorDismiss() {
                    @Override
                    public void OnDismiss(DragIndicatorView view) {
                        conversation.markAllMessagesAsRead();
                    }
                });
            } else {
                indicator.dismissView();
            }
        }
        return view;
    }
}
