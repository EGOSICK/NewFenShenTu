package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/21.
 */
@ContentView(R.layout.activity_evaluation_order)
public class EvaluationOrderActivity extends AbsBaseActivity {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.evaluationCommitBtn)
    CircularProgressButton evaluationCommitBtn;
    @ViewInject(R.id.evaluationNameTv)
    TextView evaluationNameTv;
    @ViewInject(R.id.evaluationUserImg)
    ImageView evaluationUserImg;
    @ViewInject(R.id.ratingBar)
    RatingBar ratingBar;
    @ViewInject(R.id.evaluationContentEt)
    EditText evaluationContentEt;

    float star = 1;
    String id, name;
    @ViewInject(R.id.tjkBtn)
    TextView tjkBtn;
    @ViewInject(R.id.tdhBtn)
    TextView tdhBtn;
    @ViewInject(R.id.fwhBtn)
    TextView fwhBtn;
    @ViewInject(R.id.wchBtn)
    TextView wchBtn;
    @ViewInject(R.id.yscBtn)
    TextView yscBtn;
    @ViewInject(R.id.tdbBtn)
    TextView tdbBtn;
    @ViewInject(R.id.eyjBtn)
    TextView eyjBtn;
    @ViewInject(R.id.saoBtn)
    TextView saoBtn;


    @Override
    protected void initialize() {
        titleTitleTv.setText("订单评价");
        evaluationCommitBtn.setText("提交评价");
        evaluationCommitBtn.setIdleText("提交评价");
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        String img = intent.getStringExtra("img");
        id = intent.getStringExtra("id");

        evaluationNameTv.setText(name);
        XCircleImgTools.setCircleImg(evaluationUserImg, img);

        selectTag();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.evaluationCommitBtn,
            R.id.tjkBtn, R.id.tdhBtn, R.id.fwhBtn, R.id.wchBtn,
            R.id.yscBtn, R.id.tdbBtn, R.id.eyjBtn, R.id.saoBtn})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.evaluationCommitBtn:
                commit();
                break;
            case R.id.tjkBtn:
                if (!isDown) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;
            case R.id.tdhBtn:
                if (!isDown) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;
            case R.id.fwhBtn:
                if (!isDown) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;
            case R.id.wchBtn:
                if (!isDown) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;

            case R.id.yscBtn:
                if (!isUp) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;
            case R.id.tdbBtn:
                if (!isUp) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;
            case R.id.eyjBtn:
                if (!isUp) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;
            case R.id.saoBtn:
                if (!isUp) {
                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
                break;

        }
        selectTag();
    }

    boolean isUp = false;
    boolean isDown = false;

    private void selectTag() {
        if (tjkBtn.isSelected() || tdhBtn.isSelected() || fwhBtn.isSelected() || wchBtn.isSelected()) {
            isUp = true;
        } else {
            isUp = false;
        }

        if (yscBtn.isSelected() || tdbBtn.isSelected() || eyjBtn.isSelected() || saoBtn.isSelected()) {
            isDown = true;
        } else {
            isDown = false;
        }
    }


    @Event(type = RatingBar.OnRatingBarChangeListener.class, value = R.id.ratingBar)
    private void ratingListener(RatingBar ratingBar, float rating, boolean fromUser) {
        if (fromUser) {
            star = rating;
        }
    }

    private void commit() {
        String content = evaluationContentEt.getText().toString();
        if (star < 1) {
            CustomToast.customToast(false, "请为" + name + "打星", this);
            return;
        } else{
            StyledDialogTools.showLoding(this);
            StringBuffer sb = new StringBuffer();
            List<String> list = new ArrayList<>();
            if (tjkBtn.isSelected())
                list.add(tjkBtn.getText().toString());
            if (tdhBtn.isSelected())
                list.add(tdhBtn.getText().toString());
            if (fwhBtn.isSelected())
                list.add(fwhBtn.getText().toString());
            if (wchBtn.isSelected())
                list.add(wchBtn.getText().toString());
            if (yscBtn.isSelected())
                list.add(yscBtn.getText().toString());
            if (tdbBtn.isSelected())
                list.add(tdbBtn.getText().toString());
            if (eyjBtn.isSelected())
                list.add(eyjBtn.getText().toString());
            if (saoBtn.isSelected())
                list.add(saoBtn.getText().toString());

            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        sb.append(list.get(i));
                    } else {
                        sb.append(list.get(i)).append(",");
                    }
                }
            }

            RequestParams params = new RequestParams(Constant.APIURL + "addpingjia");
            params.addParameter("oid", id);
            params.addParameter("uid", AppDbManager.getUserId());
            params.addParameter("star", star);
            params.addParameter("content", content);
            params.addParameter("tags", sb.toString());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    AbsBaseBean absBaseBean = GsonUtil.fromJson(result, AbsBaseBean.class);
                    switch (absBaseBean.getResult()) {
                        case Constant.HTTPSUCCESS:
                            StyledDialogTools.disMissStyleDialog();
                            setResult(0);
                            finish();
                            break;
                        default:
                            StyledDialogTools.disMissStyleDialog();
                            CustomToast.customToast(false, absBaseBean.getMessage(), EvaluationOrderActivity.this);
                            break;
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    StyledDialogTools.disMissStyleDialog();
                    CustomToast.customToast(false, ex.getMessage(), EvaluationOrderActivity.this);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }
}
