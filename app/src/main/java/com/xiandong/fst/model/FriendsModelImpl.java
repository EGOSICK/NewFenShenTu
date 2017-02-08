package com.xiandong.fst.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.model.bean.AbsBaseBean;
import com.xiandong.fst.model.bean.FriendsBean;
import com.xiandong.fst.presenter.FriendsPresenter;
import com.xiandong.fst.tools.BaiDuTools.MarkMapTools;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.GsonUtil;
import com.xiandong.fst.utils.StringUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by dell on 2017/01/16
 */
public class FriendsModelImpl implements FriendsModel {

    @Override
    public void getFriends(final FriendsPresenter presenter) {
        RequestParams params = new RequestParams(Constant.APIURL + "showuser");
        params.addBodyParameter("uid", AppDbManager.getLastUser().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AbsBaseBean bean = GsonUtil.fromJson(result, AbsBaseBean.class);
                switch (bean.getResult()) {
                    case 1:
                        FriendsBean friendsBean = GsonUtil.fromJson(result, FriendsBean.class);
                        presenter.getFriendsSuccess(friendsBean);
                        break;
                    default:
                        presenter.getFriendsFails(bean.getMessage());
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                presenter.getFriendsFails(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void showFriendsPosition(Context context, List<FriendsBean.FriendEntity> list,
                                    FriendsPresenter presenter) {
        if (list != null && list.size() > 0) {
            for (FriendsBean.FriendEntity friend : list) {
                if (MarkMapTools.friends != null && MarkMapTools.friends.size() > 0) {
                    if (MarkMapTools.isHaveFriend(friend.getId())){
                        if (StringUtil.isEquals(MarkMapTools.friends.get(friend.getId()) , friend.getPosition())){
                            return;
                        }else {
                            show(friend , context ,presenter);
                        }
                    }else {
                        show(friend , context ,presenter);
                    }
                }else {
                    show(friend , context ,presenter);
                }
            }
        }
    }

    private void show(FriendsBean.FriendEntity friend, Context context, FriendsPresenter presenter){
        String[] position = friend.getPosition().split(";");
        LatLng latLng = new LatLng(Double.parseDouble(position[0]),
                Double.parseDouble(position[1]));
        MarkBitmap markBitmap = new MarkBitmap(context, friend.getId(), latLng, presenter);
        markBitmap.execute(friend.getImg());
        MarkMapTools.friends.put(friend.getId() , friend.getPosition());
    }


    /***
     * url转换为bitmap
     *
     * @param urlpath 网址
     * @return bitmap
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
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
    class MarkBitmap extends AsyncTask<String, String, Bitmap> {
        private LatLng latLng;
        private String id;
        private Context context;
        private FriendsPresenter presenter;

        MarkBitmap(Context context, String id, LatLng ll, FriendsPresenter presenter) {
            this.latLng = ll;
            this.id = id;
            this.context = context;
            this.presenter = presenter;
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
            return getBitMBitmap(params[0]);
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
                    .icon(bd).title(id);
            // 定义mark出现动画  生长动画
            option.animateType(MarkerOptions.MarkerAnimateType.drop);
            //在地图上添加Marker，并显示
//            Marker m = (Marker) mBaiduMap.addOverlay(option);
            presenter.friendsImgSuccess(option);
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