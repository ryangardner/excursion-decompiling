/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.net.Uri
 *  android.os.Environment
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package com.github.mikephil.charting.charts;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Chart<T extends ChartData<? extends IDataSet<? extends Entry>>>
extends ViewGroup
implements ChartInterface {
    public static final String LOG_TAG = "MPAndroidChart";
    public static final int PAINT_CENTER_TEXT = 14;
    public static final int PAINT_DESCRIPTION = 11;
    public static final int PAINT_GRID_BACKGROUND = 4;
    public static final int PAINT_HOLE = 13;
    public static final int PAINT_INFO = 7;
    public static final int PAINT_LEGEND_LABEL = 18;
    protected ChartAnimator mAnimator;
    protected ChartTouchListener mChartTouchListener;
    protected T mData = null;
    protected DefaultValueFormatter mDefaultValueFormatter = new DefaultValueFormatter(0);
    protected Paint mDescPaint;
    protected Description mDescription;
    private boolean mDragDecelerationEnabled = true;
    private float mDragDecelerationFrictionCoef = 0.9f;
    protected boolean mDrawMarkers = true;
    private float mExtraBottomOffset = 0.0f;
    private float mExtraLeftOffset = 0.0f;
    private float mExtraRightOffset = 0.0f;
    private float mExtraTopOffset = 0.0f;
    private OnChartGestureListener mGestureListener;
    protected boolean mHighLightPerTapEnabled = true;
    protected IHighlighter mHighlighter;
    protected Highlight[] mIndicesToHighlight;
    protected Paint mInfoPaint;
    protected ArrayList<Runnable> mJobs = new ArrayList();
    protected Legend mLegend;
    protected LegendRenderer mLegendRenderer;
    protected boolean mLogEnabled = false;
    protected IMarker mMarker;
    protected float mMaxHighlightDistance = 0.0f;
    private String mNoDataText = "No chart data available.";
    private boolean mOffsetsCalculated = false;
    protected DataRenderer mRenderer;
    protected OnChartValueSelectedListener mSelectionListener;
    protected boolean mTouchEnabled = true;
    private boolean mUnbind = false;
    protected ViewPortHandler mViewPortHandler = new ViewPortHandler();
    protected XAxis mXAxis;

    public Chart(Context context) {
        super(context);
        this.init();
    }

    public Chart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public Chart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (!(view instanceof ViewGroup)) return;
        int n = 0;
        do {
            ViewGroup viewGroup;
            if (n >= (viewGroup = (ViewGroup)view).getChildCount()) {
                viewGroup.removeAllViews();
                return;
            }
            this.unbindDrawables(viewGroup.getChildAt(n));
            ++n;
        } while (true);
    }

    public void addViewportJob(Runnable runnable2) {
        if (this.mViewPortHandler.hasChartDimens()) {
            this.post(runnable2);
            return;
        }
        this.mJobs.add(runnable2);
    }

    public void animateX(int n) {
        this.mAnimator.animateX(n);
    }

    public void animateX(int n, Easing.EasingFunction easingFunction) {
        this.mAnimator.animateX(n, easingFunction);
    }

    public void animateXY(int n, int n2) {
        this.mAnimator.animateXY(n, n2);
    }

    public void animateXY(int n, int n2, Easing.EasingFunction easingFunction) {
        this.mAnimator.animateXY(n, n2, easingFunction);
    }

    public void animateXY(int n, int n2, Easing.EasingFunction easingFunction, Easing.EasingFunction easingFunction2) {
        this.mAnimator.animateXY(n, n2, easingFunction, easingFunction2);
    }

    public void animateY(int n) {
        this.mAnimator.animateY(n);
    }

    public void animateY(int n, Easing.EasingFunction easingFunction) {
        this.mAnimator.animateY(n, easingFunction);
    }

    protected abstract void calcMinMax();

    protected abstract void calculateOffsets();

    public void clear() {
        this.mData = null;
        this.mOffsetsCalculated = false;
        this.mIndicesToHighlight = null;
        this.mChartTouchListener.setLastHighlighted(null);
        this.invalidate();
    }

    public void clearAllViewportJobs() {
        this.mJobs.clear();
    }

    public void clearValues() {
        ((ChartData)this.mData).clearValues();
        this.invalidate();
    }

    public void disableScroll() {
        ViewParent viewParent = this.getParent();
        if (viewParent == null) return;
        viewParent.requestDisallowInterceptTouchEvent(true);
    }

    protected void drawDescription(Canvas canvas) {
        float f;
        float f2;
        Object object = this.mDescription;
        if (object == null) return;
        if (!((ComponentBase)object).isEnabled()) return;
        object = this.mDescription.getPosition();
        this.mDescPaint.setTypeface(this.mDescription.getTypeface());
        this.mDescPaint.setTextSize(this.mDescription.getTextSize());
        this.mDescPaint.setColor(this.mDescription.getTextColor());
        this.mDescPaint.setTextAlign(this.mDescription.getTextAlign());
        if (object == null) {
            f2 = (float)this.getWidth() - this.mViewPortHandler.offsetRight() - this.mDescription.getXOffset();
            f = (float)this.getHeight() - this.mViewPortHandler.offsetBottom() - this.mDescription.getYOffset();
        } else {
            f2 = ((MPPointF)object).x;
            f = ((MPPointF)object).y;
        }
        canvas.drawText(this.mDescription.getText(), f2, f, this.mDescPaint);
    }

    protected void drawMarkers(Canvas canvas) {
        Object object;
        if (this.mMarker == null) return;
        if (!this.isDrawMarkersEnabled()) return;
        if (!this.valuesToHighlight()) {
            return;
        }
        int n = 0;
        while (n < ((Highlight[])(object = this.mIndicesToHighlight)).length) {
            Highlight highlight = object[n];
            Object[] arrobject = ((ChartData)this.mData).getDataSetByIndex(highlight.getDataSetIndex());
            object = ((ChartData)this.mData).getEntryForHighlight(this.mIndicesToHighlight[n]);
            int n2 = arrobject.getEntryIndex((Highlight[])object);
            if (object != null && !((float)n2 > (float)arrobject.getEntryCount() * this.mAnimator.getPhaseX()) && this.mViewPortHandler.isInBounds((float)(arrobject = this.getMarkerPosition(highlight))[0], (float)arrobject[1])) {
                this.mMarker.refreshContent((Entry)object, highlight);
                this.mMarker.draw(canvas, (float)arrobject[0], (float)arrobject[1]);
            }
            ++n;
        }
    }

    public void enableScroll() {
        ViewParent viewParent = this.getParent();
        if (viewParent == null) return;
        viewParent.requestDisallowInterceptTouchEvent(false);
    }

    public ChartAnimator getAnimator() {
        return this.mAnimator;
    }

    public MPPointF getCenter() {
        return MPPointF.getInstance((float)this.getWidth() / 2.0f, (float)this.getHeight() / 2.0f);
    }

    @Override
    public MPPointF getCenterOfView() {
        return this.getCenter();
    }

    @Override
    public MPPointF getCenterOffsets() {
        return this.mViewPortHandler.getContentCenter();
    }

    public Bitmap getChartBitmap() {
        Bitmap bitmap = Bitmap.createBitmap((int)this.getWidth(), (int)this.getHeight(), (Bitmap.Config)Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable2 = this.getBackground();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        this.draw(canvas);
        return bitmap;
    }

    @Override
    public RectF getContentRect() {
        return this.mViewPortHandler.getContentRect();
    }

    public T getData() {
        return this.mData;
    }

    @Override
    public ValueFormatter getDefaultValueFormatter() {
        return this.mDefaultValueFormatter;
    }

    public Description getDescription() {
        return this.mDescription;
    }

    public float getDragDecelerationFrictionCoef() {
        return this.mDragDecelerationFrictionCoef;
    }

    public float getExtraBottomOffset() {
        return this.mExtraBottomOffset;
    }

    public float getExtraLeftOffset() {
        return this.mExtraLeftOffset;
    }

    public float getExtraRightOffset() {
        return this.mExtraRightOffset;
    }

    public float getExtraTopOffset() {
        return this.mExtraTopOffset;
    }

    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData != null) return this.getHighlighter().getHighlight(f, f2);
        Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
        return null;
    }

    public Highlight[] getHighlighted() {
        return this.mIndicesToHighlight;
    }

    public IHighlighter getHighlighter() {
        return this.mHighlighter;
    }

    public ArrayList<Runnable> getJobs() {
        return this.mJobs;
    }

    public Legend getLegend() {
        return this.mLegend;
    }

    public LegendRenderer getLegendRenderer() {
        return this.mLegendRenderer;
    }

    public IMarker getMarker() {
        return this.mMarker;
    }

    protected float[] getMarkerPosition(Highlight highlight) {
        return new float[]{highlight.getDrawX(), highlight.getDrawY()};
    }

    @Deprecated
    public IMarker getMarkerView() {
        return this.getMarker();
    }

    @Override
    public float getMaxHighlightDistance() {
        return this.mMaxHighlightDistance;
    }

    public OnChartGestureListener getOnChartGestureListener() {
        return this.mGestureListener;
    }

    public ChartTouchListener getOnTouchListener() {
        return this.mChartTouchListener;
    }

    public Paint getPaint(int n) {
        if (n == 7) return this.mInfoPaint;
        if (n == 11) return this.mDescPaint;
        return null;
    }

    public DataRenderer getRenderer() {
        return this.mRenderer;
    }

    public ViewPortHandler getViewPortHandler() {
        return this.mViewPortHandler;
    }

    public XAxis getXAxis() {
        return this.mXAxis;
    }

    @Override
    public float getXChartMax() {
        return this.mXAxis.mAxisMaximum;
    }

    @Override
    public float getXChartMin() {
        return this.mXAxis.mAxisMinimum;
    }

    @Override
    public float getXRange() {
        return this.mXAxis.mAxisRange;
    }

    public float getYMax() {
        return ((ChartData)this.mData).getYMax();
    }

    public float getYMin() {
        return ((ChartData)this.mData).getYMin();
    }

    public void highlightValue(float f, float f2, int n) {
        this.highlightValue(f, f2, n, true);
    }

    public void highlightValue(float f, float f2, int n, boolean bl) {
        if (n >= 0 && n < ((ChartData)this.mData).getDataSetCount()) {
            this.highlightValue(new Highlight(f, f2, n), bl);
            return;
        }
        this.highlightValue(null, bl);
    }

    public void highlightValue(float f, int n) {
        this.highlightValue(f, n, true);
    }

    public void highlightValue(float f, int n, boolean bl) {
        this.highlightValue(f, Float.NaN, n, bl);
    }

    public void highlightValue(Highlight highlight) {
        this.highlightValue(highlight, false);
    }

    public void highlightValue(Highlight highlight, boolean bl) {
        Object object = null;
        if (highlight == null) {
            this.mIndicesToHighlight = null;
        } else {
            if (this.mLogEnabled) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Highlighted: ");
                ((StringBuilder)object).append(highlight.toString());
                Log.i((String)"MPAndroidChart", (String)((StringBuilder)object).toString());
            }
            if ((object = ((ChartData)this.mData).getEntryForHighlight(highlight)) == null) {
                this.mIndicesToHighlight = null;
                highlight = null;
            } else {
                this.mIndicesToHighlight = new Highlight[]{highlight};
            }
        }
        this.setLastHighlighted(this.mIndicesToHighlight);
        if (bl && this.mSelectionListener != null) {
            if (!this.valuesToHighlight()) {
                this.mSelectionListener.onNothingSelected();
            } else {
                this.mSelectionListener.onValueSelected((Entry)object, highlight);
            }
        }
        this.invalidate();
    }

    public void highlightValues(Highlight[] arrhighlight) {
        this.mIndicesToHighlight = arrhighlight;
        this.setLastHighlighted(arrhighlight);
        this.invalidate();
    }

    protected void init() {
        Paint paint;
        this.setWillNotDraw(false);
        this.mAnimator = new ChartAnimator(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Chart.this.postInvalidate();
            }
        });
        Utils.init(this.getContext());
        this.mMaxHighlightDistance = Utils.convertDpToPixel(500.0f);
        this.mDescription = new Description();
        this.mLegend = new Legend();
        this.mLegendRenderer = new LegendRenderer(this.mViewPortHandler, this.mLegend);
        this.mXAxis = new XAxis();
        this.mDescPaint = new Paint(1);
        this.mInfoPaint = paint = new Paint(1);
        paint.setColor(Color.rgb((int)247, (int)189, (int)51));
        this.mInfoPaint.setTextAlign(Paint.Align.CENTER);
        this.mInfoPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        if (!this.mLogEnabled) return;
        Log.i((String)"", (String)"Chart.init()");
    }

    public boolean isDragDecelerationEnabled() {
        return this.mDragDecelerationEnabled;
    }

    @Deprecated
    public boolean isDrawMarkerViewsEnabled() {
        return this.isDrawMarkersEnabled();
    }

    public boolean isDrawMarkersEnabled() {
        return this.mDrawMarkers;
    }

    public boolean isEmpty() {
        T t = this.mData;
        if (t == null) {
            return true;
        }
        if (((ChartData)t).getEntryCount() > 0) return false;
        return true;
    }

    public boolean isHighlightPerTapEnabled() {
        return this.mHighLightPerTapEnabled;
    }

    public boolean isLogEnabled() {
        return this.mLogEnabled;
    }

    public abstract void notifyDataSetChanged();

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!this.mUnbind) return;
        this.unbindDrawables((View)this);
    }

    protected void onDraw(Canvas canvas) {
        if (this.mData == null) {
            if (!(TextUtils.isEmpty((CharSequence)this.mNoDataText) ^ true)) return;
            MPPointF mPPointF = this.getCenter();
            canvas.drawText(this.mNoDataText, mPPointF.x, mPPointF.y, this.mInfoPaint);
            return;
        }
        if (this.mOffsetsCalculated) return;
        this.calculateOffsets();
        this.mOffsetsCalculated = true;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = 0;
        while (n5 < this.getChildCount()) {
            this.getChildAt(n5).layout(n, n2, n3, n4);
            ++n5;
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = (int)Utils.convertDpToPixel(50.0f);
        this.setMeasuredDimension(Math.max(this.getSuggestedMinimumWidth(), Chart.resolveSize((int)n3, (int)n)), Math.max(this.getSuggestedMinimumHeight(), Chart.resolveSize((int)n3, (int)n2)));
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        Object object;
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)"OnSizeChanged()");
        }
        if (n > 0 && n2 > 0 && n < 10000 && n2 < 10000) {
            if (this.mLogEnabled) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Setting chart dimens, width: ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(", height: ");
                ((StringBuilder)object).append(n2);
                Log.i((String)"MPAndroidChart", (String)((StringBuilder)object).toString());
            }
            this.mViewPortHandler.setChartDimens(n, n2);
        } else if (this.mLogEnabled) {
            object = new StringBuilder();
            ((StringBuilder)object).append("*Avoiding* setting chart dimens! width: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", height: ");
            ((StringBuilder)object).append(n2);
            Log.w((String)"MPAndroidChart", (String)((StringBuilder)object).toString());
        }
        this.notifyDataSetChanged();
        object = this.mJobs.iterator();
        do {
            if (!object.hasNext()) {
                this.mJobs.clear();
                super.onSizeChanged(n, n2, n3, n4);
                return;
            }
            this.post((Runnable)object.next());
        } while (true);
    }

    public void removeViewportJob(Runnable runnable2) {
        this.mJobs.remove(runnable2);
    }

    public boolean saveToGallery(String string2) {
        return this.saveToGallery(string2, "", "MPAndroidChart-Library Save", Bitmap.CompressFormat.PNG, 40);
    }

    public boolean saveToGallery(String string2, int n) {
        return this.saveToGallery(string2, "", "MPAndroidChart-Library Save", Bitmap.CompressFormat.PNG, n);
    }

    public boolean saveToGallery(String charSequence, String charSequence2, String string2, Bitmap.CompressFormat compressFormat, int n) {
        long l;
        int n2;
        block15 : {
            block14 : {
                if (n < 0) break block14;
                n2 = n;
                if (n <= 100) break block15;
            }
            n2 = 50;
        }
        long l2 = System.currentTimeMillis();
        Object object = Environment.getExternalStorageDirectory();
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append(((File)object).getAbsolutePath());
        ((StringBuilder)object2).append("/DCIM/");
        ((StringBuilder)object2).append((String)charSequence2);
        File file = new File(((StringBuilder)object2).toString());
        boolean bl = file.exists();
        boolean bl2 = false;
        if (!bl && !file.mkdirs()) {
            return false;
        }
        n = 2.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        object = "image/png";
        if (n != 1) {
            if (n != 2) {
                charSequence2 = charSequence;
                if (!((String)charSequence).endsWith(".jpg")) {
                    charSequence2 = charSequence;
                    if (!((String)charSequence).endsWith(".jpeg")) {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        ((StringBuilder)charSequence2).append(".jpg");
                        charSequence2 = ((StringBuilder)charSequence2).toString();
                    }
                }
                object2 = "image/jpeg";
            } else {
                charSequence2 = charSequence;
                if (!((String)charSequence).endsWith(".webp")) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(".webp");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                object2 = "image/webp";
            }
        } else {
            object2 = object;
            charSequence2 = charSequence;
            if (!((String)charSequence).endsWith(".png")) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(".png");
                charSequence2 = ((StringBuilder)charSequence2).toString();
                object2 = object;
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(file.getAbsolutePath());
        ((StringBuilder)charSequence).append("/");
        ((StringBuilder)charSequence).append((String)charSequence2);
        charSequence = ((StringBuilder)charSequence).toString();
        try {
            object = new FileOutputStream((String)charSequence);
            this.getChartBitmap().compress(compressFormat, n2, (OutputStream)object);
            ((OutputStream)object).flush();
            ((FileOutputStream)object).close();
            l = new File((String)charSequence).length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
        compressFormat = new ContentValues(8);
        compressFormat.put("title", (String)charSequence2);
        compressFormat.put("_display_name", (String)charSequence2);
        compressFormat.put("date_added", Long.valueOf(l2));
        compressFormat.put("mime_type", (String)object2);
        compressFormat.put("description", string2);
        compressFormat.put("orientation", Integer.valueOf(0));
        compressFormat.put("_data", (String)charSequence);
        compressFormat.put("_size", Long.valueOf(l));
        if (this.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (ContentValues)compressFormat) == null) return bl2;
        return true;
    }

    public boolean saveToPath(String string2, String string3) {
        Bitmap bitmap = this.getChartBitmap();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append(string3);
            stringBuilder.append("/");
            stringBuilder.append(string2);
            stringBuilder.append(".png");
            FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, (OutputStream)fileOutputStream);
            ((OutputStream)fileOutputStream).close();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void setData(T object) {
        this.mData = object;
        this.mOffsetsCalculated = false;
        if (object == null) {
            return;
        }
        this.setupDefaultFormatter(((ChartData)object).getYMin(), ((ChartData)object).getYMax());
        Iterator<T> iterator2 = ((ChartData)this.mData).getDataSets().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.notifyDataSetChanged();
                if (!this.mLogEnabled) return;
                Log.i((String)"MPAndroidChart", (String)"Data is set.");
                return;
            }
            object = (IDataSet)iterator2.next();
            if (!object.needsFormatter() && object.getValueFormatter() != this.mDefaultValueFormatter) continue;
            object.setValueFormatter(this.mDefaultValueFormatter);
        } while (true);
    }

    public void setDescription(Description description) {
        this.mDescription = description;
    }

    public void setDragDecelerationEnabled(boolean bl) {
        this.mDragDecelerationEnabled = bl;
    }

    public void setDragDecelerationFrictionCoef(float f) {
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        f = f2;
        if (f2 >= 1.0f) {
            f = 0.999f;
        }
        this.mDragDecelerationFrictionCoef = f;
    }

    @Deprecated
    public void setDrawMarkerViews(boolean bl) {
        this.setDrawMarkers(bl);
    }

    public void setDrawMarkers(boolean bl) {
        this.mDrawMarkers = bl;
    }

    public void setExtraBottomOffset(float f) {
        this.mExtraBottomOffset = Utils.convertDpToPixel(f);
    }

    public void setExtraLeftOffset(float f) {
        this.mExtraLeftOffset = Utils.convertDpToPixel(f);
    }

    public void setExtraOffsets(float f, float f2, float f3, float f4) {
        this.setExtraLeftOffset(f);
        this.setExtraTopOffset(f2);
        this.setExtraRightOffset(f3);
        this.setExtraBottomOffset(f4);
    }

    public void setExtraRightOffset(float f) {
        this.mExtraRightOffset = Utils.convertDpToPixel(f);
    }

    public void setExtraTopOffset(float f) {
        this.mExtraTopOffset = Utils.convertDpToPixel(f);
    }

    public void setHardwareAccelerationEnabled(boolean bl) {
        if (bl) {
            this.setLayerType(2, null);
            return;
        }
        this.setLayerType(1, null);
    }

    public void setHighlightPerTapEnabled(boolean bl) {
        this.mHighLightPerTapEnabled = bl;
    }

    public void setHighlighter(ChartHighlighter chartHighlighter) {
        this.mHighlighter = chartHighlighter;
    }

    protected void setLastHighlighted(Highlight[] arrhighlight) {
        if (arrhighlight != null && arrhighlight.length > 0 && arrhighlight[0] != null) {
            this.mChartTouchListener.setLastHighlighted(arrhighlight[0]);
            return;
        }
        this.mChartTouchListener.setLastHighlighted(null);
    }

    public void setLogEnabled(boolean bl) {
        this.mLogEnabled = bl;
    }

    public void setMarker(IMarker iMarker) {
        this.mMarker = iMarker;
    }

    @Deprecated
    public void setMarkerView(IMarker iMarker) {
        this.setMarker(iMarker);
    }

    public void setMaxHighlightDistance(float f) {
        this.mMaxHighlightDistance = Utils.convertDpToPixel(f);
    }

    public void setNoDataText(String string2) {
        this.mNoDataText = string2;
    }

    public void setNoDataTextColor(int n) {
        this.mInfoPaint.setColor(n);
    }

    public void setNoDataTextTypeface(Typeface typeface) {
        this.mInfoPaint.setTypeface(typeface);
    }

    public void setOnChartGestureListener(OnChartGestureListener onChartGestureListener) {
        this.mGestureListener = onChartGestureListener;
    }

    public void setOnChartValueSelectedListener(OnChartValueSelectedListener onChartValueSelectedListener) {
        this.mSelectionListener = onChartValueSelectedListener;
    }

    public void setOnTouchListener(ChartTouchListener chartTouchListener) {
        this.mChartTouchListener = chartTouchListener;
    }

    public void setPaint(Paint paint, int n) {
        if (n == 7) {
            this.mInfoPaint = paint;
            return;
        }
        if (n != 11) {
            return;
        }
        this.mDescPaint = paint;
    }

    public void setRenderer(DataRenderer dataRenderer) {
        if (dataRenderer == null) return;
        this.mRenderer = dataRenderer;
    }

    public void setTouchEnabled(boolean bl) {
        this.mTouchEnabled = bl;
    }

    public void setUnbindEnabled(boolean bl) {
        this.mUnbind = bl;
    }

    protected void setupDefaultFormatter(float f, float f2) {
        T t = this.mData;
        f = t != null && ((ChartData)t).getEntryCount() >= 2 ? Math.abs(f2 - f) : Math.max(Math.abs(f), Math.abs(f2));
        int n = Utils.getDecimals(f);
        this.mDefaultValueFormatter.setup(n);
    }

    public boolean valuesToHighlight() {
        boolean bl;
        Highlight[] arrhighlight = this.mIndicesToHighlight;
        boolean bl2 = bl = false;
        if (arrhighlight == null) return bl2;
        bl2 = bl;
        if (arrhighlight.length <= 0) return bl2;
        if (arrhighlight[0] != null) return true;
        return bl;
    }

}

