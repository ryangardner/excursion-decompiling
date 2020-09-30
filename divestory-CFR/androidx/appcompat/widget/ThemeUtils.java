/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.util.Log
 *  android.view.View
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.ColorUtils;

public class ThemeUtils {
    static final int[] ACTIVATED_STATE_SET;
    static final int[] CHECKED_STATE_SET;
    static final int[] DISABLED_STATE_SET;
    static final int[] EMPTY_STATE_SET;
    static final int[] FOCUSED_STATE_SET;
    static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET;
    static final int[] PRESSED_STATE_SET;
    static final int[] SELECTED_STATE_SET;
    private static final String TAG = "ThemeUtils";
    private static final int[] TEMP_ARRAY;
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE;

    static {
        TL_TYPED_VALUE = new ThreadLocal();
        DISABLED_STATE_SET = new int[]{-16842910};
        FOCUSED_STATE_SET = new int[]{16842908};
        ACTIVATED_STATE_SET = new int[]{16843518};
        PRESSED_STATE_SET = new int[]{16842919};
        CHECKED_STATE_SET = new int[]{16842912};
        SELECTED_STATE_SET = new int[]{16842913};
        NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[]{-16842919, -16842908};
        EMPTY_STATE_SET = new int[0];
        TEMP_ARRAY = new int[1];
    }

    private ThemeUtils() {
    }

    public static void checkAppCompatTheme(View view, Context context) {
        context = context.obtainStyledAttributes(R.styleable.AppCompatTheme);
        try {
            if (context.hasValue(R.styleable.AppCompatTheme_windowActionBar)) return;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("View ");
            stringBuilder.append(view.getClass());
            stringBuilder.append(" is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
            Log.e((String)TAG, (String)stringBuilder.toString());
            return;
        }
        finally {
            context.recycle();
        }
    }

    public static ColorStateList createDisabledStateList(int n, int n2) {
        return new ColorStateList((int[][])new int[][]{DISABLED_STATE_SET, EMPTY_STATE_SET}, new int[]{n2, n});
    }

    public static int getDisabledThemeAttrColor(Context context, int n) {
        Object object = ThemeUtils.getThemeAttrColorStateList(context, n);
        if (object != null && object.isStateful()) {
            return object.getColorForState(DISABLED_STATE_SET, object.getDefaultColor());
        }
        object = ThemeUtils.getTypedValue();
        context.getTheme().resolveAttribute(16842803, (TypedValue)object, true);
        return ThemeUtils.getThemeAttrColor(context, n, object.getFloat());
    }

    public static int getThemeAttrColor(Context context, int n) {
        Object object = TEMP_ARRAY;
        object[0] = n;
        object = TintTypedArray.obtainStyledAttributes(context, null, (int[])object);
        try {
            n = ((TintTypedArray)object).getColor(0, 0);
            return n;
        }
        finally {
            ((TintTypedArray)object).recycle();
        }
    }

    static int getThemeAttrColor(Context context, int n, float f) {
        n = ThemeUtils.getThemeAttrColor(context, n);
        return ColorUtils.setAlphaComponent(n, Math.round((float)Color.alpha((int)n) * f));
    }

    public static ColorStateList getThemeAttrColorStateList(Context object, int n) {
        ColorStateList colorStateList = TEMP_ARRAY;
        colorStateList[0] = n;
        object = TintTypedArray.obtainStyledAttributes((Context)object, null, (int[])colorStateList);
        try {
            colorStateList = ((TintTypedArray)object).getColorStateList(0);
            return colorStateList;
        }
        finally {
            ((TintTypedArray)object).recycle();
        }
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue;
        TypedValue typedValue2 = typedValue = TL_TYPED_VALUE.get();
        if (typedValue != null) return typedValue2;
        typedValue2 = new TypedValue();
        TL_TYPED_VALUE.set(typedValue2);
        return typedValue2;
    }
}

