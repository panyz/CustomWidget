package com.panyz.customwidget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Project: CustomWidget
 * @Package: com.panyz.customwidget.widget
 * @Description: 弧形流量条控件
 * @Autor: panyz
 * @Date: 2017年07月10日 16:09
 */
public class ArcBarView extends View {

    private Paint arcPaint1;//顶部弧形画笔
    private Paint arcPaint2;//底部弧形画笔
    private Paint textPaint1;//大Size文字画笔
    private Paint textPaint2;//小Size文字画笔

    private int radius;//弧形的半径
    private float result = 0;//滑过弧形的弧度
    private float mStartAngle = 150;//弧形起点弧度
    private float mEndAngle = 240;//弧形结束弧度

    private RectF rectF;//弧形外接矩形
    private Rect textRect;//包裹文字的矩形

    private String totalText = "可用流量";
    private String usedText = "已用流量";
    private String totalData = "--M";
    private String usedData = "--M";


    public ArcBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ArcBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcBarView(Context context) {
        super(context);
        init();
    }

    private void init() {
        arcPaint1 = new Paint();
        arcPaint2 = new Paint();
        initArcPaint(arcPaint1);
        initArcPaint(arcPaint2);
        arcPaint1.setColor(Color.parseColor("#7CCD7C"));
        arcPaint2.setColor(Color.parseColor("#33000000"));

        textPaint1 = new Paint();
        textPaint2 = new Paint();
        initTextPaint(textPaint1);
        initTextPaint(textPaint2);
        textPaint1.setTextSize(40f);
        textPaint2.setTextSize(50f);
    }

    /**
     * 初始化文字画笔的样式
     * @param textPaint
     */
    private void initTextPaint(Paint textPaint) {
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
    }

    /**
     * 初始化弧形画笔的样式
     * @param arcPaint
     */
    private void initArcPaint(Paint arcPaint) {
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultSize = 600;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultSize,defaultSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultSize,heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize,defaultSize);
        } else {
            setMeasuredDimension(widthSpecSize,heightSpecSize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF();
        textRect = new Rect();
        //获取弧形的半径大小
        radius = (int) (Math.min(getWidth(), getHeight()) / 2 - arcPaint1.getStrokeWidth()) -100;
        rectF.left = getWidth() / 2 - radius;
        rectF.top = getHeight() / 2 - radius;
        rectF.right = getWidth() / 2 + radius;
        rectF.bottom = getHeight() / 2 + radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制弧形
        canvas.drawArc(rectF,mStartAngle,mEndAngle,false,arcPaint2);
        canvas.save();
        canvas.drawArc(rectF, mStartAngle, getResult(), false, arcPaint1);
        canvas.restore();

        //获取绘制文字的宽高给textRect
        textPaint1.getTextBounds(totalText,0,totalText.length(),textRect);
        //绘制文字
        canvas.drawText(totalText, rectF.centerX() - textRect.width() / 2, rectF.centerY() + textRect.height() - 100, textPaint1);
        canvas.drawText(totalData, rectF.centerX() - textRect.width() / 2 - 25, rectF.centerY() + textRect.height() / 2, textPaint2);
        canvas.drawText(usedText, rectF.centerX() - textRect.width() / 2 - 80, rectF.centerY() + textRect.height() + 120, textPaint1);
        canvas.drawText(usedData, rectF.centerX() - textRect.width() / 2 + 80, rectF.centerY() + textRect.height() + 120, textPaint1);

    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, (result * mEndAngle) / handleData());
        animator.setTarget(this);
        animator.setDuration(3000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAnim(animation);
            }
        });
    }

    private void startAnim(ValueAnimator animation) {
        this.result = (float) animation.getAnimatedValue();
        invalidate();
    }

    public String getTotalData() {
        return totalData;
    }

    public void setTotalData(String totalData) {
        this.totalData = totalData;
    }

    public String getUsedData() {
        return usedData;
    }

    public void setUsedData(String usedData) {
        this.usedData = usedData;
    }

    /**
     * 获取可用流量的数值
     * @return
     */
    private float handleData() {
        String text = getTotalData();
        float data = Float.parseFloat(text.substring(0, text.length() - 2));
        return data;
    }

}

