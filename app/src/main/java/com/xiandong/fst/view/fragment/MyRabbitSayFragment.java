package com.xiandong.fst.view.fragment;

import android.widget.ListView;

import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.MyRabbitSayBean;
import com.xiandong.fst.tools.adapter.MyRabbitSayAdapter;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by dell on 2017/1/24.
 */
@ContentView(R.layout.fragment_my_rabbit_say)
public class MyRabbitSayFragment extends AbsBaseFragment {
    private int position;

    public static MyRabbitSayFragment getInstance(int position) {
        MyRabbitSayFragment fragment = new MyRabbitSayFragment();
        fragment.choosePager(position);
        return fragment;
    }

    private void choosePager(int position) {
        this.position = position;
    }

    @ViewInject(R.id.myRabbitSayLv)
    private ListView myRabbitSayLv;

    MyRabbitSayAdapter adapter;

    @Override
    protected void initialize() {
        adapter = new MyRabbitSayAdapter(getContext(),position);
        myRabbitSayLv.setAdapter(adapter);
        switch (position) {
            case 0:
                getSay("h");
                break;
            case 1:
                getSay("f");
                break;
        }
    }

    private void getSay(String flag) {
        RequestParams params = new RequestParams(Constant.APIURL + "myforum");
        params.addBodyParameter("uid", AppDbManager.getUserId());
        params.addBodyParameter("flag", flag);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case Constant.HTTPSUCCESS:
                        MyRabbitSayBean sayBean = GsonUtil.fromJson(result, MyRabbitSayBean.class);
                        success(sayBean);
                        break;
                    default:
                        err(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                err(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void err(String err) {

    }

    private void success(MyRabbitSayBean bean) {
        adapter.addData(bean.getForum());
    }
}
