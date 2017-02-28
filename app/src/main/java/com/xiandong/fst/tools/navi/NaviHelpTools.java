package com.xiandong.fst.tools.navi;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import com.xiandong.fst.view.activity.PathGuideActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dell on 2017/2/9.
 */

public class NaviHelpTools {
    Activity context;
    private boolean isCanNavi = false;
    private static String mSdcardPath = null;
    private static final String APP_FOLDER_NAME = "FST";
    public static final String ROUTE_PLAN_NODE = "routePlanNode";



    public NaviHelpTools(Activity context) {
        this.context = context;
        // 初始化语音导航
        if (initSdcardPath()) {//先获得SD卡的路径
            isCanNavi = true;
        }
    }

    private boolean initSdcardPath() {
        if (initDir()) {
            initNaviPath();
            return true;
        }
        return false;
    }

    private boolean initDir() {//创建一个文件夹用于保存在路线导航过程中语音导航语音文件的缓存，防止用户再次开启同样的导航直接从缓存中读取即可
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            mSdcardPath = Environment.getExternalStorageDirectory().toString();
        } else {
            mSdcardPath = null;
        }
        if (mSdcardPath == null) {
            return false;
        }
        File file = new File(mSdcardPath, APP_FOLDER_NAME);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void initNaviPath() {//初始化导航路线的导航引擎
        BaiduNaviManager.getInstance().init(context, mSdcardPath,
                context.getApplicationInfo().packageName,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {}

                    public void initSuccess() {}

                    public void initStart() {}

                    public void initFailed() {}
                }, null /*mTTSCallback*/);
    }


    public void startNavi(LatLng latLng, String p, String ad) {
        if (isCanNavi) {
            StartInfo sInfo = new StartInfo();
            EndInfo eInfo = new EndInfo();
            eInfo.setDesname(ad);
            eInfo.setLatitude(Double.valueOf(p.split(";")[0]));
            eInfo.setLongtiude(Double.valueOf(p.split(";")[1]));
            sInfo.setDesname("我的位置");
            sInfo.setLatitude(latLng.latitude);
            sInfo.setLongtiude(latLng.longitude);
            startNavi(sInfo, eInfo);
        } else {
            CustomToast.customToast(false, "导航初始化失败, 请稍后再试", context);
        }
    }

    private void startNavi(StartInfo sInfo, EndInfo eInfo) {
        if (sInfo != null && eInfo != null) {
            //如果起点和终点信息都不为空的时候开启路线规划得到最佳路径途径的所有节点信息
            initBNRoutePlan(sInfo, eInfo);
        }
    }

    private void initBNRoutePlan(StartInfo mySInfo, EndInfo myEndInfo) {
        BNRoutePlanNode startNode = new BNRoutePlanNode(mySInfo.getLongtiude(), mySInfo.getLatitude(), mySInfo.getDesname(), null, BNRoutePlanNode.CoordinateType.BD09LL);//根据得到的起点的信息创建起点节点
        BNRoutePlanNode endNode = new BNRoutePlanNode(myEndInfo.getLongtiude(), myEndInfo.getLatitude(), myEndInfo.getDesname(), null, BNRoutePlanNode.CoordinateType.BD09LL);//根据得到的终点的信息创建终点节点
        ArrayList<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
        list.add(startNode);//将起点和终点加入节点集合中
        list.add(endNode);
        BaiduNaviManager.getInstance().launchNavigator(context,
                list, 1, true, new MyRoutePlanListener(list));
    }

    private class MyRoutePlanListener implements BaiduNaviManager.RoutePlanListener {//路线规划监听器接口类
        private ArrayList<BNRoutePlanNode> mList = null;

        MyRoutePlanListener(ArrayList<BNRoutePlanNode> list) {
            mList = list;
        }

        @Override
        public void onJumpToNavigator() {
            Intent intent = new Intent(context, PathGuideActivity.class);
            intent.putExtra(ROUTE_PLAN_NODE, mList);//将得到所有的节点集合传入到导航的Activity中去
            context.startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {

        }
    }

}
