/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.widget.CompoundButton
 */
package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import androidx.core.widget.TintableCompoundButton;
import java.lang.reflect.Field;

public final class CompoundButtonCompat {
    private static final String TAG = "CompoundButtonCompat";
    private static Field sButtonDrawableField;
    private static boolean sButtonDrawableFieldFetched;

    private CompoundButtonCompat() {
    }

    public static Drawable getButtonDrawable(CompoundButton compoundButton) {
        Field field;
        if (Build.VERSION.SDK_INT >= 23) {
            return compoundButton.getButtonDrawable();
        }
        if (!sButtonDrawableFieldFetched) {
            try {
                sButtonDrawableField = field = CompoundButton.class.getDeclaredField("mButtonDrawable");
                field.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.i((String)TAG, (String)"Failed to retrieve mButtonDrawable field", (Throwable)noSuchFieldException);
            }
            sButtonDrawableFieldFetched = true;
        }
        if ((field = sButtonDrawableField) == null) return null;
        try {
            return (Drawable)field.get((Object)compoundButton);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)TAG, (String)"Failed to get button drawable via reflection", (Throwable)illegalAccessException);
            sButtonDrawableField = null;
        }
        return null;
    }

    public static ColorStateList getButtonTintList(CompoundButton compoundButton) {
        if (Build.VERSION.SDK_INT >= 21) {
            return compoundButton.getButtonTintList();
        }
        if (!(compoundButton instanceof TintableCompoundButton)) return null;
        return ((TintableCompoundButton)compoundButton).getSupportButtonTintList();
    }

    public static PorterDuff.Mode getButtonTintMode(CompoundButton compoundButton) {
        if (Build.VERSION.SDK_INT >= 21) {
            return compoundButton.getButtonTintMode();
        }
        if (!(compoundButton instanceof TintableCompoundButton)) return null;
        return ((TintableCompoundButton)compoundButton).getSupportButtonTintMode();
    }

    public static void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            compoundButton.setButtonTintList(colorStateList);
            return;
        }
        if (!(compoundButton instanceof TintableCompoundButton)) return;
        ((TintableCompoundButton)compoundButton).setSupportButtonTintList(colorStateList);
    }

    public static void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT >= 21) {
            compoundButton.setButtonTintMode(mode);
            return;
        }
        if (!(compoundButton instanceof TintableCompoundButton)) return;
        ((TintableCompoundButton)compoundButton).setSupportButtonTintMode(mode);
    }
}

