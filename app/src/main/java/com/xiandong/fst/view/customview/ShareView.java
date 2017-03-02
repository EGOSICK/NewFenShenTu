package com.xiandong.fst.view.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.xiandong.fst.R;

public class ShareView extends View {
    Canvas canvas;
    Paint paint;
    String price = "12312312312312312312312312";
    String address = "addressaddressaddressaddressaddressaddressaddressaddressaddressaddress";
    Rect bounds;

    public ShareView(Context context) {
        super(context);
        canvas = new Canvas();
        paint = new Paint();
        bounds = new Rect();
    }

    public ShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.share_red_pack)
                .copy(Bitmap.Config.ARGB_8888, true);;
        Bitmap.createBitmap(b,0,0,getMeasuredWidth(),getMeasuredHeight());
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        canvas.drawBitmap(b, 0, 0, paint);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setTextSize(30);
        paint.getTextBounds(price, 0, price.length(), bounds);
        canvas.drawText(price, getMeasuredWidth() / 2 - bounds.width() / 2, getMeasuredHeight() / 7, paint);
        paint.getTextBounds(address, 0, address.length(), bounds);
        canvas.drawText(address, getMeasuredWidth() / 2 - bounds.width() / 2, getMeasuredHeight() / 9, paint);
    }
}
