package com.xiandong.fst.utils;

/**
 * [Android实例] 二维码Demo,包含生成二维码和扫描二维码,支持从相册选取
 * http://www.eoeandroid.com/thread-594415-1-6.html?_dsign=efc098f0
 * 
 * 直接从图片中对二维码编码解
 * http://www.ailab.cn/article/2015092454243.html
 * 
 * 
 * 1）生成二维码并且加logo=========>generateBitmap();addLogo();generate();
 * 2）图片旋转=========>readPictureDegree();rotaingImageView();
 * 3）高级模糊  =========>fastblur();
 
 * 4)listview悬浮头
 * 
 * 5）扫描二维码，本地二维码识别
 * Android的二维码功能实现以及长按识别二维码
 * http://www.2cto.com/kf/201603/495847.html
 * 
 * 
 * Zxing图片识别 从相册选二维码图片解析总结
 * http://blog.csdn.net/aaawqqq/article/details/24880209
 * 
 * 6）微信登录
 * 微信授权登陆接入第三方App（步骤总结）Android
 * http://blog.csdn.net/qq247890212/article/details/40822481
 * 
 * Android App实现第三方微信登录  
 * http://songyuanlin1101.blog.163.com/blog/static/31126387201410209445648/
 * 528375e5b25b9adb82c7ec933ae19e5c
 * 7）侧滑 DrawerLayout
 * http://blog.csdn.net/wwj_748/article/details/41355459
 * http://blog.csdn.net/lmj623565791/article/details/39185641
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Code2AndPic {
	
	public static final Drawable grey_drawable(Context context, int iret){
		Drawable mDrawable = context.getResources().getDrawable(iret);
		//Make this drawable mutable.  
		//A mutable drawable is guaranteed to not share its state with any other drawable.  
		mDrawable.mutate();  
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0.5f);  
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
		mDrawable.setColorFilter(cf); 	
		return mDrawable;
	}
 
	
	/**
	 * 图片变灰{类似不在线}
	 * @param bitmap
	 * @return
	 */
	public static final Bitmap grey(Bitmap bitmap) {
		 int width = bitmap.getWidth();
		 int height = bitmap.getHeight();    
		 Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		 Canvas canvas = new Canvas(faceIconGreyBitmap);
		 Paint paint = new Paint();
		 ColorMatrix colorMatrix = new ColorMatrix();
		 colorMatrix.setSaturation(0);
		 ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		 paint.setColorFilter(colorMatrixFilter);
		 canvas.drawBitmap(bitmap, 0, 0, paint);
		 return faceIconGreyBitmap;
	}	
	/**
	 * 生成二维码图片
	 * @param content
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap generateBitmap(String content, int width, int height) {
	    QRCodeWriter qrCodeWriter = new QRCodeWriter();  
	    Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
	    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
	    try {  
	        BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);  
	        int[] pixels = new int[width * height];  
	        for (int i = 0; i < height; i++) {  
	            for (int j = 0; j < width; j++) {  
	                if (encode.get(j, i)) {  
	                    pixels[i * width + j] = 0x00000000;  
	                } else {  
	                    pixels[i * width + j] = 0xffffffff;  
	                }  
	            }  
	        }  
	        return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
	    } catch (WriterException e) {  
	        e.printStackTrace();  
	    }  
	    return null;  
	}  
	
	/**
	 * 二维码中心添加Logo
	 */
	private static Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
	    int qrBitmapWidth = qrBitmap.getWidth();  
	    int qrBitmapHeight = qrBitmap.getHeight();  
	    int logoBitmapWidth = logoBitmap.getWidth();  
	    int logoBitmapHeight = logoBitmap.getHeight();  
	    Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(blankBitmap);
	    canvas.drawBitmap(qrBitmap, 0, 0, null);  
	    canvas.save(Canvas.ALL_SAVE_FLAG);
	    float scaleSize = 1.0f;  
	    while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {  
	        scaleSize *= 2;  
	    }  
	    float sx = 1.0f / scaleSize;  
	    canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);  
	    canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);  
	    canvas.restore();  
	    return blankBitmap;  
	}  
	
	/**
	 * 显示二维码
	 * @param view
	 */
	public static void generate(Context context, String code2, ImageView view, int iwidth, int iheight) {
		Bitmap qrBitmap = generateBitmap(code2,iwidth, iheight);
	    //Bitmap logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);  
	    //Bitmap bitmap = addLogo(qrBitmap, logoBitmap);  
		view.setScaleType(ScaleType.FIT_XY);
		view.setImageBitmap(qrBitmap); 	    
	}
	
	 /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
   /*
    * 旋转图片 
    * @param angle 
    * @param bitmap 
    * @return Bitmap 
    */  
   public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
       //旋转图片 动作   
       Matrix matrix = new Matrix();;
       matrix.postRotate(angle);  
       System.out.println("angle2=" + angle);
       // 创建新的图片   
       Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
               bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
       return resizedBitmap;  
   }
   /*
Android高级模糊技术的原理，如下：
·首先我创建了一个空的bitmap，把背景的一部分复制进去，之后我会对这个bitmap进行模糊处理并设置为TextView的背景。
·通过这个bitmap保存Canvas的状态；
·在父布局文件中把Canvas移动到TextView的位置；
·把ImageView的内容绘到bitmap中；
·此时，我们就有了一个和TextView一样大小的bitmap，它包含了ImageView的一部分内容，也就是TextView背后一层布局的内容；
·创建一个Renderscript的实例；
·把bitmap复制一份到Renderscript需要的数据片中；
·创建Renderscript模糊处理的实例；
·设置输入，半径范围然后进行模糊处理；
·把处理后的结果复制回之前的bitmap中；
·好了，我们已经把bitmap惊醒模糊处理了，可以将它设置为TextView背景了；
我最近在做一款App，其中有一个功能需要对图片处理实现毛玻璃的特效，经过一番研究，找到了3中实现方案，其中各有优缺点，如果系统的api在16以上，可以使用系统提供的方法直接处理图片，但是小编认为下边的解决方案是实现效果最好的。
    * @param context
    * @param sentBitmap
    * @param radius
    * @return
    */
   public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int temp = 256 * divsum;
		int dv[] = new int[temp];
		for (i = 0; i < temp; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return (bitmap);
	}
}
/*
Android—ZXing二维码扫描遇到的问题

最近工作中需要开发带有二维码扫描功能的软件（基于开源项目ZXing），遇到的问题记录一下，也希望给大家带来帮助。

1.首先因为扫描要开摄像机所以加权限是一定的，不然后面什么都不能进行

  <uses-permission android:name="android.permission.CAMERA" />

2.设置扫描框的大小：

 在com.zxing.camera包中查找

  private static final int MIN_FRAME_WIDTH = 240;
  private static final int MIN_FRAME_HEIGHT = 240;
  private static final int MAX_FRAME_WIDTH = 580;
  private static final int MAX_FRAME_HEIGHT = 580;

  修改这几个属性值就可以修改扫描框大小。

3.实现二维码的重复扫描

 1.在com.zxing.decoding包中查找restartPreviewAndDecode()方法，本身是private改为public

 2.在扫描二维码的activity中添加重复扫描方法：

 private void continuePreview() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);
        if (handler != null) {
            handler.restartPreviewAndDecode();
        }

 注：有时候直接调用此方法会出现bug：多次扫描过后返回到上一个界面再次进入的时候会出现黑屏。

      解决方法：不直接调用continuePreview()这个方法，在需要二次扫描的时候直接写：

      if (handler != null) {
            handler.restartPreviewAndDecode();
        }
 */
