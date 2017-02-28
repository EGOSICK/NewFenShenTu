package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.SearchFriendsBean;
import com.xiandong.fst.presenter.AddFriendsPresenterImpl;
import com.xiandong.fst.presenter.ContactPresenterImpl;
import com.xiandong.fst.presenter.SearchFriendsPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.adapter.AddFriendsTitleAdapter;
import com.xiandong.fst.tools.adapter.SearchFriendsAdapter;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.AddFriendsView;
import com.xiandong.fst.view.SearchFriendsView;
import com.xiandong.fst.view.fragment.AddFriendsFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by dell on 2017/1/19.
 */
@ContentView(R.layout.activity_add_friends)
public class AddFriendsActivity extends AbsBaseActivity implements SearchFriendsView, AddFriendsView {
    @ViewInject(R.id.addFriendsTl)
    TabLayout addFriendsTl;
    @ViewInject(R.id.addFriendsVp)
    ViewPager addFriendsVp;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.addFriendsSearchLv)
    ListView addFriendsSearchLv;
    @ViewInject(R.id.addFriendsSearchEt)
    EditText addFriendsSearchEt;
    AddFriendsPresenterImpl addFriendsPresenter;
    SearchFriendsPresenterImpl presenter;
    SearchFriendsAdapter adapter;
    Context context;
    AddFriendsTitleAdapter titleAdapter;

    @Override
    protected void initialize() {
        context = this;
        titleTitleTv.setText("加好友");



        addFriendsSearchEt.clearFocus();
        adapter = new SearchFriendsAdapter(context);
        titleAdapter = new AddFriendsTitleAdapter(getSupportFragmentManager());
        addFriendsPresenter = new AddFriendsPresenterImpl(this);
        addFriendsSearchLv.setAdapter(adapter);
        addFriendsSearchLv.addFooterView(new View(this));
        adapter.setListener(new SearchFriendsAdapter.AddFriendsListener() {
            @Override
            public void addFriends(String id) {
                addFriendsPresenter.addFriends(id);
            }
        });
        presenter = new SearchFriendsPresenterImpl(this);

        addFriendsSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !StringUtil.isEmpty(editable.toString()) && editable.length() > 0) {
                    String search = editable.toString().trim();
                    presenter.searchFriends(search);
                    addFriendsSearchLv.setVisibility(View.VISIBLE);

                }else {
                    addFriendsSearchLv.setVisibility(View.GONE);

                }
            }
        });
        addFriendsVp.setAdapter(titleAdapter);
        addFriendsTl.setupWithViewPager(addFriendsVp);

        if (getIntent() != null){
            if (StringUtil.isEquals(getIntent().getStringExtra("notice"),"1")){
                addFriendsTl.getTabAt(1).select();
            }
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg,
            R.id.addFriendsSweepCodeImg})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.addFriendsSweepCodeImg:
                startActivityForResult(new Intent(context, CaptureActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 0) {
                addFriendsSearchEt.setText(data.getStringExtra("result"));
            }
        }
    }

    @Override
    public void searchFriendsFails(String err) {}

    @Override
    public void searchFriendsSuccess(List<SearchFriendsBean> list) {
        adapter.addData(list);
    }

    @Override
    public void addFriendsFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void addFriendsSuccess() {
        CustomToast.customToast(true, "好友请求发送成功", context);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}
