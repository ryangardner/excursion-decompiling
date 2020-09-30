/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 */
package com.google.android.material.circularreveal;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.math.MathUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CircularRevealHelper {
    public static final int BITMAP_SHADER = 0;
    public static final int CLIP_PATH = 1;
    private static final boolean DEBUG = false;
    public static final int REVEAL_ANIMATOR = 2;
    public static final int STRATEGY;
    private boolean buildingCircularRevealCache;
    private Paint debugPaint;
    private final Delegate delegate;
    private boolean hasCircularRevealCache;
    private Drawable overlayDrawable;
    private CircularRevealWidget.RevealInfo revealInfo;
    private final Paint revealPaint;
    private final Path revealPath;
    private final Paint scrimPaint;
    private final View view;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            STRATEGY = 2;
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            STRATEGY = 1;
            return;
        }
        STRATEGY = 0;
    }

    public CircularRevealHelper(Delegate delegate) {
        this.delegate = delegate;
        delegate = (View)delegate;
        this.view = delegate;
        delegate.setWillNotDraw(false);
        this.revealPath = new Path();
        this.revealPaint = new Paint(7);
        delegate = new Paint(1);
        this.scrimPaint = delegate;
        delegate.setColor(0);
    }

    private void drawDebugCircle(Canvas canvas, int n, float f) {
        this.debugPaint.setColor(n);
        this.debugPaint.setStrokeWidth(f);
        canvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius - f / 2.0f, this.debugPaint);
    }

    private void drawDebugMode(Canvas canvas) {
        this.delegate.actualDraw(canvas);
        if (this.shouldDrawScrim()) {
            canvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.scrimPaint);
        }
        if (this.shouldDrawCircularReveal()) {
            this.drawDebugCircle(canvas, -16777216, 10.0f);
            this.drawDebugCircle(canvas, -65536, 5.0f);
        }
        this.drawOverlayDrawable(canvas);
    }

    private void drawOverlayDrawable(Canvas canvas) {
        if (!this.shouldDrawOverlayDrawable()) return;
        Rect rect = this.overlayDrawable.getBounds();
        float f = this.revealInfo.centerX - (float)rect.width() / 2.0f;
        float f2 = this.revealInfo.centerY - (float)rect.height() / 2.0f;
        canvas.translate(f, f2);
        this.overlayDrawable.draw(canvas);
        canvas.translate(-f, -f2);
    }

    private float getDistanceToFurthestCorner(CircularRevealWidget.RevealInfo revealInfo) {
        return MathUtils.distanceToFurthestCorner(revealInfo.centerX, revealInfo.centerY, 0.0f, 0.0f, this.view.getWidth(), this.view.getHeight());
    }

    private void invalidateRevealInfo() {
        if (STRATEGY == 1) {
            this.revealPath.rewind();
            CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
            if (revealInfo != null) {
                this.revealPath.addCircle(revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, Path.Direction.CW);
            }
        }
        this.view.invalidate();
    }

    private boolean shouldDrawCircularReveal() {
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        boolean bl = false;
        boolean bl2 = revealInfo == null || revealInfo.isInvalid();
        if (STRATEGY != 0) return bl2 ^ true;
        boolean bl3 = bl;
        if (bl2) return bl3;
        bl3 = bl;
        if (!this.hasCircularRevealCache) return bl3;
        return true;
    }

    private boolean shouldDrawOverlayDrawable() {
        if (this.buildingCircularRevealCache) return false;
        if (this.overlayDrawable == null) return false;
        if (this.revealInfo == null) return false;
        return true;
    }

    private boolean shouldDrawScrim() {
        if (this.buildingCircularRevealCache) return false;
        if (Color.alpha((int)this.scrimPaint.getColor()) == 0) return false;
        return true;
    }

    public void buildCircularRevealCache() {
        Bitmap bitmap;
        if (STRATEGY != 0) return;
        this.buildingCircularRevealCache = true;
        this.hasCircularRevealCache = false;
        this.view.buildDrawingCache();
        Bitmap bitmap2 = bitmap = this.view.getDrawingCache();
        if (bitmap == null) {
            bitmap2 = bitmap;
            if (this.view.getWidth() != 0) {
                bitmap2 = bitmap;
                if (this.view.getHeight() != 0) {
                    bitmap2 = Bitmap.createBitmap((int)this.view.getWidth(), (int)this.view.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    bitmap = new Canvas(bitmap2);
                    this.view.draw((Canvas)bitmap);
                }
            }
        }
        if (bitmap2 != null) {
            this.revealPaint.setShader((Shader)new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        }
        this.buildingCircularRevealCache = false;
        this.hasCircularRevealCache = true;
    }

    public void destroyCircularRevealCache() {
        if (STRATEGY != 0) return;
        this.hasCircularRevealCache = false;
        this.view.destroyDrawingCache();
        this.revealPaint.setShader(null);
        this.view.invalidate();
    }

    public void draw(Canvas object) {
        if (this.shouldDrawCircularReveal()) {
            int n = STRATEGY;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unsupported strategy ");
                        ((StringBuilder)object).append(STRATEGY);
                        throw new IllegalStateException(((StringBuilder)object).toString());
                    }
                    this.delegate.actualDraw((Canvas)object);
                    if (this.shouldDrawScrim()) {
                        object.drawRect(0.0f, 0.0f, (float)this.view.getWidth(), (float)this.view.getHeight(), this.scrimPaint);
                    }
                } else {
                    n = object.save();
                    object.clipPath(this.revealPath);
                    this.delegate.actualDraw((Canvas)object);
                    if (this.shouldDrawScrim()) {
                        object.drawRect(0.0f, 0.0f, (float)this.view.getWidth(), (float)this.view.getHeight(), this.scrimPaint);
                    }
                    object.restoreToCount(n);
                }
            } else {
                object.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.revealPaint);
                if (this.shouldDrawScrim()) {
                    object.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.scrimPaint);
                }
            }
        } else {
            this.delegate.actualDraw((Canvas)object);
            if (this.shouldDrawScrim()) {
                object.drawRect(0.0f, 0.0f, (float)this.view.getWidth(), (float)this.view.getHeight(), this.scrimPaint);
            }
        }
        this.drawOverlayDrawable((Canvas)object);
    }

    public Drawable getCircularRevealOverlayDrawable() {
        return this.overlayDrawable;
    }

    public int getCircularRevealScrimColor() {
        return this.scrimPaint.getColor();
    }

    public CircularRevealWidget.RevealInfo getRevealInfo() {
        if (this.revealInfo == null) {
            return null;
        }
        CircularRevealWidget.RevealInfo revealInfo = new CircularRevealWidget.RevealInfo(this.revealInfo);
        if (!revealInfo.isInvalid()) return revealInfo;
        revealInfo.radius = this.getDistanceToFurthestCorner(revealInfo);
        return revealInfo;
    }

    public boolean isOpaque() {
        if (!this.delegate.actualIsOpaque()) return false;
        if (this.shouldDrawCircularReveal()) return false;
        return true;
    }

    public void setCircularRevealOverlayDrawable(Drawable drawable2) {
        this.overlayDrawable = drawable2;
        this.view.invalidate();
    }

    public void setCircularRevealScrimColor(int n) {
        this.scrimPaint.setColor(n);
        this.view.invalidate();
    }

    public void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo) {
        if (revealInfo == null) {
            this.revealInfo = null;
        } else {
            CircularRevealWidget.RevealInfo revealInfo2 = this.revealInfo;
            if (revealInfo2 == null) {
                this.revealInfo = new CircularRevealWidget.RevealInfo(revealInfo);
            } else {
                revealInfo2.set(revealInfo);
            }
            if (MathUtils.geq(revealInfo.radius, this.getDistanceToFurthestCorner(revealInfo), 1.0E-4f)) {
                this.revealInfo.radius = Float.MAX_VALUE;
            }
        }
        this.invalidateRevealInfo();
    }

    public static interface Delegate {
        public void actualDraw(Canvas var1);

        public boolean actualIsOpaque();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Strategy {
    }

}

