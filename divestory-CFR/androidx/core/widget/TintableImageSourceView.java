/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 */
package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public interface TintableImageSourceView {
    public ColorStateList getSupportImageTintList();

    public PorterDuff.Mode getSupportImageTintMode();

    public void setSupportImageTintList(ColorStateList var1);

    public void setSupportImageTintMode(PorterDuff.Mode var1);
}

