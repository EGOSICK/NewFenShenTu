package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.presenter.ChangeUserMessagePresenterImpl;
import com.xiandong.fst.presenter.ChooseImageViewPresenterImpl;
import com.xiandong.fst.presenter.UserMessagePresenterImpl;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.view.ChangeUserMessageView;
import com.xiandong.fst.view.ChooseImageViewView;
import com.xiandong.fst.view.UserMessageView;
import com.xiandong.fst.view.activity.AboutMeActivity;
import com.xiandong.fst.view.activity.CredibilityIntegralActivity;
import com.xiandong.fst.view.activity.MessageActivity;
import com.xiandong.fst.view.activity.MyOrdersActivity;
import com.xiandong.fst.view.activity.MyWalletActivity;
import com.xiandong.fst.view.activity.QianDaoActivity;
import com.xiandong.fst.view.activity.SecurityCenterActivity;
import com.xiandong.fst.view.activity.SettingActivity;
import com.xiandong.fst.view.customview.CircularImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 我的
 */


@ContentView(R.layout.fragment_rabbit_me)
public class RabbitMeFragment extends AbsBaseFragment implements ChooseImageViewView, UserMessageView {
    Context context;
    @ViewInject(R.id.rabbitMeSettingView)
    View rabbitMeSettingView;
    @ViewInject(R.id.rabbitMeAboutMeView)
    View rabbitMeAboutMeView;
    @ViewInject(R.id.rabbitMeSecuritySettingView)
    View rabbitMeSecuritySettingView;
    @ViewInject(R.id.rabbitMeCreditScoreView)
    View rabbitMeCreditScoreView;
    @ViewInject(R.id.rabbitMeWalletView)
    View rabbitMeWalletView;
    @ViewInject(R.id.rabbitMeOrdersView)
    View rabbitMeOrdersView;
    @ViewInject(R.id.rabbitMeUserImg)
    CircularImageView rabbitMeUserImg;
    @ViewInject(R.id.rabbitMeUserNameTv)
    TextView rabbitMeUserNameTv;
    @ViewInject(R.id.rabbitMeBarCodeImg)
    ImageView rabbitMeBarCodeImg;
    @ViewInject(R.id.rabbitMeChangeNameImg)
    ToggleButton compoundButton;
    ChooseImageViewPresenterImpl chooseImageViewPresenter;
    UserEntity user;
    UserMessagePresenterImpl presenter;
    boolean isCheck = false;
    @ViewInject(R.id.rabbitMeChangeNameImg)
    ToggleButton rabbitMeChangeNameImg;

    private static RabbitMeFragment rabbitMeFragment = null;

    public static RabbitMeFragment getInstance() {
        if (rabbitMeFragment == null)
            rabbitMeFragment = new RabbitMeFragment();
        return rabbitMeFragment;
    }

    public RabbitMeFragment showPager() {
        getMainActivity().cleanMarks();
        MarkMapTools.choosePager(false, false, false, false, true);
        initCheck();
        return rabbitMeFragment;
    }

    @Override
    protected void initialize() {
        context = getContext();
        chooseImageViewPresenter = new ChooseImageViewPresenterImpl(this);
        initUserData();
    }

    private void initUserData() {
        presenter = new UserMessagePresenterImpl(this);
        presenter.getUserMessage();
    }

    @Event(type = CompoundButton.OnCheckedChangeListener.class,
            value = {R.id.rabbitMeChangeNameImg})
    private void changeNameCheckListener(CompoundButton compoundButton, boolean b) {
        isCheck = b;
        rabbitMeUserNameTv.setEnabled(b);
        if (b) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            compoundButton.setBackgroundResource(R.mipmap.me_change_name_complect);
        } else {
            compoundButton.setBackgroundResource(R.mipmap.me_change_name);
            String cName = rabbitMeUserNameTv.getText().toString().trim();
            if (StringUtil.isEmpty(cName)) {
                CustomToast.customToast(false, "昵称不可为空", context);
            } else {
                if (StringUtil.isEquals(user.getUserName(), cName)) {

                } else {
                    //////////提交修改操作
                    ChangeUserMessagePresenterImpl presenter =
                            new ChangeUserMessagePresenterImpl(
                                    new ChangeUserMessageView() {
                                        @Override
                                        public void changeUserMsgSuccess() {
                                            CustomToast.customToast(true, "昵称修改成功", context);
                                        }

                                        @Override
                                        public void changeUserMsgFails(String err) {
                                            CustomToast.customToast(false, err, context);
                                        }
                                    });
                    presenter.changeUserMessage(cName);
                }
            }
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.rabbitMeSettingView,
            R.id.rabbitMeAboutMeView, R.id.rabbitMeUserImg, R.id.rabbitMeBarCodeImg,
            R.id.rabbitMeWalletView, R.id.rabbitMeSecuritySettingView, R.id.rabbitMeOrdersView,
            R.id.rabbitMeCreditScoreView, R.id.rabbitMeQianDaoImg, R.id.rabbitMeMessageImg})
    private void rabbitMeOnClick(View view) {
        switch (view.getId()) {
            case R.id.rabbitMeQianDaoImg:
                startActivity(new Intent(context, QianDaoActivity.class));
                break;
            case R.id.rabbitMeMessageImg:
                startActivity(new Intent(context, MessageActivity.class));
                break;
            case R.id.rabbitMeUserImg:
                StyledDialogTools.showSelectStyleDialog(context, chooseImageViewPresenter.chooseImageView(),
                        "", new MyItemDialogListener() {
                            @Override
                            public void onItemClick(CharSequence charSequence, int i) {
                                switch (i) {
                                    case 0:
                                        Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                                                new File(Constant.sImgPath, Constant.sImgname)));
                                        startActivityForResult(intent_camera, Constant.PHOTOHRAPH);
                                        break;
                                    case 1:
                                        Intent intent_photobox = new Intent(Intent.ACTION_PICK, null);
                                        intent_photobox.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                Constant.IMAGE_UNSPECIFIED);
                                        startActivityForResult(intent_photobox, Constant.PHOTOZOOM);
                                        break;
                                }
                                StyledDialogTools.disMissStyleDialog();
                            }
                        });
                break;

            case R.id.rabbitMeSettingView:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.rabbitMeSecuritySettingView:
                startActivity(new Intent(context, SecurityCenterActivity.class));
                break;
            case R.id.rabbitMeAboutMeView:
                startActivity(new Intent(context, AboutMeActivity.class));
                break;
            case R.id.rabbitMeBarCodeImg:
                StyledDialogTools.showCodeDialog(context, user);
                break;
            case R.id.rabbitMeWalletView:
                startActivity(new Intent(context, MyWalletActivity.class));
                break;
            case R.id.rabbitMeOrdersView:
                startActivity(new Intent(context, MyOrdersActivity.class));
                break;
            case R.id.rabbitMeCreditScoreView:
                startActivity(new Intent(context, CredibilityIntegralActivity.class)
                        .putExtra("fen", user.getUserJiFen()));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.NONE)
            return;
        //拍照
        if (requestCode == Constant.PHOTOHRAPH) {
            //设置文件保存路径这里放在跟目录下
            File picture = new File(Constant.sImgPath +
                    Constant.sImgSeparator +
                    Constant.sImgname);
            startPhotoZoom(Uri.fromFile(picture));
        }
        //读取相册缩放图片
        if (requestCode == Constant.PHOTOZOOM) {
            if (data == null)
                return;
            startPhotoZoom(data.getData());
        }
        //处理结果
        if (requestCode == Constant.PHOTORESOULT) {
            if (data == null)
                return;
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件
                rabbitMeUserImg.setImageBitmap(photo);
                chooseImageViewPresenter.upDataUserImg(photo);
            }
        }
        if (requestCode == Constant.USERIMGCODE) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - 100)压缩文件
                    rabbitMeUserImg.setImageBitmap(photo);
                    chooseImageViewPresenter.upDataUserImg(photo);
                }
            }
        }
    }

    /**
     * 图片裁剪
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, Constant.IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constant.PHOTORESOULT);
    }

    @Override
    public void upDataUserImgSuccess(String msg) {
        CustomToast.customToast(true, msg, context);
    }

    @Override
    public void upDataUserImgFails(String err) {
        CustomToast.customToast(false, err, context);
    }

    @Override
    public void getUserMessageSuccess(UserBean userBean) {
        user = AppDbManager.getLastUser();
        if (user != null && user.isUserLogIn()) {
            XCircleImgTools.setCircleImg(rabbitMeUserImg, user.getUserImg());
            rabbitMeUserNameTv.setText(user.getUserName());
        }
    }

    @Override
    public void getUserMessageFails(String err) {
        CustomToast.customToast(false, err, context);
        user = AppDbManager.getLastUser();
        if (user != null && user.isUserLogIn()) {
            XCircleImgTools.setCircleImg(rabbitMeUserImg, user.getUserImg());
            rabbitMeUserNameTv.setText(user.getUserName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initCheck();
    }

    private void initCheck() {
        if (isCheck) {
            rabbitMeChangeNameImg.setChecked(false);
            rabbitMeUserNameTv.setEnabled(false);
            compoundButton.setBackgroundResource(R.mipmap.me_change_name);
            initUserData();
        }
    }
}
