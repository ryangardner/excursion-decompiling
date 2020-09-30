/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 */
package androidx.vectordrawable.graphics.drawable;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;

abstract class VectorDrawableCommon
extends Drawable
implements TintAwareDrawable {
    Drawable mDelegateDrawable;

    VectorDrawableCommon() {
    }

    public void applyTheme(Resources.Theme theme) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return;
        DrawableCompat.applyTheme(drawable2, theme);
    }

    public void clearColorFilter() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 != null) {
            drawable2.clearColorFilter();
            return;
        }
        super.clearColorFilter();
    }

    public Drawable getCurrent() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.getCurrent();
        return drawable2.getCurrent();
    }

    public int getMinimumHeight() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.getMinimumHeight();
        return drawable2.getMinimumHeight();
    }

    public int getMinimumWidth() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.getMinimumWidth();
        return drawable2.getMinimumWidth();
    }

    public boolean getPadding(Rect rect) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.getPadding(rect);
        return drawable2.getPadding(rect);
    }

    public int[] getState() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.getState();
        return drawable2.getState();
    }

    public Region getTransparentRegion() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.getTransparentRegion();
        return drawable2.getTransparentRegion();
    }

    public void jumpToCurrentState() {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return;
        DrawableCompat.jumpToCurrentState(drawable2);
    }

    protected void onBoundsChange(Rect rect) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
            return;
        }
        super.onBoundsChange(rect);
    }

    protected boolean onLevelChange(int n) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.onLevelChange(n);
        return drawable2.setLevel(n);
    }

    public void setChangingConfigurations(int n) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 != null) {
            drawable2.setChangingConfigurations(n);
            return;
        }
        super.setChangingConfigurations(n);
    }

    public void setColorFilter(int n, PorterDuff.Mode mode) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 != null) {
            drawable2.setColorFilter(n, mode);
            return;
        }
        super.setColorFilter(n, mode);
    }

    public void setFilterBitmap(boolean bl) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return;
        drawable2.setFilterBitmap(bl);
    }

    public void setHotspot(float f, float f2) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return;
        DrawableCompat.setHotspot(drawable2, f, f2);
    }

    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return;
        DrawableCompat.setHotspotBounds(drawable2, n, n2, n3, n4);
    }

    public boolean setState(int[] arrn) {
        Drawable drawable2 = this.mDelegateDrawable;
        if (drawable2 == null) return super.setState(arrn);
        return drawable2.setState(arrn);
    }
}

