package com.xiandong.fst.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gastudio.downloadloadding.library.GADownloadingView;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
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
import com.zcw.togglebutton.ToggleButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

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
    @ViewInject(R.id.settingTb)
    ToggleButton settingTb;

    @Override
    protected void initialize() {
        titleTitleTv.setText("设置");
        context = this;
        settingTb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    EaseUI.getInstance().setSettingsProvider(new EaseUI.EaseSettingsProvider() {
                        @Override
                        public boolean isMsgNotifyAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isMsgSoundAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isMsgVibrateAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isSpeakerOpened() {
                            return false;
                        }
                    });
                    JPushInterface.setDefaultPushNotificationBuilder(new
                            BasicPushNotificationBuilder(SettingActivity.this));
//                    CloseSpeaker(0);
                }else {
//                    CloseSpeaker(50);

                    EaseUI.getInstance().setSettingsProvider(new EaseUI.EaseSettingsProvider() {
                        @Override
                        public boolean isMsgNotifyAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isMsgSoundAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isMsgVibrateAllowed(EMMessage message) {
                            return false;
                        }

                        @Override
                        public boolean isSpeakerOpened() {
                            return false;
                        }
                    });
                }
            }
        });
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.updateView,
            R.id.dataCleanView, R.id.shareFriendsView, R.id.logOutView, R.id.feedBackView})
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
                StyledDialogTools.showShareDialog(this, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        WXShareTools.wechatShare(i, context);
                        StyledDialogTools.showLoding(context);
                    }
                });
                WechatShareManager.getInstance(context).registerListener(new WechatShareManager.ShareInterface() {
                    @Override
                    public void shareSuccess() {
                        StyledDialogTools.disMissStyleDialog();
                    }

                    @Override
                    public void shareFails() {
                        StyledDialogTools.disMissStyleDialog();
                    }
                });
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
            case R.id.feedBackView:
                startActivity(new Intent(context, FeedBackActivity.class));
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

    //关闭扬声器
    public void CloseSpeaker(int v) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, v,
                            AudioManager.STREAM_VOICE_CALL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
