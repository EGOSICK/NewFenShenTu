package com.xiandong.fst.view.activity;

import android.view.View;
import android.widget.TextView;

import com.xiandong.fst.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/21.
 */
@ContentView(R.layout.activity_evaluation_order)
public class EvaluationOrderActivity extends AbsBaseActivity{
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;


    @Override
    protected void initialize() {
        titleTitleTv.setText("订单评价");
    }

    @Event(type = View.OnClickListener.class , value = {R.id.titleBackImg})
    private void onClickListener(View view){
        switch (view.getId()){
            case R.id.titleBackImg:
                finish();
                break;
        }
    }
}
