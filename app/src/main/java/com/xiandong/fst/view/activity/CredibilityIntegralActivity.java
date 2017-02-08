package com.xiandong.fst.view.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiandong.fst.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2017/1/21.
 */
@ContentView(R.layout.activity_crediblity_integral)
public class CredibilityIntegralActivity extends AbsBaseActivity {
    int startX, startY;
    boolean isup;
    private boolean isShow = false, isCanTouch = true;
    String jifen;
    @ViewInject(R.id.bottomView)
    View bottomView;
    @ViewInject(R.id.upImg)
    ImageView upImg;
    @ViewInject(R.id.titleTitleTv)
    TextView titleTitleTv;
    @ViewInject(R.id.creditScoreTv)
    TextView creditScoreTv;

    @Override
    protected void initialize() {
        titleTitleTv.setText("信誉积分");
        jifen = getIntent().getStringExtra("fen");
        creditScoreTv.setText(jifen);
        bottomView.setY(bottomView.getTop() + (dp2pix(250)));
        setFocusAndAnimation();
    }

    @Event(type = View.OnClickListener.class, value = {R.id.titleBackImg, R.id.upImg, R.id.jiFenXQTv})
    private void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.titleBackImg:
                finish();
                break;
            case R.id.upImg:
                if (isShow) {
                    slideview(0, dp2pix(220), bottomView);
                    setAnimation(view, 180, 360);
                    isShow = false;
                } else {
                    slideview(0, -dp2pix(220), bottomView);
                    setAnimation(view, 0, 180);
                    isShow = true;
                }
                break;
            case R.id.jiFenXQTv:
                startActivity(new Intent(this, CredibilityIntegralDetailsActivity.class));
                break;
        }
    }


    private void setFocusAndAnimation() {
        bottomView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isCanTouch) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = x;
                            startY = y;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (y - startY > 10) {
                                isup = true;
                            }

                            if (y - startY < -10) {
                                isup = false;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (isup) {
                                if (isShow) {
                                    slideview(0, dp2pix(220), bottomView);
                                    setAnimation(upImg, 180, 360);
                                    isShow = false;
                                }
                            } else {
                                if (!isShow) {
                                    slideview(0, -dp2pix(220), bottomView);
                                    setAnimation(upImg, 0, 180);
                                    isShow = true;
                                }
                            }
                            break;
                    }
                }
                return true;
            }
        });
    }


    public void slideview(final float p1, final float p2, final View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, p1, p2);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(500);
        animation.setStartOffset(0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isCanTouch = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = view.getLeft();
                int top = view.getTop() + (int) (p2 - p1);
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top, left + width, top + height);
                isCanTouch = true;
            }
        });
        view.startAnimation(animation);
    }

    /***
     * 设置旋转动画
     *
     * @param v 旋转的view
     * @param a 起始角度
     * @param b 结束角度
     */
    private void setAnimation(View v, int a, int b) {
        RotateAnimation ra = new RotateAnimation(a, b, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true);
        ra.setDuration(300);
        v.startAnimation(ra);
    }

    public int dp2pix(float dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
