package com.xindeguo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.xindeguo.activity.R;
import com.xindeguo.activity.util.LogUtils;


/**
 * Created by zhaokun on 2016/3/29 0029.
 */
public class TasksCompletedView extends View{
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    //圆环初始的颜色
    private Paint mFirstRingPaint;
    //圆环透明颜色画笔
    private Paint mTransPaint;
    //透明色
    private int mTransColor;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环初始化颜色
    private int mFirstColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private float mProgress;
    //数字进度
    private float mNumber;

    public TasksCompletedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        //半径
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        mFirstColor = typeArray.getColor(R.styleable.TasksCompletedView_firstringColor,0xFFFFD5D5);
        mTransColor = typeArray.getColor(R.styleable.TasksCompletedView_transColor,0xFFFFFFFF);

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mFirstRingPaint = new Paint();
        mFirstRingPaint.setAntiAlias(true);
        mFirstRingPaint.setColor(mFirstColor);
        mFirstRingPaint.setStyle(Paint.Style.STROKE);
        mFirstRingPaint.setStrokeWidth(mStrokeWidth);

        mTransPaint = new Paint();
        mTransPaint.setAntiAlias(true);
        mTransPaint.setColor(mTransColor);
        mTransPaint.setStyle(Paint.Style.STROKE);
        mTransPaint.setStrokeWidth(mStrokeWidth+1);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(mRadius/2.7f);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        RectF oval = new RectF();
        oval.left = (mXCenter - mRingRadius);
        oval.top = (mYCenter - mRingRadius);
        oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);

        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
        canvas.drawArc(oval,-195,210f,false,mFirstRingPaint);
        canvas.drawArc(oval,50,80,false,mTransPaint);

        String txt = mNumber + "%";
        mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
        canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight, mTextPaint);

        if (mProgress > 0 ) {
            RectF ovals = new RectF();
            ovals.left = (mXCenter - mRingRadius);
            ovals.top = (mYCenter - mRingRadius);
            ovals.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            ovals.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(ovals, -195, ((float)mProgress / mTotalProgress) * 210, false, mRingPaint); //

        }
    }

    public void setProgress(float progress,float mNumber) {
        mProgress = progress;
        this.mNumber = mNumber;
        postInvalidate();
    }
}
