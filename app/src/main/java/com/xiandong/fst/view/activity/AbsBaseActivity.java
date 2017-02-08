package com.xiandong.fst.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by dell on 2016/12/19.
 */

public abstract class AbsBaseActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initialize();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initialize();
    }


    protected abstract void initialize();



}
