package com.xiandong.fst.tools;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;

/**
 * 自定义动画
 * Created by dell on 2016/8/15.
 */
public class CostomAnimation {
    public final static String WIDTH = "width";
    public final static String HEIGHT = "height";

    /***
     * 缩放动画
     *
     * @param target
     * @param start
     * @param end
     * @param type
     * @param time
     */
    public static ValueAnimator zoomAnimate(final View target, final int start, final int end, final String type, long time) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void
            onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                //这里我偷懒了，不过有现成的干吗不用呢
                //直接调用整型估值器通过比例计算出宽度，然后再设给View

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if (type.equals(WIDTH)) {
                        target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                    } else {
                        target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
                    }
                }
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(time).start();
        return valueAnimator;
    }

    /***
     * 缩放动画(改进)
     *
     * @param v      设置完成前不可点击
     * @param target
     * @param start
     * @param end
     * @param type
     * @param time
     */
    public static void zoomAnimate(final View v, final View target, final int start, final int end, final String type, long time) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void
            onAnimationUpdate(ValueAnimator animator) {

                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();

                // 设置动画进行中不可点击
                if (currentValue > 0 && currentValue < 100) {
                    v.setFocusable(false);
                    v.setClickable(false);
                } else {
                    v.setClickable(true);
                    v.setFocusable(true);
                }

                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                //这里我偷懒了，不过有现成的干吗不用呢
                //直接调用整型估值器通过比例计算出宽度，然后再设给View

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if (type.equals(WIDTH)) {
                        target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                    } else {
                        target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
                    }
                }
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(time).start();
    }


    /***
     * 平移动画
     *
     * @param v
     * @param start
     * @param end
     * @param time
     * @param type
     */
    public static void PanningAnimation(final View v, float start, float end, long time, String type) {
        ObjectAnimator obj = null;
        if (type.equals(WIDTH)) {
            obj = ObjectAnimator.ofFloat(v, "translationX", start, end);
            obj.setDuration(time).start();
        } else {
            obj = ObjectAnimator.ofFloat(v, "translationY", start, end);
            obj.setDuration(time).start();
        }

        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                v.setClickable(false);
                v.setFocusable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setClickable(true);
                v.setFocusable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    public static void PanningTPanning(View v, float start, float end, long time) {
        ObjectAnimator o1 = ObjectAnimator.ofFloat(v, "translationY", start, end).setDuration(time);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(v, "translationY", end, start).setDuration(time);
        o2.setStartDelay(1000);
        AnimatorSet set = new AnimatorSet();
        set.play(o1).before(o2);
        set.start();
    }

    // 动画实际执行
    public static void startPropertyAnim(View v) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", 0f, 360f);

        // 动画的持续时间，执行多久？
        anim.setDuration(1000);

        // 回调监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
            }
        });

        // 正式开始启动执行动画
        anim.start();
    }
}
