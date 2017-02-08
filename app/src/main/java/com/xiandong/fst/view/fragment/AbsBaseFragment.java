package com.xiandong.fst.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiandong.fst.view.activity.MainActivity;
import org.xutils.x;

/**
 * Fragment 基类
 */

public abstract class AbsBaseFragment extends Fragment {
    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        if (context instanceof MainActivity){
            activity = (MainActivity) context;
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    protected abstract void initialize();

    protected MainActivity getMainActivity(){
        return activity;
    }
}
