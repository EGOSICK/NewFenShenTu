package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.GetNewFriendsModelImpl;
import com.xiandong.fst.model.bean.ContactBean;
import com.xiandong.fst.model.bean.NewFriendsBean;
import com.xiandong.fst.presenter.ContactPresenterImpl;
import com.xiandong.fst.presenter.GetNewFriendsPresenter;
import com.xiandong.fst.presenter.GetNewFriendsPresenterImpl;
import com.xiandong.fst.presenter.PayPresenter;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.adapter.AddFriendsAdapter;
import com.xiandong.fst.utils.CharacterParser;
import com.xiandong.fst.view.AddFriendsView;
import com.xiandong.fst.view.ContactView;
import com.xiandong.fst.view.GetNewFriendsView;
import com.xiandong.fst.view.customview.emptyview.HHEmptyView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 2017/1/19.
 */

@ContentView(R.layout.fragment_add_friends)
public class AddFriendsFragment extends AbsBaseFragment implements ContactView,
        AddFriendsView, GetNewFriendsView {
    public static AddFriendsFragment getInstance(int position) {
        AddFriendsFragment fragment = new AddFriendsFragment();
        fragment.getData(position);
        return fragment;
    }

    int position;
    Context context;

    private void getData(int position) {
        this.position = position;
    }

    @ViewInject(R.id.addFriendsLv)
    ListView addFriendsLv;
    @ViewInject(R.id.hhEmptyView)
    HHEmptyView hhEmptyView;
    AddFriendsAdapter adapter;
    ContactPresenterImpl presenter;
    GetNewFriendsPresenterImpl getNewFriendsPresenter;

    @Override
    protected void initialize() {
        initData();
        switch (position) {
            case 0:
                initContact();
                break;
            case 1:
                initNewFriends();
                break;
        }

    }

    private void initData() {
        context = getContext();
        adapter = new AddFriendsAdapter(context);
        addFriendsLv.setAdapter(adapter);
        hhEmptyView.bindView(addFriendsLv);
        hhEmptyView.setOnBtnClickListener(new HHEmptyView.OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                switch (position) {
                    case 0:
                        presenter.upDateContact(context);
                        break;
                    case 1:
                        getNewFriendsPresenter.getNewFriends();
                        break;
                }
            }
        });
        adapter.setRightBtnClick(new AddFriendsAdapter.RightBtnClick() {
            @Override
            public void rightBtnClick(int type, String phone) {
                switch (type) {
                    case 1:
                        presenter.addFriends(phone);
                        break;
                    case 2:
                        Uri uri = Uri.parse("smsto:" + phone);
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                        sendIntent.putExtra("sms_body", context.getString(R.string.send_content));
                        context.startActivity(sendIntent);
                        break;
                }
            }
        });
    }

    private void initContact() {
        upContactDate();
    }

    private void initNewFriends() {
        getNewFriendsPresenter = new GetNewFriendsPresenterImpl(this);
        getNewFriendsPresenter.getNewFriends();
        adapter.setRightBtnClick(new AddFriendsAdapter.RightBtnClick() {
            @Override
            public void rightBtnClick(int type, String phone) {
                switch (type) {
                    case 1:
                        getNewFriendsPresenter.agreedAddFriends(phone);
                        break;
                }
            }
        });
    }

    private void upContactDate() {
        presenter = new ContactPresenterImpl(this, this);
        presenter.upDateContact(context);
    }

    @Override
    public void upContactDateSuccess() {
        presenter.getContactDate();
    }

    @Override
    public void upContactDateFails(String err) {
        showFails(err);
    }

    @Override
    public void getContactDateSuccess(List<ContactBean> list) {
        adapter.addData(list);
        hhEmptyView.success();
    }

    @Override
    public void getContactDateFails(String err) {
        showFails(err);
        hhEmptyView.empty();
    }

    @Override
    public void addFriendsFails(String err) {
        showFails(err);
    }

    @Override
    public void addFriendsSuccess() {
        CustomToast.customToast(true, "添加请求已发送", context);
    }

    @Override
    public void getNewFriendsSuccess(NewFriendsBean friendsBean) {
        adapter.addNewFriendsData(friendsBean);
        hhEmptyView.success();
    }

    @Override
    public void getNewFriendsFails(String err) {
        showFails(err);
        hhEmptyView.empty();
    }

    @Override
    public void agreedAddFriendsFails(String err) {
        showFails(err);
    }

    @Override
    public void agreedAddFriendsSuccess() {
        CustomToast.customToast(true, "同意成功", context);
        getNewFriendsPresenter.getNewFriends();
    }

    private void showFails(String err) {
        CustomToast.customToast(false, err, context);
    }


//    /**
//     * 上次第一个可见元素，用于滚动时记录标识。
//     */
//    private int lastFirstVisibleItem = -1;
//    /**
//     * 汉字转换成拼音的类
//     */
//    private CharacterParser characterParser;
//    private List<GroupMemberBean> SourceDateList;
//
//    /**
//     * 根据拼音来排列ListView里面的数据类
//     */
//    private PinyinComparator pinyinComparator;
//    private void setListViewMode(){
//        addFriendsLv.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                int section = getSectionForPosition(firstVisibleItem);
//                int nextSection = getSectionForPosition(firstVisibleItem + 1);
//                int nextSecPosition = getPositionForSection(+nextSection);
//                if (firstVisibleItem != lastFirstVisibleItem) {
//                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
//                            .getLayoutParams();
//                    params.topMargin = 0;
//                    titleLayout.setLayoutParams(params);
//                    title.setText(SourceDateList.get(
//                            getPositionForSection(section)).getSortLetters());
//                }
//                if (nextSecPosition == firstVisibleItem + 1) {
//                    View childView = view.getChildAt(0);
//                    if (childView != null) {
//                        int titleHeight = titleLayout.getHeight();
//                        int bottom = childView.getBottom();
//                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
//                                .getLayoutParams();
//                        if (bottom < titleHeight) {
//                            float pushedDistance = bottom - titleHeight;
//                            params.topMargin = (int) pushedDistance;
//                            titleLayout.setLayoutParams(params);
//                        } else {
//                            if (params.topMargin != 0) {
//                                params.topMargin = 0;
//                                titleLayout.setLayoutParams(params);
//                            }
//                        }
//                    }
//                }
//                lastFirstVisibleItem = firstVisibleItem;
//            }
//        });
//    }
//
//    /**
//     * 为ListView填充数据
//     *
//     * @param list
//     * @return
//     */
//    private List<GroupMemberBean> filledData(List<TongXunLuEntity> list) {
//        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
//
//        for (int i = 0; i < list.size(); i++) {
//            GroupMemberBean sortModel = new GroupMemberBean();
//            sortModel.setName(list.get(i).getName());
//            // 汉字转换成拼音
//            String pinyin = characterParser.getSelling(list.get(i).getName());
//            String sortString = pinyin.substring(0, 1).toUpperCase();
//
//            // 正则表达式，判断首字母是否是英文字母
//            if (sortString.matches("[A-Z]")) {
//                sortModel.setSortLetters(sortString.toUpperCase());
//            } else {
//                sortModel.setSortLetters("#");
//            }
//            sortModel.setStatus(list.get(i).getStatus());
//            sortModel.setHaoyou(list.get(i).getHaoyou());
//            sortModel.setPhoneNum(list.get(i).getPhone());
//            sortModel.setId(list.get(i).getId());
//            mSortList.add(sortModel);
//        }
//        return mSortList;
//    }
//
//    /**
//     * 根据输入框中的值来过滤数据并更新ListView
//     *
//     * @param filterStr
//     */
//    private void filterData(String filterStr) {
//        List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();
//
//        if (TextUtils.isEmpty(filterStr)) {
//            filterDateList = SourceDateList;
//            tvNofriends.setVisibility(View.GONE);
//        } else {
//            filterDateList.clear();
//            for (GroupMemberBean sortModel : SourceDateList) {
//                String name = sortModel.getName();
//                if (name.indexOf(filterStr.toString()) != -1
//                        || characterParser.getSelling(name).startsWith(
//                        filterStr.toString())) {
//                    filterDateList.add(sortModel);
//                }
//            }
//        }
//
//        // 根据a-z进行排序
//        Collections.sort(filterDateList, pinyinComparator);
//        stAdapter.updateListView(filterDateList);
//        if (filterDateList.size() == 0) {
//            tvNofriends.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    /**
//     * 根据ListView的当前位置获取分类的首字母的Char ascii值
//     */
//    public int getSectionForPosition(int position) {
//        if (position < list.size())
//
//            return SourceDateList.get(position).getSortLetters().charAt(0);
//
//        else
//            return list.size();
//    }
//
//    /**
//     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
//     */
//    public int getPositionForSection(int section) {
//        for (int i = 0; i < SourceDateList.size(); i++) {
//            String sortStr = SourceDateList.get(i).getSortLetters();
//            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section) {
//                return i;
//            }
//        }
//        return -1;
//    }
}
