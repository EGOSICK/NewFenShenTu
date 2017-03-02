package com.xiandong.fst.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiandong.fst.R;
import com.xiandong.fst.application.Constant;
import com.xiandong.fst.utils.ViewUtils;
import com.xiandong.fst.utils.wxpayutils.Util;
import com.xiandong.fst.view.customview.ShareView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/2/7.
 */

public class WechatShareManager {
    private static final int THUMB_SIZE = 150;

    public static final int WECHAT_SHARE_WAY_TEXT = 1;   //文字
    public static final int WECHAT_SHARE_WAY_PICTURE = 2; //图片
    public static final int WECHAT_SHARE_WAY_WEBPAGE = 3;  //链接
    public static final int WECHAT_SHARE_WAY_VIDEO = 4; //视频
    public static final int WECHAT_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  //会话
    public static final int WECHAT_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline; //朋友圈

    private static WechatShareManager mInstance;
    private ShareContent mShareContentText, mShareContentPicture, mShareContentWebpag, mShareContentVideo;
    private IWXAPI mWXApi;
    private Context mContext;
    private LayoutInflater inflater;


    public interface ShareInterface {
        void shareSuccess();

        void shareFails();
    }

    private List<ShareInterface> list = new ArrayList<>();

    public void registerListener(ShareInterface anInterface) {
        list.add(anInterface);
    }

    public void shareSuccess() {
        for (ShareInterface s : list) {
            s.shareSuccess();
        }
    }

    public void shareFails() {
        for (ShareInterface s : list) {
            s.shareFails();
        }
    }

    private WechatShareManager(Context context) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        //初始化数据
        //初始化微信分享代码
        initWechatShare(context);
    }

    /**
     * 获取WeixinShareManager实例
     * 非线程安全，请在UI线程中操作
     *
     * @return
     */
    public static WechatShareManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WechatShareManager(context);
        }
        return mInstance;
    }

    private void initWechatShare(Context context) {
        if (mWXApi == null) {
            mWXApi = WXAPIFactory.createWXAPI(context, Constant.WX_APPID, true);
        }
        mWXApi.registerApp(Constant.WX_APPID);
    }

    /**
     * 通过微信分享
     *
     * @param shareContent 分享的方式（文本、图片、链接）
     * @param shareType    分享的类型（朋友圈，会话）
     */
    public void shareByWebchat(ShareContent shareContent, int shareType, String money, String address) {
        switch (shareContent.getShareWay()) {
            case WECHAT_SHARE_WAY_TEXT:
                shareText(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_PICTURE:
                sharePicture(shareContent, shareType, money, address);
                break;
            case WECHAT_SHARE_WAY_WEBPAGE:
                shareWebPage(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_VIDEO:
                shareVideo(shareContent, shareType);
                break;
        }
    }

    private abstract class ShareContent {
        protected abstract int getShareWay();

        protected abstract String getContent();

        protected abstract String getTitle();

        protected abstract String getURL();

        protected abstract int getPictureResource();
    }

    /**
     * 设置分享文字的内容
     *
     * @author chengcj1
     */
    public class ShareContentText extends ShareContent {
        private String content;

        /**
         * 构造分享文字类
         *
         * @param content 分享的文字内容
         */
        public ShareContentText(String content) {
            this.content = content;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_TEXT;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }

        @Override
        protected int getPictureResource() {
            return -1;
        }
    }

    /*
     * 获取文本分享对象
     */
    public ShareContent getShareContentText(String content) {
        if (mShareContentText == null) {
            mShareContentText = new ShareContentText(content);
        }
        return (ShareContentText) mShareContentText;
    }

    /**
     * 设置分享图片的内容
     *
     * @author chengcj1
     */
    public class ShareContentPicture extends ShareContent {
        private int pictureResource;

        public ShareContentPicture(int pictureResource) {
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_PICTURE;
        }

        @Override
        protected int getPictureResource() {
            return pictureResource;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }
    }

    /*
     * 获取图片分享对象
     */
    public ShareContent getShareContentPicture(int pictureResource) {
        if (mShareContentPicture == null) {
            mShareContentPicture = new ShareContentPicture(pictureResource);
        }
        return mShareContentPicture;
    }

    /**
     * 设置分享链接的内容
     *
     * @author chengcj1
     */
    public class ShareContentWebpage extends ShareContent {
        private String title;
        private String content;
        private String url;
        private int pictureResource;

        public ShareContentWebpage(String title, String content, String url, int pictureResource) {
            this.title = title;
            this.content = content;
            this.url = url;
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_WEBPAGE;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return title;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected int getPictureResource() {
            return pictureResource;
        }
    }

    /*
     * 获取网页分享对象
     */
    public ShareContent getShareContentWebpag(String title, String content, String url, int pictureResource) {
        if (mShareContentWebpag == null) {
            mShareContentWebpag = new ShareContentWebpage(title, content, url, pictureResource);
        }
        return (ShareContentWebpage) mShareContentWebpag;
    }

    /**
     * 设置分享视频的内容
     *
     * @author chengcj1
     */
    public class ShareContentVideo extends ShareContent {
        private String url;

        public ShareContentVideo(String url) {
            this.url = url;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_VIDEO;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected int getPictureResource() {
            return -1;
        }
    }

    /*
     * 获取视频分享内容
     */
    public ShareContent getShareContentVideo(String url) {
        if (mShareContentVideo == null) {
            mShareContentVideo = new ShareContentVideo(url);
        }
        return (ShareContentVideo) mShareContentVideo;
    }

    /*
     * 分享文字
     */
    private void shareText(ShareContent shareContent, int shareType) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("textshare");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /*
     * 分享图片
     */
    private void sharePicture(ShareContent shareContent, int shareType, String money, String address) {


        //加载xml布局文件
//        LayoutInflater factory = LayoutInflater.from(context);
//        View view = inflater.inflate(shareContent.getPictureResource(), null);
//
//        int[] wah = new int[]{480 , 800};//ViewUtils.getViewWAndH(view);
//
//
//        TextView tv = (TextView) view.findViewById(R.id.shareRedPacketMoneyTv);
//        tv.setText("有个" + money + "元的大红包");
////        tv.setTop();
////        tv.setTextSize(wah[0]/30);
//        RelativeLayout.LayoutParams linearParam =(RelativeLayout.LayoutParams)
//                tv.getLayoutParams(); //取控件textView当前的布局参数
//        linearParam.height = wah[1]/20;// 控件的高强制设成20
//        tv.setLayoutParams(linearParam); //使设置好的布局参数应用到控件
//
//        TextView at = (TextView) view.findViewById(R.id.shareRedPacketAddressTv);
//        at.setText(address);
////        at.setTextSize(wah[0] / 30);
//        at.setPadding(0, wah[1] / 11, 0, 0);
//
//        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams)
//                at.getLayoutParams(); //取控件textView当前的布局参数
//        linearParams.height = wah[1]/20;// 控件的高强制设成20
//        at.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
//
//
////        at.setTop(wah[1]/8);
//        //启用绘图缓存
//        view.setDrawingCacheEnabled(true);
//        //调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
//        view.measure(View.MeasureSpec.makeMeasureSpec(wah[0], View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(wah[1], View.MeasureSpec.UNSPECIFIED));
//        //这个方法也非常重要，设置布局的尺寸和位置
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        //获得绘图缓存中的Bitmap
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();

//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
//        view.setDrawingCacheEnabled(true);
//        Bitmap bitmap = view.getDrawingCache();

//        Bitmap bitmap = getViewBitmap(view,0,0);
//        Bitmap bitmap = view2Bitmap(view, shareContent.getPictureResource(), money, address);


//        Bitmap bmp=BitmapFactory.decodeResource(r, R.drawable.icon);
//        Bitmap //b //= Bitmap.createBitmap(1000, 200, Bitmap.Config.ARGB_8888);

//        Resources r = mContext.getResources();
//        InputStream is = r.openRawResource(R.mipmap.share_red_pack);
//        BitmapDrawable bd = new BitmapDrawable(is);
//        Bitmap b = bd.getBitmap();
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
//                R.mipmap.share_red_pack).copy(Bitmap.Config.ARGB_8888, true);  ;
//        Bitmap bitmap = Bitmap.createBitmap(b, 0, 0, 200, 1000);
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/CONSOLA.TTF");
//        paint.setTypeface(typeface);
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(40);
//        paint.setTextAlign(Paint.Align.CENTER);
//
//        String price = "有一个"+money+"元的大红包";
//        Rect bounds = new Rect();
//        paint.getTextBounds(price, 0, price.length(), bounds);
//
//
//        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
//        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
//        canvas.drawText(testString, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);
//        canvas.drawText(price, 100, 100, paint);
//        canvas.drawText(address , 100 , 200 ,paint);
//
//
//        canvas.drawText(price, getMeasuredWidth()/2 - bounds.width()/2,
//                getMeasuredHeight()/2 + bounds.height()/2, paint);
//        Bitmap bitmap = drawTextAtBitmap(BitmapUtils.getBitFromRes(
//                context, shareContent.getPictureResource()),address ,
//                0 ,0);//= screenShot(view);

//        ShareWXView shareWXView = new ShareWXView(mContext ,address,money);
//
//                //启用绘图缓存
//        shareWXView.setDrawingCacheEnabled(true);
//        //调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
//        shareWXView.measure(View.MeasureSpec.makeMeasureSpec(480, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY));
//        //这个方法也非常重要，设置布局的尺寸和位置
//        shareWXView.layout(0, 0, shareWXView.getMeasuredWidth(), shareWXView.getMeasuredHeight());
//
//        Bitmap bitmap = shareWXView.getDrawingCache();

//        ShareView view = new ShareView(mContext);
//        启用绘图缓存
//        view.setDrawingCacheEnabled(true);
        //调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY));
        //这个方法也非常重要，设置布局的尺寸和位置
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //获得绘图缓存中的Bitmap
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();

//        Bitmap bitmap = view2Bitmap(view);

//        WXShareTools.sharePic(bitmap, 1, mContext);
        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(),
                R.mipmap.share_red_pack).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap bitmap //= getBitmapByView(view , wah);
        = getShareingBitmap(25 ,b,address,"有个" + money + "元的大红包");

        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);  //设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imgshareappdata");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /*
     * 分享链接
     */
    private void shareWebPage(ShareContent shareContent, int shareType) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareContent.getURL();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();

        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
        if (thumb == null) {
            Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
        } else {
            msg.thumbData = Util.bmpToByteArray(thumb, true);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /*
     * 分享视频
     */
    private void shareVideo(ShareContent shareContent, int shareType) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = shareContent.getURL();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//		BitmapFactory.decodeStream(new URL(video.videoUrl).openStream());
        /**
         * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。
         * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。
         */
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
        thumb.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public Bitmap view2Bitmap(View view) {  //, int res, String money, String address
//        View view = LayoutInflater.from(context).inflate(res, null);


        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }


    /**
     * 图片上画文字
     *
     * @param bitmap
     * @param text   文字内容
     * @param textX  文字X坐标
     * @param textY  文字Y坐标
     * @return Bitmap
     */
    private Bitmap drawTextAtBitmap(Bitmap bitmap, String text, float textX, float textY) {
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newbit);

        Paint paint = new Paint();

        // 在原始位置0，0插入原图
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setColor(mContext.getResources().getColor(R.color.text_black));
        paint.setTextSize(17);

        // 在原图指定位置写上字
        canvas.drawText(text, textX, textY, paint);

        canvas.save(Canvas.ALL_SAVE_FLAG);

        // 存储
        canvas.restore();

        return newbit;
    }


    /**
     * 把View绘制到Bitmap上
     *
     * @param comBitmap 需要绘制的View
     * @param width     该View的宽度
     * @param height    该View的高度
     * @return 返回Bitmap对象
     */
    public Bitmap getViewBitmap(View comBitmap, int width, int height) {
        Bitmap bitmap = null;
        if (comBitmap != null) {
            comBitmap.clearFocus();
            comBitmap.setPressed(false);

            boolean willNotCache = comBitmap.willNotCacheDrawing();
            comBitmap.setWillNotCacheDrawing(false);

            // Reset the drawing cache background color to fully transparent
            // for the duration of this operation
            int color = comBitmap.getDrawingCacheBackgroundColor();
            comBitmap.setDrawingCacheBackgroundColor(0);
            float alpha = comBitmap.getAlpha();
            comBitmap.setAlpha(1.0f);

            if (color != 0) {
                comBitmap.destroyDrawingCache();
            }

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            comBitmap.measure(widthSpec, heightSpec);
            comBitmap.layout(0, 0, width, height);

            comBitmap.buildDrawingCache();
            Bitmap cacheBitmap = comBitmap.getDrawingCache();
            if (cacheBitmap == null) {
                Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + comBitmap + ")",
                        new RuntimeException());
                return null;
            }
            bitmap = Bitmap.createBitmap(cacheBitmap);
            // Restore the view
            comBitmap.setAlpha(alpha);
            comBitmap.destroyDrawingCache();
            comBitmap.setWillNotCacheDrawing(willNotCache);
            comBitmap.setDrawingCacheBackgroundColor(color);
        }
        return bitmap;
    }


    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("Folder", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public Bitmap screenShot(View view) {
        if (null == view) {
            throw new IllegalArgumentException("parameter can't be null.");
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     */
    public Bitmap getBitmapByView(View view, int[] wAh) {
        Bitmap imageBm;
//        int h = 0;
        // 获取scrollview实际高度
//        for (int i = 0; i < scrollView.getChildCount(); i++) {
//            h += scrollView.getChildAt(i).getHeight();
//        }
//        int[] wAh = ViewUtils.getViewWAndH(view);
        // 创建对应大小的bitmap
        imageBm = Bitmap.createBitmap(wAh[0], wAh[1],
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imageBm);

        view.draw(canvas);

        return imageBm;
    }


    // 拍摄所得的图片为imageBitmap
    private Bitmap getShareingBitmap(int textSize , Bitmap imageBitmap,String address,String money) {
        Bitmap.Config config = imageBitmap.getConfig();
        int sourceBitmapHeight = imageBitmap.getHeight();
        int sourceBitmapWidth = imageBitmap.getWidth();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // 画笔颜色
        TextPaint textpaint = new TextPaint(paint);
        textpaint.setTextSize(textSize); // 文字大小
        textpaint.setAntiAlias(true); // 抗锯齿

        StaticLayout title_layout = new StaticLayout(address, textpaint,
                sourceBitmapWidth , Layout.Alignment.ALIGN_CENTER, 1f, 1f, true);
        StaticLayout desc_layout = new StaticLayout(money, textpaint,
                sourceBitmapWidth, Layout.Alignment.ALIGN_CENTER, 1f, 1f, true);
//        StaticLayout phone_layout = new StaticLayout("联系电话："+phone.getText().toString(), textpaint,
//                sourceBitmapWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 1f, true);
        Bitmap share_bitmap = Bitmap.createBitmap(sourceBitmapWidth, sourceBitmapHeight,// +
                       // title_layout.getHeight() + desc_layout.getHeight() ,//+ phone_layout.getHeight(),
                config);
        Canvas canvas = new Canvas(share_bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(imageBitmap, 0, 0, paint); // 绘制图片
        canvas.translate(0, sourceBitmapHeight/11);
        title_layout.draw(canvas);

        canvas.translate(0, title_layout.getHeight());
//        phone_layout.draw(canvas);

//        canvas.translate(0, phone_layout.getHeight());
        desc_layout.draw(canvas);
        return share_bitmap;
    }
}
