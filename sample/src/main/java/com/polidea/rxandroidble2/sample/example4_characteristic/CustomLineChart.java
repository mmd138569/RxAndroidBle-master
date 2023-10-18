package com.polidea.rxandroidble2.sample.example4_characteristic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;

import java.util.ArrayList;
import java.util.List;

public class CustomLineChart extends LineChart {
    protected Paint mYAxisSafeZonePaint;
    protected TextPaint textPaint;
    private List<TargetZone> mTargetZones;

    public CustomLineChart(Context context) {
        super(context);
    }

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mYAxisSafeZonePaint = new Paint();
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20 *  getResources().getDisplayMetrics().density);
        mYAxisSafeZonePaint.setStyle(Paint.Style.FILL);
        mTargetZones = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (TargetZone targetZone : mTargetZones) {
            // prepare coordinates
            float[] pts = new float[4];
            pts[1] = targetZone.lowerLimit;
            pts[3] = targetZone.upperLimit;
            mLeftAxisTransformer.pointValuesToPixel(pts);

            // draw
            mYAxisSafeZonePaint.setColor(targetZone.color);
            canvas.drawRect(mViewPortHandler.contentLeft(), pts[1], mViewPortHandler.contentRight(),
                    pts[3], mYAxisSafeZonePaint);
            textPaint.setColor(Color.DKGRAY);
            textPaint.setTextSize(30);
            canvas.drawText(targetZone.text,getCenter().x,pts[1] + 50,textPaint);
        }
        super.onDraw(canvas);
    }

    public void addTargetZone(TargetZone targetZone){
        mTargetZones.add(targetZone);
    }

    public List<TargetZone> getTargetZones(){
        return mTargetZones;
    }

    public void clearTargetZones(){
        mTargetZones = new ArrayList<>();
    }

    public static class TargetZone {
        public final int color;
        public final float lowerLimit;
        public final float upperLimit;
        public final String text;

        public TargetZone(int color, float lowerLimit, float upperLimit,String text) {
            this.color = color;
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
            this.text = text;
        }
    }
}