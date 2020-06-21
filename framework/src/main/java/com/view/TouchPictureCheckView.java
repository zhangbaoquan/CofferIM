package com.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.framework.R;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * @author：张宝全
 * @date：2020/6/19
 * @Description：图片验证View
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class TouchPictureCheckView extends View {

    private static final String TAG = "TouchPictureCheckView_tag";

    /**
     * 背景
     */
    private Paint mBgPaint;
    private Bitmap mBgBitmap;

    /**
     * 空白块
     */
    private Paint mNullPaint;
    private Bitmap mNullBitmap;

    /**
     * 移动方块
     */
    private Paint mMovePaint;
    private Bitmap mMoveBitmap;

    /**
     * 当前View的宽度、高度
     */
    private int mWidth;
    private int mHeight;

    /**
     * 空白图的大小
     */
    private int mCardSize;

    /**
     * 滑块的起始便宜距离
     */
    private int mMoveX = 200;

    /**
     * 滑动校验的误差偏移值
     */
    private int mErrorX = 10;

    /**
     * 空白图的 x、y 坐标，方便后面校验使用
     */
    private int mNullX = 0;
    private int mNullY = 0;


    /**
     * 判断首次按下的地方是否在滑块的内部
     */
    private boolean mIsPressMoveInner;


    public TouchPictureCheckView(Context context) {
        super(context);
        init();
    }

    public TouchPictureCheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 初始化画笔
        mBgPaint = new Paint();
        mNullPaint = new Paint();
        mMovePaint = new Paint();
    }

    private ResultListener mListener;

    public void setViewResultListener(ResultListener viewResultListener) {
        this.mListener = viewResultListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景图片
        drawBg(canvas);
        drawNullBg(canvas);
        drawMoveBg(canvas);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas) {
        if (mBgBitmap == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg);
            // 创建一个宽高和View 一样的空Bitmap(避免原图尺寸过小，导致后面截图时出现计算异常)
            mBgBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            // 将图片绘制到空的Bitmap上
            Canvas canvas1 = new Canvas(mBgBitmap);
            canvas1.drawBitmap(bitmap, null, new Rect(0, 0, mWidth, mHeight), mBgPaint);
        }
        canvas.drawBitmap(mBgBitmap, null, new Rect(0, 0, mWidth, mHeight), mBgPaint);
    }

    /**
     * 绘制空白方块的背景
     * 将空白方块绘制到x = mWidth * 2 / 3,y = mHeight / 2 - nullBgHeight / 2 (居中)
     */
    private void drawNullBg(Canvas canvas) {
        if (mNullBitmap == null) {
            mNullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_null_card);
        }
        mCardSize = mNullBitmap.getHeight();
        mNullX = mWidth * 2 / 3;
        mNullY = mHeight / 2 - mCardSize / 2;
        canvas.drawBitmap(mNullBitmap, mNullX, mNullY, mNullPaint);
    }

    /**
     * 绘制移动方块的背景
     */
    private void drawMoveBg(Canvas canvas) {
        if (mMoveBitmap == null) {
            // 从背景图中截取一个在空白图 位置相同 且 大小相同的背景图
            mMoveBitmap = Bitmap.createBitmap(mBgBitmap, mNullX, mNullY, mCardSize, mCardSize);
        }
        // 滑块在移动时，仅 X 坐标改变，Y坐标不变
        canvas.drawBitmap(mMoveBitmap, mMoveX, mNullY, mMovePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Region region = new Region(new Rect(mMoveX, mNullY, mMoveX + mCardSize,
                        mNullY + mCardSize));
                if (region.contains((int) event.getX(), (int) event.getY())) {
                    mIsPressMoveInner = true;
                } else {
                    mIsPressMoveInner = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsPressMoveInner && event.getX() > 0 && event.getX() < mWidth) {
                    mMoveX = (int) event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsPressMoveInner) {
                    // 设置终点空白区域，这里设置了一个误差范围
//                    Region nullRegion = new Region(new Rect(mNullX - mErrorX, mNullY,
//                            mNullX + mCardSize + mErrorX,
//                            mNullY + mCardSize));
//                    if (nullRegion.contains((int) event.getX(), (int) event.getY())) {
                    // 用区域法有个问题，就是如果在滑动时，手指若偏离了滑块区域，就会导致验证失败
                    int posX = (int) event.getX();
                    // 使用这种方式，不去校验Y坐标
                    if (posX > mNullX - mErrorX && posX < mNullX + mCardSize + mErrorX){
                        // 验证通过
                        mListener.onResult();
                    } else {
                        // 验证失败，将滑块的位置还原
                        mMoveX = 200;
                        invalidate();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    public interface ResultListener {
        void onResult();
    }
}