package com.xiandong.fst.view.customview.codeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View {
  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 100L;
  private static final int OPAQUE = 0xFF;
  private Bitmap resultBitmap;
  private int scannerAlpha;
  private Collection<ResultPoint> possibleResultPoints;
  private Collection<ResultPoint> lastPossibleResultPoints;

  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);
    scannerAlpha = 0;
    possibleResultPoints = new HashSet<ResultPoint>(5);
  }

  @Override
  public void onDraw(Canvas canvas)  {
    Rect frame = null;
    try {
    	frame = CameraManager.get().getFramingRect();
    }catch(Exception ex){
    	ex.printStackTrace();
    	frame = null;
    }
    if (frame == null) {
      return;
    }

    if (resultBitmap == null) {
      postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }
  }

  public void drawViewfinder() {
    resultBitmap = null;
    invalidate();
  }

  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

  public void addPossibleResultPoint(ResultPoint point) {
    possibleResultPoints.add(point);
  }

}
