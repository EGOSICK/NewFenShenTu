package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.PromoteUserBean;
import com.xiandong.fst.tools.adapter.RecommendAdapter;
import com.xiandong.fst.view.customview.emptyview.HHEmptyView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/6.
 */

@ContentView(R.layout.activity_recommend)
public class RecommendActivity extends AbsBaseActivity {
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.titleBackImg)
    ImageView titleBackImg;
    @ViewInject(R.id.recommendLv)
    ListView recommendLv;
    @ViewInject(R.id.emptyView)
    HHEmptyView emptyView;

    @Override
    protected void initialize() {
        initView();
        initData();
    }

    private void initView() {
        titleTitleTv.setText("直接推荐");
        emptyView.bindView(recommendLv);
        emptyView.setLoadingModel(HHEmptyView.MODEL_DEFAULT);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("promoteUser");
        PromoteUserBean bean = bundle.getParcelable("promoteUser");
        RecommendAdapter adapter = new RecommendAdapter(this);
        recommendLv.setAdapter(adapter);
        adapter.addData(bean.getChild());
        if (bean.getChild() != null && bean.getChild().size() > 0){
            emptyView.success();
        }else {
            emptyView.empty();
            emptyView.setOnBtnClickListener(new HHEmptyView.OnBtnClickListener() {
                @Override
                public void onBtnClick() {
                    emptyView.loading();
                }
            });
        }
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg})
    private void recommendOnclick(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
