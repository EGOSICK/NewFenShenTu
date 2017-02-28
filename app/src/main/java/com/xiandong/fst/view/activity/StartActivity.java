package com.xiandong.fst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.xiandong.fst.R;
import com.xiandong.fst.tools.CustomToast;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.AppUtils;

import org.xutils.view.annotation.ContentView;

import cn.jpush.android.api.JPushInterface;

/**
 * 欢迎界面
 */

@ContentView(R.layout.activity_start)
public class StartActivity extends AbsBaseActivity {
    Context context;

    @Override
    protected void initialize() {
        context = this;
        AppUtils.checkJurisdiction(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToNext();
                } else {
                    goToNext();
                    CustomToast.customToast(true, "如无法正常使用,请重新授予本程序权限", this);
                }
                return;
            }
        }
    }

    private void goToNext() {
        if (AppUtils.isAllowMockLocation(this)) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ss();
            }
        }, 500);
    }

    private void ss() {
        if (AppDbManager.getLastUser() != null) {
            if (AppDbManager.getLastUser().isUserLogIn()) {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(context, LogInActivity.class));
                finish();
            }
        } else {
            startActivity(new Intent(context, LogInActivity.class));
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
