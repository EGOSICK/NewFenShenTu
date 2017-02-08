package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiandong.fst.R;
import com.xiandong.fst.model.bean.HotPintDetailBean;
import com.xiandong.fst.presenter.HotPintPresenterImpl;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.XCircleImgTools;
import com.xiandong.fst.tools.adapter.ForumListAdapter;
import com.xiandong.fst.utils.StringUtil;
import com.xiandong.fst.utils.TimeUtil;
import com.xiandong.fst.view.HotPintView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/22.
 */
@ContentView(R.layout.activity_rabbit_say_nei)
public class RabbitSayNeiActivity extends AbsBaseActivity implements HotPintView {
    @ViewInject(R.id.forumListView)
    ListView forumListView;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.huiFuEt)
    EditText huiFuEt;
    ForumListAdapter adapter;
    HotPintPresenterImpl presenter;
    HotPintDetailBean bean;
    String position, id;

    @Override
    protected void initialize() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        position = intent.getStringExtra("position");
        adapter = new ForumListAdapter(this);
        forumListView.setAdapter(adapter);
        presenter = new HotPintPresenterImpl(this);
        presenter.getHotPintMsg(id);
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.huiFuBtn})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.huiFuBtn:

                if (StringUtil.isEmpty(huiFuEt.getText().toString().trim()))
                    return;
                else
                    presenter.huiFu(huiFuEt.getText().toString().trim(),
                            bean.getPforum().getId(), position);
                break;
        }
    }

    @Override
    public void getHotPintSuccess(HotPintDetailBean bean) {
        this.bean = bean;

        titleTitleTv.setText("兔子说");

        if (forumListView.getHeaderViewsCount() == 0) {
            View v = LayoutInflater.from(this).inflate(R.layout.item_forum_list, null);
            TextView itemForumTimeTv = (TextView) v.findViewById(R.id.itemForumTimeTv);
            TextView itemForumContentTv = (TextView) v.findViewById(R.id.itemForumContentTv);
            TextView itemForumNameTv = (TextView) v.findViewById(R.id.itemForumNameTv);
            TextView itemForumHFTv = (TextView) v.findViewById(R.id.itemForumHFTv);
            ImageView itemForumImg = (ImageView) v.findViewById(R.id.itemForumImg);
            itemForumContentTv.setText(bean.getPforum().getContent());
            itemForumTimeTv.setText(TimeUtil.convertTimeToFormat(Long.parseLong(bean.getPforum().getTimeline())));
            itemForumNameTv.setText(bean.getPforum().getNicheng());
            itemForumHFTv.setText(bean.getPforum().getCount());
            XCircleImgTools.setCircleImg(itemForumImg, bean.getPforum().getImg());
            forumListView.addHeaderView(v);
        }
        adapter.addData(bean.getForum());
    }

    @Override
    public void fetHotPintFails(String err) {

    }

    @Override
    public void huiFuSuccess() {
        huiFuEt.setText("");
        presenter.getHotPintMsg(id);
    }
}
