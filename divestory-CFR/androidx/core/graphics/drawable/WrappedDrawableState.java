/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.graphics.drawable.WrappedDrawableApi14;
import androidx.core.graphics.drawable.WrappedDrawableApi21;

final class WrappedDrawableState
extends Drawable.ConstantState {
    int mChangingConfigurations;
    Drawable.ConstantState mDrawableState;
    ColorStateList mTint = null;
    PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;

    WrappedDrawableState(WrappedDrawableState wrappedDrawableState) {
        if (wrappedDrawableState == null) return;
        this.mChangingConfigurations = wrappedDrawableState.mChangingConfigurations;
        this.mDrawableState = wrappedDrawableState.mDrawableState;
        this.mTint = wrappedDrawableState.mTint;
        this.mTintMode = wrappedDrawableState.mTintMode;
    }

    boolean canConstantState() {
        if (this.mDrawableState == null) return false;
        return true;
    }

    public int getChangingConfigurations() {
        int n;
        int n2 = this.mChangingConfigurations;
        Drawable.ConstantState constantState = this.mDrawableState;
        if (constantState != null) {
            n = constantState.getChangingConfigurations();
            return n2 | n;
        }
        n = 0;
        return n2 | n;
    }

    public Drawable newDrawable() {
        return this.newDrawable(null);
    }

    public Drawable newDrawable(Resources resources) {
        if (Build.VERSION.SDK_INT < 21) return new WrappedDrawableApi14(this, resources);
        return new WrappedDrawableApi21(this, resources);
    }
}

