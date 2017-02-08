package com.xiandong.fst.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.gastudio.downloadloadding.library.GADownloadingView;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.xiandong.fst.R;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.StyledDialogTools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * 更新管理类
 */
public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
    private HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    private String upUrl = "http://www.fenshentu.com/fenshentu-release.apk";
    private String vsUrl = "http://www.fenshentu.com/app.php/Index/version";
    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private GADownloadingView view;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    view.updateProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(boolean is) {
        Update(is);
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private void Update(final boolean isClick) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int versionCode = getVersionCode(mContext);
                InputStream is = (InputStream) msg.obj;
                ParseXmlService service = new ParseXmlService();
                try {
                    mHashMap = service.parseXml(is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != mHashMap) {
                    int serviceCode = Integer.valueOf(mHashMap.get("version"));
                    // 版本判断
                    if (serviceCode > versionCode) {
                        showNoticeDialog();
                    } else {
                        if (isClick) {
                            CustomToast.customToast(true, mContext.getString(R.string.soft_update_no), mContext);
                        }
                    }
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                //解析网络xml进行更新软件
                URL url = null;
                try {
                    url = new URL(vsUrl);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                conn.setConnectTimeout(5000);
                InputStream is = null;
                try {
                    is = conn.getInputStream();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Message m = new Message();
                m.obj = is;
                handler.sendMessage(m);
            }
        }).start();
    }

    /////////////////////////////////////////////////////////////////////////


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.xiandong.fst", 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        StyledDialogTools.showNoticeDialog(mContext, new MyDialogListener() {
            @Override
            public void onFirst() {
                showDownloadDialog();
            }
            @Override
            public void onSecond() {
                StyledDialogTools.disMissStyleDialog();
            }
        });
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_download, null);
        view = (GADownloadingView) v.findViewById(R.id.ga_downloading);
        Button downBtn = (Button) v.findViewById(R.id.downBtn);
        Button backDownBtn = (Button) v.findViewById(R.id.backDownBtn);
        backDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDownloadDialog.dismiss();
            }
        });
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelUpdate = true;
                mDownloadDialog.dismiss();
            }
        });
        view.performAnimation();
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(v);
        mDownloadDialog = builder.create();
        mDownloadDialog.setCanceledOnTouchOutside(false);
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mHashMap.get("name"));
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}