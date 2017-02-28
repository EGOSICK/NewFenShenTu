package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.OrderListBean;
import com.xiandong.fst.presenter.FriendsPresenter;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.adapter.RabbitOrdersVpAdapter;
import com.xiandong.fst.utils.BitmapUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


@ContentView(R.layout.fragment_rabbit_order)
public class RabbitOrderFragment extends AbsBaseFragment {
    @ViewInject(R.id.rabbitOrdersVp)
    ViewPager rabbitOrdersVp;
    RabbitOrdersVpAdapter adapter;
    List<OrderListBean.OrderEntity> list;
    BaiduMap baiDuMap;
    @ViewInject(R.id.tiShiTv)
    TextView tiShiTv;


    public static RabbitOrderFragment getInstance(List<OrderListBean.OrderEntity> list,
                                                  BaiduMap baiDuMap) {
        RabbitOrderFragment fragment = new RabbitOrderFragment();
        fragment.getOrdersList(list);
        fragment.setBaiDuMap(baiDuMap);
        return fragment;
    }

    private void setBaiDuMap(BaiduMap baiDuMap){
        this.baiDuMap = baiDuMap;
    }
    private void getOrdersList(List<OrderListBean.OrderEntity> list) {
        this.list = list;
    }

    @Event(type = View.OnClickListener.class , value = {R.id.orderTiShiBtn})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.orderTiShiBtn:
                if (tiShiTv.getVisibility() == View.VISIBLE){
                    tiShiTv.setVisibility(View.INVISIBLE);
                }else {
                    tiShiTv.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected void initialize() {
        adapter = new RabbitOrdersVpAdapter(getContext(), getFragmentManager().beginTransaction());
        rabbitOrdersVp.setAdapter(adapter);
        rabbitOrdersVp.setCurrentItem(100);
        adapter.addData(list);
        rabbitOrdersVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (adapter.getSelectId(position) != null) {
                    Marker marker = MarkMapTools.getOrders().get(adapter.getSelectId(position));
                    if (marker == null)
                        return;
                    marker.setToTop();
                    getMainActivity().positioning(marker.getPosition());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        initMarker();
    }

    private void initMarker() {
        if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
            String[] position = list.get(i).getPosition().split(";");
            LatLng latLng = new LatLng(Double.valueOf(position[0]), Double.valueOf(position[1]));
            String title = MarkMapTools.setTitle(Constant.MarkerType.ORDER, list.get(i).getId());
            if (MarkMapTools.getOrders() != null && MarkMapTools.getOrders().size() > 0) {
                if (MarkMapTools.isHaveOrder(list.get(i).getId())) {
                    if (MarkMapTools.getOrders().get(list.get(i).getId()).getPosition() == latLng) {
                        return;
                    } else {
                        MarkMapTools.getOrders().get(list.get(i).getId()).setPosition(latLng);
                    }
                } else {
                    MarkBitmap markBitmap = new MarkBitmap(getContext(),list.get(i).getId(),title,latLng);
                    markBitmap.execute(list.get(i).getImg());
                }
            } else {
                MarkBitmap markBitmap = new MarkBitmap(getContext(),list.get(i).getId(),title,latLng);
                markBitmap.execute(list.get(i).getImg());
            }
        }

    }


    /**
     * 自定义mark  使用异步任务先设置到布局再转换成bitmap
     */
    public Bitmap view2Bitmap(Context context, Bitmap b) {
        View addViewContent = LayoutInflater.from(context).inflate(R.layout.mark_baidu_map, null);
        ImageView im = (ImageView) addViewContent.findViewById(R.id.markImg);
        im.setImageBitmap(b);
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(), addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    /***
     * 使用异步任务进行mark的自定义
     */
    private class MarkBitmap extends AsyncTask<String, String, Bitmap> {
        private LatLng latLng;
        private String id;
        private String title;
        private Context context;

        MarkBitmap(Context context, String id, String title , LatLng ll) {
            this.latLng = ll;
            this.id = id;
            this.title = title;
            this.context = context;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            return BitmapUtils.getBitMBitmap(params[0]);
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Bitmap b = view2Bitmap(context, bitmap);
            BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(b);
            MarkerOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bd).title(title);
            // 定义mark出现动画  生长动画
            option.animateType(MarkerOptions.MarkerAnimateType.none);
            //在地图上添加Marker，并显示
            Marker m = (Marker) baiDuMap.addOverlay(option);
            MarkMapTools.getOrders().put(id, m);
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
