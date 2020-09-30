/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.StaticLayout$Builder
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 */
package com.google.android.material.internal;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

final class StaticLayoutBuilderCompat {
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_CLASS_LTR = "LTR";
    private static final String TEXT_DIR_CLASS_RTL = "RTL";
    private static Constructor<StaticLayout> constructor;
    private static boolean initialized;
    private static Object textDirection;
    private Layout.Alignment alignment;
    private TextUtils.TruncateAt ellipsize;
    private int end;
    private boolean includePad;
    private boolean isRtl;
    private int maxLines;
    private final TextPaint paint;
    private CharSequence source;
    private int start;
    private final int width;

    private StaticLayoutBuilderCompat(CharSequence charSequence, TextPaint textPaint, int n) {
        this.source = charSequence;
        this.paint = textPaint;
        this.width = n;
        this.start = 0;
        this.end = charSequence.length();
        this.alignment = Layout.Alignment.ALIGN_NORMAL;
        this.maxLines = Integer.MAX_VALUE;
        this.includePad = true;
        this.ellipsize = null;
    }

    private void createConstructorWithReflection() throws StaticLayoutBuilderCompatException {
        if (initialized) {
            return;
        }
        try {
            Object object;
            boolean bl = this.isRtl && Build.VERSION.SDK_INT >= 23;
            if (Build.VERSION.SDK_INT >= 18) {
                Class<TextDirectionHeuristic> class_ = TextDirectionHeuristic.class;
                object = bl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
                textDirection = object;
                object = class_;
            } else {
                Object object2 = StaticLayoutBuilderCompat.class.getClassLoader();
                object = this.isRtl ? TEXT_DIR_CLASS_RTL : TEXT_DIR_CLASS_LTR;
                Class<?> class_ = ((ClassLoader)object2).loadClass(TEXT_DIR_CLASS);
                object2 = ((ClassLoader)object2).loadClass(TEXT_DIRS_CLASS);
                textDirection = ((Class)object2).getField((String)object).get(object2);
                object = class_;
            }
            constructor = object = StaticLayout.class.getDeclaredConstructor(new Class[]{CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Layout.Alignment.class, object, Float.TYPE, Float.TYPE, Boolean.TYPE, TextUtils.TruncateAt.class, Integer.TYPE, Integer.TYPE});
            ((Constructor)object).setAccessible(true);
            initialized = true;
            return;
        }
        catch (Exception exception) {
            throw new StaticLayoutBuilderCompatException(exception);
        }
    }

    public static StaticLayoutBuilderCompat obtain(CharSequence charSequence, TextPaint textPaint, int n) {
        return new StaticLayoutBuilderCompat(charSequence, textPaint, n);
    }

    public StaticLayout build() throws StaticLayoutBuilderCompatException {
        CharSequence charSequence;
        if (this.source == null) {
            this.source = "";
        }
        int n = Math.max(0, this.width);
        CharSequence charSequence2 = charSequence = this.source;
        if (this.maxLines == 1) {
            charSequence2 = TextUtils.ellipsize((CharSequence)charSequence, (TextPaint)this.paint, (float)n, (TextUtils.TruncateAt)this.ellipsize);
        }
        this.end = Math.min(charSequence2.length(), this.end);
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.isRtl) {
                this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
            }
            charSequence = StaticLayout.Builder.obtain((CharSequence)charSequence2, (int)this.start, (int)this.end, (TextPaint)this.paint, (int)n);
            charSequence.setAlignment(this.alignment);
            charSequence.setIncludePad(this.includePad);
            charSequence2 = this.isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
            charSequence.setTextDirection((TextDirectionHeuristic)charSequence2);
            charSequence2 = this.ellipsize;
            if (charSequence2 != null) {
                charSequence.setEllipsize((TextUtils.TruncateAt)charSequence2);
            }
            charSequence.setMaxLines(this.maxLines);
            return charSequence.build();
        }
        this.createConstructorWithReflection();
        try {
            return Preconditions.checkNotNull(constructor).newInstance(new Object[]{charSequence2, this.start, this.end, this.paint, n, this.alignment, Preconditions.checkNotNull(textDirection), Float.valueOf(1.0f), Float.valueOf(0.0f), this.includePad, null, n, this.maxLines});
        }
        catch (Exception exception) {
            throw new StaticLayoutBuilderCompatException(exception);
        }
    }

    public StaticLayoutBuilderCompat setAlignment(Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public StaticLayoutBuilderCompat setEllipsize(TextUtils.TruncateAt truncateAt) {
        this.ellipsize = truncateAt;
        return this;
    }

    public StaticLayoutBuilderCompat setEnd(int n) {
        this.end = n;
        return this;
    }

    public StaticLayoutBuilderCompat setIncludePad(boolean bl) {
        this.includePad = bl;
        return this;
    }

    public StaticLayoutBuilderCompat setIsRtl(boolean bl) {
        this.isRtl = bl;
        return this;
    }

    public StaticLayoutBuilderCompat setMaxLines(int n) {
        this.maxLines = n;
        return this;
    }

    public StaticLayoutBuilderCompat setStart(int n) {
        this.start = n;
        return this;
    }

    static class StaticLayoutBuilderCompatException
    extends Exception {
        StaticLayoutBuilderCompatException(Throwable throwable) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error thrown initializing StaticLayout ");
            stringBuilder.append(throwable.getMessage());
            super(stringBuilder.toString(), throwable);
        }
    }

}

