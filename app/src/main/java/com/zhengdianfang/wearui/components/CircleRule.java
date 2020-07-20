package com.zhengdianfang.wearui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.zhengdianfang.wearui.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CircleRule extends View {

    private Paint circlePaint = new Paint();
    private Paint textPaint   = new Paint();
    private List<RuleData> data = new ArrayList();

    private static final float DEFAULT_START_ANGLE   = 140F;
    private static final float DEFAULT_SWEEP_ANGLE   = 261F;
    private static final float DEFAULT_SPACING_ANGLE = 1F;

    private float strokeWidth;
    private float startAngle;
    private float sweepAngle;
    private float spacingAngle;
    private int strokeColor;
    private float textSize;
    private float margin;

    public CircleRule(Context context) {
        super(context);
        init(null);
    }

    public CircleRule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleRule(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void updateData(List<RuleData> newData) {
        this.data = newData;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rect = new RectF(this.margin, this.margin, getWidth() - this.margin, getHeight() - this.margin);
        drawRule(canvas, rect);
        drawText(canvas, rect);
        drawPointer(canvas, rect);
    }

    private void drawPointer(Canvas canvas, RectF rect) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.wear_triangle_pointer);
        drawable.setBounds(new Rect(100, 100, 132, 132));
        drawable.draw(canvas);
    }

    private void drawText(@NotNull Canvas canvas, RectF rect) {
        canvas.save();
        int count = this.data.size() - 1;
        float average = sweepAngle / count;
        float start = startAngle - average / 2;
        for (int i = 0; i < this.data.size(); i++) {
            Path path = new Path();
            path.addArc(rect, start + average * i, average);
            canvas.drawTextOnPath(this.data.get(i)
                                           .getTitle(), path, 0, -this.margin / 2, this.textPaint);
        }
        canvas.restore();
    }

    private void drawRule(@NotNull Canvas canvas, RectF rect) {
        canvas.save();
        float start = startAngle;
        int count = this.data.size() - 1;
        float average = sweepAngle / count;
        for (int i = 0; i < count; i++) {
            canvas.drawArc(rect, start, average - this.spacingAngle, false, circlePaint);
            start += average;
        }
        canvas.restore();
    }

    private void initCirclePaint() {
        this.circlePaint.setAntiAlias(true);
        this.circlePaint.setStyle(Paint.Style.STROKE);
        this.circlePaint.setStrokeWidth(this.strokeWidth);
        this.circlePaint.setColor(this.strokeColor);
    }

    private void initTextPaint() {
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setColor(Color.WHITE);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void init(@Nullable AttributeSet attrs) {
        readStyleableFields(attrs);
        initCirclePaint();
        initTextPaint();
    }

    private void readStyleableFields(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.wear_rule);
            this.strokeWidth = typedArray.getDimension(R.styleable.wear_rule_stroke_width,
                    getContext().getResources()
                                .getDimension(R.dimen.circle_rule_default_stroke_width));
            this.startAngle = typedArray.getFloat(R.styleable.wear_rule_start_angle, DEFAULT_START_ANGLE);
            this.sweepAngle = typedArray.getFloat(R.styleable.wear_rule_sweep_angle, DEFAULT_SWEEP_ANGLE);
            this.spacingAngle = typedArray.getFloat(R.styleable.wear_rule_spacing_angle, DEFAULT_SPACING_ANGLE);
            this.strokeColor = typedArray.getColor(R.styleable.wear_rule_stroke_color,
                    ContextCompat.getColor(getContext(), R.color.wear_rule_default_stroke_color));
            this.textSize = typedArray.getDimensionPixelSize(R.styleable.wear_rule_text_size,
                    getContext().getResources()
                                .getDimensionPixelOffset(R.dimen.circle_rule_default_text_size));
            this.margin = this.textSize * 2;
            typedArray.recycle();
        }
    }
}
