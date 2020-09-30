/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package com.github.mikephil.charting.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import java.util.List;

public abstract class Utils {
    public static final double DEG2RAD = 0.017453292519943295;
    public static final double DOUBLE_EPSILON = Double.longBitsToDouble(1L);
    public static final float FDEG2RAD = 0.017453292f;
    public static final float FLOAT_EPSILON = Float.intBitsToFloat(1);
    private static final int[] POW_10;
    private static Rect mCalcTextHeightRect;
    private static Rect mCalcTextSizeRect;
    private static ValueFormatter mDefaultValueFormatter;
    private static Rect mDrawTextRectBuffer;
    private static Rect mDrawableBoundsCache;
    private static Paint.FontMetrics mFontMetrics;
    private static Paint.FontMetrics mFontMetricsBuffer;
    private static int mMaximumFlingVelocity = 8000;
    private static DisplayMetrics mMetrics;
    private static int mMinimumFlingVelocity = 50;

    static {
        mCalcTextHeightRect = new Rect();
        mFontMetrics = new Paint.FontMetrics();
        mCalcTextSizeRect = new Rect();
        POW_10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
        mDefaultValueFormatter = Utils.generateDefaultValueFormatter();
        mDrawableBoundsCache = new Rect();
        mDrawTextRectBuffer = new Rect();
        mFontMetricsBuffer = new Paint.FontMetrics();
    }

    public static int calcTextHeight(Paint paint, String string2) {
        Rect rect = mCalcTextHeightRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(string2, 0, string2.length(), rect);
        return rect.height();
    }

    public static FSize calcTextSize(Paint paint, String string2) {
        FSize fSize = FSize.getInstance(0.0f, 0.0f);
        Utils.calcTextSize(paint, string2, fSize);
        return fSize;
    }

    public static void calcTextSize(Paint paint, String string2, FSize fSize) {
        Rect rect = mCalcTextSizeRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(string2, 0, string2.length(), rect);
        fSize.width = rect.width();
        fSize.height = rect.height();
    }

    public static int calcTextWidth(Paint paint, String string2) {
        return (int)paint.measureText(string2);
    }

    public static float convertDpToPixel(float f) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) return f * displayMetrics.density;
        Log.e((String)"MPChartLib-Utils", (String)"Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
        return f;
    }

    public static int[] convertIntegers(List<Integer> list) {
        int[] arrn = new int[list.size()];
        Utils.copyIntegers(list, arrn);
        return arrn;
    }

    public static float convertPixelsToDp(float f) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) return f / displayMetrics.density;
        Log.e((String)"MPChartLib-Utils", (String)"Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertPixelsToDp(...). Otherwise conversion does not take place.");
        return f;
    }

    public static String[] convertStrings(List<String> list) {
        int n = list.size();
        String[] arrstring = new String[n];
        int n2 = 0;
        while (n2 < n) {
            arrstring[n2] = list.get(n2);
            ++n2;
        }
        return arrstring;
    }

    public static void copyIntegers(List<Integer> list, int[] arrn) {
        int n = arrn.length < list.size() ? arrn.length : list.size();
        int n2 = 0;
        while (n2 < n) {
            arrn[n2] = list.get(n2);
            ++n2;
        }
    }

    public static void copyStrings(List<String> list, String[] arrstring) {
        int n = arrstring.length < list.size() ? arrstring.length : list.size();
        int n2 = 0;
        while (n2 < n) {
            arrstring[n2] = list.get(n2);
            ++n2;
        }
    }

    public static void drawImage(Canvas canvas, Drawable drawable2, int n, int n2, int n3, int n4) {
        MPPointF mPPointF = MPPointF.getInstance();
        mPPointF.x = n - n3 / 2;
        mPPointF.y = n2 - n4 / 2;
        drawable2.copyBounds(mDrawableBoundsCache);
        drawable2.setBounds(Utils.mDrawableBoundsCache.left, Utils.mDrawableBoundsCache.top, Utils.mDrawableBoundsCache.left + n3, Utils.mDrawableBoundsCache.top + n3);
        n = canvas.save();
        canvas.translate(mPPointF.x, mPPointF.y);
        drawable2.draw(canvas);
        canvas.restoreToCount(n);
    }

    public static void drawMultilineText(Canvas canvas, StaticLayout staticLayout, float f, float f2, TextPaint textPaint, MPPointF mPPointF, float f3) {
        Paint.Align align;
        block7 : {
            float f4;
            block9 : {
                float f5;
                float f6;
                float f7;
                float f8;
                block8 : {
                    block4 : {
                        float f9;
                        block6 : {
                            block5 : {
                                f4 = textPaint.getFontMetrics(mFontMetricsBuffer);
                                f7 = staticLayout.getWidth();
                                f5 = (float)staticLayout.getLineCount() * f4;
                                f8 = 0.0f - (float)Utils.mDrawTextRectBuffer.left;
                                f6 = f5 + 0.0f;
                                align = textPaint.getTextAlign();
                                textPaint.setTextAlign(Paint.Align.LEFT);
                                if (f3 == 0.0f) break block4;
                                if (mPPointF.x != 0.5f) break block5;
                                f9 = f;
                                f4 = f2;
                                if (mPPointF.y == 0.5f) break block6;
                            }
                            FSize fSize = Utils.getSizeOfRotatedRectangleByDegrees(f7, f5, f3);
                            f9 = f - fSize.width * (mPPointF.x - 0.5f);
                            f4 = f2 - fSize.height * (mPPointF.y - 0.5f);
                            FSize.recycleInstance(fSize);
                        }
                        canvas.save();
                        canvas.translate(f9, f4);
                        canvas.rotate(f3);
                        canvas.translate(f8 - f7 * 0.5f, f6 - f5 * 0.5f);
                        staticLayout.draw(canvas);
                        canvas.restore();
                        break block7;
                    }
                    if (mPPointF.x != 0.0f) break block8;
                    f4 = f8;
                    f3 = f6;
                    if (mPPointF.y == 0.0f) break block9;
                }
                f4 = f8 - f7 * mPPointF.x;
                f3 = f6 - f5 * mPPointF.y;
            }
            canvas.save();
            canvas.translate(f4 + f, f3 + f2);
            staticLayout.draw(canvas);
            canvas.restore();
        }
        textPaint.setTextAlign(align);
    }

    public static void drawMultilineText(Canvas canvas, String string2, float f, float f2, TextPaint textPaint, FSize fSize, MPPointF mPPointF, float f3) {
        Utils.drawMultilineText(canvas, new StaticLayout((CharSequence)string2, 0, string2.length(), textPaint, (int)Math.max(Math.ceil(fSize.width), 1.0), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false), f, f2, textPaint, mPPointF, f3);
    }

    public static void drawXAxisValue(Canvas canvas, String string2, float f, float f2, Paint paint, MPPointF mPPointF, float f3) {
        Paint.Align align;
        block7 : {
            float f4;
            block9 : {
                float f5;
                float f6;
                float f7;
                block8 : {
                    block4 : {
                        float f8;
                        float f9;
                        float f10;
                        block6 : {
                            block5 : {
                                f6 = paint.getFontMetrics(mFontMetricsBuffer);
                                paint.getTextBounds(string2, 0, string2.length(), mDrawTextRectBuffer);
                                f7 = 0.0f - (float)Utils.mDrawTextRectBuffer.left;
                                f5 = -Utils.mFontMetricsBuffer.ascent + 0.0f;
                                align = paint.getTextAlign();
                                paint.setTextAlign(Paint.Align.LEFT);
                                if (f3 == 0.0f) break block4;
                                f9 = mDrawTextRectBuffer.width();
                                if (mPPointF.x != 0.5f) break block5;
                                f10 = f;
                                f8 = f2;
                                if (mPPointF.y == 0.5f) break block6;
                            }
                            FSize fSize = Utils.getSizeOfRotatedRectangleByDegrees(mDrawTextRectBuffer.width(), f6, f3);
                            f10 = f - fSize.width * (mPPointF.x - 0.5f);
                            f8 = f2 - fSize.height * (mPPointF.y - 0.5f);
                            FSize.recycleInstance(fSize);
                        }
                        canvas.save();
                        canvas.translate(f10, f8);
                        canvas.rotate(f3);
                        canvas.drawText(string2, f7 - f9 * 0.5f, f5 - f6 * 0.5f, paint);
                        canvas.restore();
                        break block7;
                    }
                    if (mPPointF.x != 0.0f) break block8;
                    f4 = f7;
                    f3 = f5;
                    if (mPPointF.y == 0.0f) break block9;
                }
                f4 = f7 - (float)mDrawTextRectBuffer.width() * mPPointF.x;
                f3 = f5 - f6 * mPPointF.y;
            }
            canvas.drawText(string2, f4 + f, f3 + f2, paint);
        }
        paint.setTextAlign(align);
    }

    public static String formatNumber(float f, int n, boolean bl) {
        return Utils.formatNumber(f, n, bl, '.');
    }

    public static String formatNumber(float f, int n, boolean bl, char c) {
        boolean bl2;
        char[] arrc = new char[35];
        if (f == 0.0f) {
            return "0";
        }
        int n2 = 0;
        boolean bl3 = f < 1.0f && f > -1.0f;
        if (f < 0.0f) {
            f = -f;
            bl2 = true;
        } else {
            bl2 = false;
        }
        int[] arrn = POW_10;
        int n3 = n > arrn.length ? arrn.length - 1 : n;
        long l = Math.round(f * (float)POW_10[n3]);
        n = 34;
        boolean bl4 = false;
        while (l != 0L || n2 < n3 + 1) {
            int n4;
            block10 : {
                block12 : {
                    block11 : {
                        int n5 = (int)(l % 10L);
                        l /= 10L;
                        n4 = n - 1;
                        arrc[n] = (char)(n5 + 48);
                        if (++n2 == n3) {
                            n = n4 - 1;
                            arrc[n4] = (char)44;
                            ++n2;
                            bl4 = true;
                            continue;
                        }
                        if (!bl || l == 0L || n2 <= n3) break block10;
                        if (!bl4) break block11;
                        if ((n2 - n3) % 4 != 0) break block10;
                        n = n4 - 1;
                        arrc[n4] = c;
                        break block12;
                    }
                    if ((n2 - n3) % 4 != 3) break block10;
                    n = n4 - 1;
                    arrc[n4] = c;
                }
                ++n2;
                continue;
            }
            n = n4;
        }
        n3 = n;
        c = (char)n2;
        if (bl3) {
            arrc[n] = (char)48;
            c = (char)(n2 + 1);
            n3 = n - 1;
        }
        n = c;
        if (bl2) {
            arrc[n3] = (char)45;
            n = c + '\u0001';
        }
        n = 35 - n;
        return String.valueOf(arrc, n, 35 - n);
    }

    private static ValueFormatter generateDefaultValueFormatter() {
        return new DefaultValueFormatter(1);
    }

    public static int getDecimals(float f) {
        if (!Float.isInfinite(f = Utils.roundToNextSignificant(f))) return (int)Math.ceil(-Math.log10(f)) + 2;
        return 0;
    }

    public static ValueFormatter getDefaultValueFormatter() {
        return mDefaultValueFormatter;
    }

    public static float getLineHeight(Paint paint) {
        return Utils.getLineHeight(paint, mFontMetrics);
    }

    public static float getLineHeight(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float getLineSpacing(Paint paint) {
        return Utils.getLineSpacing(paint, mFontMetrics);
    }

    public static float getLineSpacing(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.ascent - fontMetrics.top + fontMetrics.bottom;
    }

    public static int getMaximumFlingVelocity() {
        return mMaximumFlingVelocity;
    }

    public static int getMinimumFlingVelocity() {
        return mMinimumFlingVelocity;
    }

    public static float getNormalizedAngle(float f) {
        while (f < 0.0f) {
            f += 360.0f;
        }
        return f % 360.0f;
    }

    public static MPPointF getPosition(MPPointF mPPointF, float f, float f2) {
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        Utils.getPosition(mPPointF, f, f2, mPPointF2);
        return mPPointF2;
    }

    public static void getPosition(MPPointF mPPointF, float f, float f2, MPPointF mPPointF2) {
        double d = mPPointF.x;
        double d2 = f;
        double d3 = f2;
        mPPointF2.x = (float)(d + Math.cos(Math.toRadians(d3)) * d2);
        mPPointF2.y = (float)((double)mPPointF.y + d2 * Math.sin(Math.toRadians(d3)));
    }

    public static int getSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(float f, float f2, float f3) {
        return Utils.getSizeOfRotatedRectangleByRadians(f, f2, f3 * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(FSize fSize, float f) {
        return Utils.getSizeOfRotatedRectangleByRadians(fSize.width, fSize.height, f * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(float f, float f2, float f3) {
        double d = f3;
        return FSize.getInstance(Math.abs((float)Math.cos(d) * f) + Math.abs((float)Math.sin(d) * f2), Math.abs(f * (float)Math.sin(d)) + Math.abs(f2 * (float)Math.cos(d)));
    }

    public static FSize getSizeOfRotatedRectangleByRadians(FSize fSize, float f) {
        return Utils.getSizeOfRotatedRectangleByRadians(fSize.width, fSize.height, f);
    }

    public static void init(Context context) {
        if (context == null) {
            mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            Log.e((String)"MPChartLib-Utils", (String)"Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
            return;
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMetrics = context.getResources().getDisplayMetrics();
    }

    @Deprecated
    public static void init(Resources resources) {
        mMetrics = resources.getDisplayMetrics();
        mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
        mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
    }

    public static double nextUp(double d) {
        long l;
        if (d == Double.POSITIVE_INFINITY) {
            return d;
        }
        long l2 = Double.doubleToRawLongBits(d += 0.0);
        if (d >= 0.0) {
            l = 1L;
            return Double.longBitsToDouble(l2 + l);
        }
        l = -1L;
        return Double.longBitsToDouble(l2 + l);
    }

    public static void postInvalidateOnAnimation(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation();
            return;
        }
        view.postInvalidateDelayed(10L);
    }

    public static float roundToNextSignificant(double d) {
        if (Double.isInfinite(d)) return 0.0f;
        if (Double.isNaN(d)) return 0.0f;
        if (d == 0.0) {
            return 0.0f;
        }
        double d2 = d < 0.0 ? -d : d;
        float f = (float)Math.pow(10.0, 1 - (int)Math.ceil((float)Math.log10(d2)));
        return (float)Math.round(d * (double)f) / f;
    }

    public static void velocityTrackerPointerUpCleanUpIfNecessary(MotionEvent motionEvent, VelocityTracker velocityTracker) {
        velocityTracker.computeCurrentVelocity(1000, (float)mMaximumFlingVelocity);
        int n = motionEvent.getActionIndex();
        int n2 = motionEvent.getPointerId(n);
        float f = velocityTracker.getXVelocity(n2);
        float f2 = velocityTracker.getYVelocity(n2);
        int n3 = motionEvent.getPointerCount();
        n2 = 0;
        while (n2 < n3) {
            int n4;
            if (n2 != n && velocityTracker.getXVelocity(n4 = motionEvent.getPointerId(n2)) * f + velocityTracker.getYVelocity(n4) * f2 < 0.0f) {
                velocityTracker.clear();
                return;
            }
            ++n2;
        }
    }
}

