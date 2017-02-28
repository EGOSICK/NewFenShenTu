package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.PromoteUserBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.FillInRecommendedPersonPresenterImpl;
import com.xiandong.fst.presenter.PromoteUserPresenterImpl;
import com.xiandong.fst.tools.CircularProgressButtonTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.FillInRecommendedPersonView;
import com.xiandong.fst.view.PromoteUserView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 关于我们 activity
 */

@ContentView(R.layout.activity_about_me)
public class AboutMeActivity extends AbsBaseActivity implements
        FillInRecommendedPersonView, PromoteUserView {
    Context context;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.aboutFSTView)
    View aboutFSTView;
    @ViewInject(R.id.serviceProvisionView)
    View serviceProvisionView;
    @ViewInject(R.id.fillInRecommendedPersonView)
    View fillInRecommendedPersonView;
    @ViewInject(R.id.aboutMePromoteUserNumTv)
    TextView aboutMePromoteUserNumTv;

    CircularProgressButton dialogFillInBtn;
    FillInRecommendedPersonPresenterImpl presenter;
    PromoteUserBean bean;

    @Override
    protected void initialize() {
        initView();
        initData();
    }

    private void initView() {
        context = this;
        presenter = new FillInRecommendedPersonPresenterImpl(this);
        titleTitleTv.setText("关于我们");
        PromoteUserPresenterImpl presenter = new PromoteUserPresenterImpl(this);
        presenter.getPromoteUser();

    }

    private void initData() {
        UserEntity user = AppDbManager.getLastUser();
        if (user != null) {
            if (!StringUtil.isEmpty(user.getUserRestTime())) {
                long rt = Long.parseLong(user.getUserRestTime());
                if (rt < 0) {
                    fillInRecommendedPersonView.setVisibility(View.GONE);
                } else {
                    if (StringUtil.isEmpty(user.getUserReferer())) {
                        fillInRecommendedPersonView.setVisibility(View.VISIBLE);
                    } else {
                        fillInRecommendedPersonView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.aboutFSTView, R.id.serviceProvisionView,
            R.id.fillInRecommendedPersonView, R.id.recommendView, R.id.titleBackImg})
    private void aboutMeOnClick(View v) {
        switch (v.getId()) {
            case R.id.aboutFSTView:
                startActivity(new Intent(context, AboutFSTActivity.class));
                break;
            case R.id.serviceProvisionView:
                startActivity(new Intent(context, ServiceProvisionActivity.class));
                break;
            case R.id.fillInRecommendedPersonView:
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_fill_in, null);
                dialogFillInBtn = (CircularProgressButton) dialogView.findViewById(R.id.dialogFillInBtn);
                dialogFillInBtn.setText("完成");
                dialogFillInBtn.setIdleText("完成");
                final EditText dialogFillInEt = (EditText) dialogView.findViewById(R.id.dialogFillInEt);
                StyledDialogTools.showCustomDialog(this, dialogView);
                dialogFillInBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CircularProgressButtonTools.showLoding(dialogFillInBtn);
                        presenter.fillInRecommendedPerson(dialogFillInEt.getText().toString().trim(), context);
                    }
                });
                break;
            case R.id.recommendView:
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("promoteUser", bean);
                    startActivity(new Intent(context, RecommendActivity.class)
                            .putExtra("promoteUser", bundle));
                break;
            case R.id.titleBackImg:
                finish();
                break;
        }
    }

    @Override
    public void fillInRecommendedPersonSuccess(String msg) {
        CustomToast.customToast(false, msg, context);
        CircularProgressButtonTools.showTrue(dialogFillInBtn);
        StyledDialogTools.disMissStyleDialog();
    }

    @Override
    public void fillInRecommendedPersonFails(String err) {
        CustomToast.customToast(false, err, context);
        CircularProgressButtonTools.showErr(dialogFillInBtn);
    }

    @Override
    public void getPromoteUserFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void getPromoteUserSuccess() {
        initData();
    }

    @Override
    public void getPromoteUserSuccess(PromoteUserBean bean) {
        aboutMePromoteUserNumTv.setText(bean.getChildren() + "人");
        this.bean = bean;
    }
}
