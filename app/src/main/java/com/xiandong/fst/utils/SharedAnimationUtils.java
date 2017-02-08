package com.xiandong.fst.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 共享动画
 */

public class SharedAnimationUtils {

    public static void setUpWindowTransition(Activity context) {

        final ChangeBounds ts = new ChangeBounds();
        ts.setPathMotion(new ArcMotion());

        ts.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                ts.removeListener(this);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        context.getWindow().setSharedElementEnterTransition(ts);
    }

    public static Pair<View, String>[] createSafeTransitionParticipants(
            @NonNull Activity activity, boolean includeStatusBar, View imageview) {
        View decor = activity.getWindow().getDecorView();
        View statusBar = null;
        if (includeStatusBar) {
            statusBar = decor.findViewById(android.R.id.statusBarBackground);
        }
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);
        List<Pair> participants = new ArrayList<>(3);
        addNonNullViewToTransitionParticipants(imageview, participants);
        return participants.toArray(new Pair[participants.size()]);
    }

    private static void addNonNullViewToTransitionParticipants(View view, List<Pair> participants) {
        if (view == null) {
            return;
        }
        participants.add(new Pair<>(view, view.getTransitionName()));
    }
}
