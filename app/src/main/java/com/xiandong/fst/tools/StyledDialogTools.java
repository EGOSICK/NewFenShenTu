package com.xiandong.fst.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.jungly.gridpasswordview.GridPasswordView;
import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.MeetBean;
import com.xiandong.fst.model.bean.MyOrdersBean;
import com.xiandong.fst.model.entity.UserEntity;
import com.xiandong.fst.tools.adapter.MeetAddFriendsAdapter;
import com.xiandong.fst.tools.adapter.MeetDetailsAdapter;
import com.xiandong.fst.utils.Code2AndPic;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.utils.TimeUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mirko.android.datetimepicker.date.DatePickerDialog;
import mirko.android.datetimepicker.time.TimePickerDialog;

import static com.xiandong.fst.utils.TimeUtil.pad;

/**
 * buildIosAlert  ios风格 标题 内容 确定 取消
 * <p/>
 * buildMdAlert   app风格 标题 内容 确定 取消
 * <p/>
 * buildCustomBottomSheet  底部View 自定义
 * <p/>
 * buildMdMultiChoose  app风格 多选
 * <p/>
 * buildNormalInput  ios风格 登录
 * <p/>
 * buildCustom  自定义
 */
public class StyledDialogTools {
    private static StyledDialog dialog = new StyledDialog();

    public static void showStyledDialog(Context context) {
        dialog.buildMdLoading(context, "微信登录中...", true, false).show();
    }

    public static void disMissStyleDialog() {
        dialog.dismissLoading();
    }

    public static void showSelectStyleDialog(Context context, List<String> words, String title, MyItemDialogListener listener) {
        dialog.buildBottomItemDialog(context, words, title, listener).show();
    }

    public static void showCodeDialog(Context context, UserEntity user) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_code, null);
        ImageView code = (ImageView) v.findViewById(R.id.dialogCodeImg);
        ImageView uImg = (ImageView) v.findViewById(R.id.dialogCodeUserImg);
        TextView uName = (TextView) v.findViewById(R.id.dialogCodeUserNameTv);
        if (user != null) {
            if (user.isUserLogIn()) {
                x.image().bind(uImg, user.getUserImg());
                uName.setText(user.getUserName());
                if (!StringUtil.isEmpty(user.getUserPhone())) {
                    Code2AndPic.generate(context, user.getUserPhone(), code, 500, 500);
                } else {
                    Code2AndPic.generate(context, user.getUserName(), code, 500, 500);
                }
            }
        }
        dialog.buildCustom(context, v, 0).show().setCanceledOnTouchOutside(true);
    }

    public static void showDeleteDialog(Activity activity, MyDialogListener listener) {
        dialog.buildMdAlert(activity, "删除好友", "确认删除?", listener).show();
    }

    public static void showCustomDialog(Activity context, View v) {
        dialog.buildCustom(context, v, 0).show();
    }

    public static void showLoding(Context context) {
        dialog.buildMdLoading(context, "玩命加载中...", true, false).show();
    }

    public static void showNoticeDialog(Context context, MyDialogListener listener) {
        dialog.buildMdAlert((Activity) context, context.getString(R.string.soft_update_title),
                context.getString(R.string.soft_update_info), listener).show();

    }

    public static void showCleanDataDialog(Context context, MyDialogListener listener) {
        dialog.buildMdAlert((Activity) context, "清除数据",
                "此操作会清除软件所有数据,确定要清除吗", listener).show();
    }

    public static void showLogOutDialog(Context context, MyDialogListener listener) {
        dialog.buildMdAlert((Activity) context, "退出登录", "确定要退出当前账号吗?", listener).show();
    }

    public static void showLogoutMeetDialog(Context context, MyDialogListener listener) {
        dialog.buildMdAlert((Activity) context, "退出群组", "确定要退出当前群组吗?", listener).show();
    }

    public static void showCallDialog(Context context, String phone, MyDialogListener listener) {
        dialog.buildMdAlert((Activity) context, "拨打电话", "呼叫" + phone + "?", listener).show();
    }

    public static void showIsClosePositionDialog(Context context, MyDialogListener listener) {
        dialog.buildMdAlert((Activity) context, "位置", "是否对该好友屏蔽我的位置?", listener).show();
    }

    private static Calendar mCalendar = Calendar.getInstance();

    public static void datePickerDialog(final android.app.FragmentManager fm,
                                        final TextView dateView, final TextView timeView,
                                        final PickerDialogListener listener) {
        final String tag = "";
        final TimePickerDialog timePickerDialog24h = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog dialog, int hourOfDay, int minute) {
                timeView.setText(new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));
                listener.pickerTime(minute, hourOfDay);
            }

            @Override
            public void onTimeCleared(TimePickerDialog timePickerDialog) {
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true, true);
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                        dateView.setText(new StringBuffer().append(pad(year))
                                .append("年").append(pad(monthOfYear + 1))
                                .append("月").append(pad(dayOfMonth))
                                .append("日"));
                        listener.pickerDate(dayOfMonth, monthOfYear, year);
                        timePickerDialog24h.show(fm, tag);
                    }

                    @Override
                    public void onDateCleared(DatePickerDialog datePickerDialog) {
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH), true);
        dialog.show(fm, tag);
    }

    public interface PickerDialogListener {
        void pickerDate(int day, int month, int year);

        void pickerTime(int minute, int hour);
    }

    public static void showOrderDetailsDialog(Context context, MyOrdersBean.OrderEntity entity) {
        final Dialog dialog = new Dialog(context, R.style.alert_dialog);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_orders_details, null);
        TextView contentTv = (TextView) v.findViewById(R.id.dialogOrdersDetailsContentTv);
        TextView addressTv = (TextView) v.findViewById(R.id.dialogOrdersDetailsAddressTv);
        TextView timeTv = (TextView) v.findViewById(R.id.dialogOrdersDetailsTimeTv);
        TextView titleTv = (TextView) v.findViewById(R.id.dialogOrdersDetailsTitleTv);
        TextView moneyTv = (TextView) v.findViewById(R.id.dialogOrdersDetailsMoneyTv);
        ImageView userImg = (ImageView) v.findViewById(R.id.dialogOrdersDetailsUserImg);
        ImageView closeImg = (ImageView) v.findViewById(R.id.dialogOrdersDetailsCloseImg);
        TextView ODEContentTv = (TextView) v.findViewById(R.id.dialogODEvaluationContentTv);
        ImageView ODEUserImg = (ImageView) v.findViewById(R.id.dialogODEvaluationUserImg);
        TextView ODENameTv = (TextView) v.findViewById(R.id.dialogODEvaluationNameTv);
        View ODEView = v.findViewById(R.id.dialogODEvaluationView);
        LinearLayout ODETagView = (LinearLayout) v.findViewById(R.id.dialogODEvaluationMsgView);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        contentTv.setText(entity.getInfo());
        addressTv.setText(entity.getPcontent());
        timeTv.setText(TimeUtil.timeStamp2Date(entity.getTimeline(), ""));
        titleTv.setText(entity.getTitle());
        moneyTv.setText(entity.getTprice());
        XCircleImgTools.setCircleImg(userImg, entity.getImg());
        if (StringUtil.isEmpty(entity.getStar())) {
            ODEView.setVisibility(View.GONE);
        } else {
            ODEView.setVisibility(View.VISIBLE);
            ODEContentTv.setText(entity.getContent());
            ODENameTv.setText(entity.getPnicheng());
            XCircleImgTools.setCircleImg(ODEUserImg, entity.getPimg());
            if (entity.getTags().size() > 0) {
                for (int i = 0; i < entity.getTags().size(); i++) {
                    TextView tag = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_tv, null);
                    tag.setText(entity.getTags().get(i));
                    ODETagView.addView(tag);
                }
            }
        }
        dialog.setContentView(v);
        dialog.show();
    }

    public static Dialog showPayPswDialog(String title, final Context context, GridPasswordView.OnPasswordChangedListener listener) {
        Dialog dialog = new Dialog(context, R.style.alert_dialog);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_pay_password, null);
        TextView payTv = (TextView) v.findViewById(R.id.dialogPayPasswordTv);
        payTv.setText(title);
        GridPasswordView payPswEt = (GridPasswordView) v.findViewById(R.id.dialogPayPasswordEt);
        payPswEt.setSelected(true);
        payPswEt.setFocusable(true);
//        payPswEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
                InputTools.KeyBoard(payPswEt,"open");
//            }
//        });
        payPswEt.setOnPasswordChangedListener(listener);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(v);
        dialog.show();
        return dialog;
    }

    public static Dialog showRedPacket(String msg, Context context, View.OnClickListener listener) {
        Dialog dialog = new Dialog(context, R.style.alert_dialog);

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_red_packet, null);
        TextView msgTv = (TextView) v.findViewById(R.id.dialogRedPacketMsgTv);
        msgTv.setText(msg);
        v.setOnClickListener(listener);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(v);
        dialog.show();
        return dialog;
    }

    public static void showMeetDetailsDialog(Context context, MeetBean meetBean,
                                             String img, String nick) {
        final Dialog dialog = new Dialog(context, R.style.alert_dialog);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_meet_details, null);
        dialog.setContentView(v);
        MeetBean.MeetEntity meet = meetBean.getMeet();
        ImageView closeImg = (ImageView) v.findViewById(R.id.dialogMeetDetailsCloseImg);
        ImageView userImg = (ImageView) v.findViewById(R.id.dialogMeetDetailsUserImg);
        TextView userTv = (TextView) v.findViewById(R.id.dialogMeetDetailsUserTv);
        RecyclerView friendsRv = (RecyclerView) v.findViewById(R.id.dialogMeetFriendsRv);
        TextView contentTv = (TextView) v.findViewById(R.id.dialogMeetDetailsContentTv);
        TextView addressTv = (TextView) v.findViewById(R.id.dialogMeetDetailsAddressTv);
        TextView otherTv = (TextView) v.findViewById(R.id.dialogMeetDetailsOtherTv);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        friendsRv.setLayoutManager(layoutManager);
        MeetDetailsAdapter adapter = new MeetDetailsAdapter(context);
        friendsRv.setAdapter(adapter);
        adapter.addData(meetBean.getMeet().getChild());
        XCircleImgTools.setCircleImg(userImg, img);
        userTv.setText(nick);
        contentTv.setText("时间:  " + TimeUtil.convertTimeToFormat(Long.parseLong(meet.getTimeline())));
        addressTv.setText("地址:  " + meet.getPcontent());
        otherTv.setText("备注:  " + meet.getContent());
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showShareDialog(Activity activity, MyItemDialogListener listener) {
        List<String> s = new ArrayList<>();
        s.add("微信好友");
        s.add("朋友圈");
        dialog.buildBottomItemDialog(activity, s, "取消", listener).show();
    }

    public static void showAgreementDialog(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_agreement, null);
        dialog.buildCustom(context, v, 0).show().setCanceledOnTouchOutside(true);
    }

    public static void showComplacteOrderDialog(Activity activity, MyDialogListener listener) {
        dialog.buildMdAlert(activity, "完成订单", "请确认已经完成订单，提交后不可更改!", listener).show();
    }

    public static void showCancelOrderDialog(Activity activity, MyDialogListener listener) {
        dialog.buildMdAlert(activity, "取消订单",
                "确定取消?", listener).setBtnText("协商取消", "强制取消").show().setCanceledOnTouchOutside(true);
    }
}
