package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.presenter.FriendsManagerPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.FriendsManagerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/20.
 */
@ContentView(R.layout.activity_user_card)
public class UserCardActivity extends AbsBaseActivity implements FriendsManagerView {
    @ViewInject(R.id.userCardImg)
    ImageView userCardImg;
    @ViewInject(R.id.userCardNameEt)
    EditText userCardNameEt;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.userCardPhoneTv)
    TextView userCardPhoneTv;
    @ViewInject(R.id.userCardCreditTv)
    TextView userCardCreditTv;
    @ViewInject(R.id.userCardRealNameTv)
    TextView userCardRealNameTv;
    @ViewInject(R.id.userCardShieldingPositionTb)
    com.zcw.togglebutton.ToggleButton userCardShieldingPositionTb;
    FriendsManagerPresenterImpl presenter;
    Context context;
    FriendsBean.FriendEntity friend;

    @Override
    protected void initialize() {
        context = this;
        presenter = new FriendsManagerPresenterImpl(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("friend");
        friend = bundle.getParcelable("friend");
        XCircleImgTools.setCircleImg(userCardImg, friend.getImg());
        if (StringUtil.isEmpty(friend.getBz()))
            userCardNameEt.setText(friend.getNicheng());
        else
            userCardNameEt.setText(friend.getBz());
        titleTitleTv.setText("");
        userCardPhoneTv.setText(friend.getPhone());
        userCardCreditTv.setText(friend.getJifen() + "分");
        if (StringUtil.isEmpty(friend.getRenzheng()))
            userCardRealNameTv.setText("未实名");
        else
            userCardRealNameTv.setText("已实名");

        switch (friend.getTurnon()){
            case "0":
                userCardShieldingPositionTb.setSelected(true);
                break;
            case "1":
                userCardShieldingPositionTb.setSelected(false);
                break;
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.userCardDeleteFriendTv})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.userCardDeleteFriendTv:
                presenter.deleteFriend(friend.getId());
                break;
        }
    }

    @Event(type = CompoundButton.OnCheckedChangeListener.class,
            value = {R.id.userCardChangeNameTb, R.id.userCardShieldingPositionTb})
    private void changeNameCheckListener(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.userCardShieldingPositionTb:
                if (b) {
                    presenter.shieldingPosition(0, friend.getId());
                } else {
                    presenter.shieldingPosition(1, friend.getId());
                }
                break;
            case R.id.userCardChangeNameTb:
                userCardNameEt.setEnabled(b);
                if (b) {
                    compoundButton.setBackgroundResource(R.mipmap.me_change_name_complect);
                } else {
                    compoundButton.setBackgroundResource(R.mipmap.me_change_name);
                    String cName = userCardNameEt.getText().toString().trim();
                    if (StringUtil.isEmpty(cName)) {
                        CustomToast.customToast(false, "昵称不可为空", context);
                    } else {
                        if (StringUtil.isEquals(friend.getBz(), cName))
                            return;
                        if (StringUtil.isEquals(friend.getNicheng(), cName))
                            return;
                        //////////提交修改操作
                        presenter.changeFriendBz(friend.getId(), cName);
                    }
                }
                break;
        }
    }

    @Override
    public void changeFriendMessageSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
    }

    @Override
    public void changeFriendsMessageFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void deleteFriendSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
        finish();
    }
}
