package com.xiandong.fst.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gastudio.downloadloadding.library.GADownloadingView;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hyphenate.chat.EMClient;
import com.xiandong.fst.R;
import com.xiandong.fst.model.LogOutModelImpl;
import com.xiandong.fst.presenter.LogOutPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.WXShareTools;
import com.xiandong.fst.tools.WechatShareManager;
import com.xiandong.fst.utils.DataCleanManager;
import com.xiandong.fst.utils.UpdateManager;
import com.xiandong.fst.view.LogOutView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 设置activity
 */

@ContentView(R.layout.activity_setting)
public class SettingActivity extends AbsBaseActivity implements LogOutView {
    Context context;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.updateView)
    View updateView;
    @ViewInject(R.id.dataCleanView)
    View dataCleanView;
    @ViewInject(R.id.shareFriendsView)
    View shareFriendsView;

    @Override
    protected void initialize() {
        titleTitleTv.setText("设置");
        context = this;
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.updateView,
            R.id.dataCleanView, R.id.shareFriendsView, R.id.logOutView})
    private void setttingOnCLick(final View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.updateView:
                UpdateManager updateManager = new UpdateManager(context);
                updateManager.checkUpdate(true);
                break;
            case R.id.dataCleanView:
                StyledDialogTools.showCleanDataDialog(context, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        DataCleanManager.cleanApplicationData(context);
                        StyledDialogTools.disMissStyleDialog();
                        CustomToast.customToast(true, "清除完成", context);
                    }

                    @Override
                    public void onSecond() {
                        StyledDialogTools.disMissStyleDialog();
                    }
                });
                break;
            case R.id.shareFriendsView:
//                WechatShareManager shareManager = WechatShareManager.getInstance(context);
//                shareManager.shareByWebchat(shareManager.getShareContentPicture(
//                        R.layout.activity_start), 1);
//                shareManager.registerListener(new WechatShareManager.ShareInterface() {
//                    @Override
//                    public void shareSuccess() {
//                        CustomToast.customToast(true, "分享成功", context);
//                    }
//
//                    @Override
//                    public void shareFails() {
//                        CustomToast.customToast(false, "分享失败", context);
//                    }
//                });

                WXShareTools.wechatShare(0,context);

                break;
            case R.id.logOutView:
                StyledDialogTools.showLogOutDialog(context, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        LogOutPresenterImpl presenter = new LogOutPresenterImpl(SettingActivity.this);
                        presenter.logOut();
                    }

                    @Override
                    public void onSecond() {
                        StyledDialogTools.disMissStyleDialog();
                    }
                });
                break;
        }
    }

    @Override
    public void logOutSuccess() {
        EMClient.getInstance().logout(true);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(context, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    public void logOutFails(String err) {
        CustomToast.customToast(false, err, context);
    }
}
