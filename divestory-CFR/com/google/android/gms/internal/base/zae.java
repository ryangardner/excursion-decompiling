/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.SystemClock
 */
package com.google.android.gms.internal.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.google.android.gms.internal.base.zaf;
import com.google.android.gms.internal.base.zah;

public final class zae
extends Drawable
implements Drawable.Callback {
    private int zaa = 0;
    private long zab;
    private int zac;
    private int zad;
    private int zae = 255;
    private int zaf;
    private int zag = 0;
    private boolean zah = true;
    private boolean zai;
    private zah zaj;
    private Drawable zak;
    private Drawable zal;
    private boolean zam;
    private boolean zan;
    private boolean zao;
    private int zap;

    public zae(Drawable object, Drawable object2) {
        this(null);
        Drawable drawable2 = object;
        if (object == null) {
            drawable2 = zaf.zaa();
        }
        this.zak = drawable2;
        drawable2.setCallback((Drawable.Callback)this);
        object = this.zaj;
        int n = object.zab;
        object.zab = drawable2.getChangingConfigurations() | n;
        object = object2;
        if (object2 == null) {
            object = zaf.zaa();
        }
        this.zal = object;
        object.setCallback((Drawable.Callback)this);
        object2 = this.zaj;
        n = object2.zab;
        object2.zab = object.getChangingConfigurations() | n;
    }

    zae(zah zah2) {
        this.zaj = new zah(zah2);
    }

    private final boolean zab() {
        if (this.zam) return this.zan;
        boolean bl = this.zak.getConstantState() != null && this.zal.getConstantState() != null;
        this.zan = bl;
        this.zam = true;
        return this.zan;
    }

    public final void draw(Canvas canvas) {
        int n = this.zaa;
        int n2 = 0;
        int n3 = 1;
        if (n != 1) {
            if (n == 2 && this.zab >= 0L) {
                float f = (float)(SystemClock.uptimeMillis() - this.zab) / (float)this.zaf;
                n2 = f >= 1.0f ? n3 : 0;
                if (n2 != 0) {
                    this.zaa = 0;
                }
                f = Math.min(f, 1.0f);
                this.zag = (int)((float)this.zad * f + 0.0f);
            } else {
                n2 = 1;
            }
        } else {
            this.zab = SystemClock.uptimeMillis();
            this.zaa = 2;
        }
        n3 = this.zag;
        boolean bl = this.zah;
        Drawable drawable2 = this.zak;
        Drawable drawable3 = this.zal;
        if (n2 != 0) {
            if (!bl || n3 == 0) {
                drawable2.draw(canvas);
            }
            if (n3 != (n2 = this.zae)) return;
            drawable3.setAlpha(n2);
            drawable3.draw(canvas);
            return;
        }
        if (bl) {
            drawable2.setAlpha(this.zae - n3);
        }
        drawable2.draw(canvas);
        if (bl) {
            drawable2.setAlpha(this.zae);
        }
        if (n3 > 0) {
            drawable3.setAlpha(n3);
            drawable3.draw(canvas);
            drawable3.setAlpha(this.zae);
        }
        this.invalidateSelf();
    }

    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.zaj.zaa | this.zaj.zab;
    }

    public final Drawable.ConstantState getConstantState() {
        if (!this.zab()) return null;
        this.zaj.zaa = this.getChangingConfigurations();
        return this.zaj;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zak.getIntrinsicHeight(), this.zal.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zak.getIntrinsicWidth(), this.zal.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (this.zao) return this.zap;
        this.zap = Drawable.resolveOpacity((int)this.zak.getOpacity(), (int)this.zal.getOpacity());
        this.zao = true;
        return this.zap;
    }

    public final void invalidateDrawable(Drawable drawable2) {
        drawable2 = this.getCallback();
        if (drawable2 == null) return;
        drawable2.invalidateDrawable((Drawable)this);
    }

    public final Drawable mutate() {
        if (this.zai) return this;
        if (super.mutate() != this) return this;
        if (!this.zab()) throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
        this.zak.mutate();
        this.zal.mutate();
        this.zai = true;
        return this;
    }

    protected final void onBoundsChange(Rect rect) {
        this.zak.setBounds(rect);
        this.zal.setBounds(rect);
    }

    public final void scheduleDrawable(Drawable drawable2, Runnable runnable2, long l) {
        drawable2 = this.getCallback();
        if (drawable2 == null) return;
        drawable2.scheduleDrawable((Drawable)this, runnable2, l);
    }

    public final void setAlpha(int n) {
        if (this.zag == this.zae) {
            this.zag = n;
        }
        this.zae = n;
        this.invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zak.setColorFilter(colorFilter);
        this.zal.setColorFilter(colorFilter);
    }

    public final void unscheduleDrawable(Drawable drawable2, Runnable runnable2) {
        drawable2 = this.getCallback();
        if (drawable2 == null) return;
        drawable2.unscheduleDrawable((Drawable)this, runnable2);
    }

    public final Drawable zaa() {
        return this.zal;
    }

    public final void zaa(int n) {
        this.zac = 0;
        this.zad = this.zae;
        this.zag = 0;
        this.zaf = 250;
        this.zaa = 1;
        this.invalidateSelf();
    }
}

