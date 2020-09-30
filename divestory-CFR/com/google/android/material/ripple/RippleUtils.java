/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.Color
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.StateSet
 */
package com.google.android.material.ripple;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.StateSet;
import androidx.core.graphics.ColorUtils;

public class RippleUtils {
    private static final int[] ENABLED_PRESSED_STATE_SET;
    private static final int[] FOCUSED_STATE_SET;
    private static final int[] HOVERED_FOCUSED_STATE_SET;
    private static final int[] HOVERED_STATE_SET;
    static final String LOG_TAG;
    private static final int[] PRESSED_STATE_SET;
    private static final int[] SELECTED_FOCUSED_STATE_SET;
    private static final int[] SELECTED_HOVERED_FOCUSED_STATE_SET;
    private static final int[] SELECTED_HOVERED_STATE_SET;
    private static final int[] SELECTED_PRESSED_STATE_SET;
    private static final int[] SELECTED_STATE_SET;
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";
    public static final boolean USE_FRAMEWORK_RIPPLE;

    static {
        boolean bl = Build.VERSION.SDK_INT >= 21;
        USE_FRAMEWORK_RIPPLE = bl;
        PRESSED_STATE_SET = new int[]{16842919};
        HOVERED_FOCUSED_STATE_SET = new int[]{16843623, 16842908};
        FOCUSED_STATE_SET = new int[]{16842908};
        HOVERED_STATE_SET = new int[]{16843623};
        SELECTED_PRESSED_STATE_SET = new int[]{16842913, 16842919};
        SELECTED_HOVERED_FOCUSED_STATE_SET = new int[]{16842913, 16843623, 16842908};
        SELECTED_FOCUSED_STATE_SET = new int[]{16842913, 16842908};
        SELECTED_HOVERED_STATE_SET = new int[]{16842913, 16843623};
        SELECTED_STATE_SET = new int[]{16842913};
        ENABLED_PRESSED_STATE_SET = new int[]{16842910, 16842919};
        LOG_TAG = RippleUtils.class.getSimpleName();
    }

    private RippleUtils() {
    }

    public static ColorStateList convertToRippleDrawableColor(ColorStateList colorStateList) {
        if (USE_FRAMEWORK_RIPPLE) {
            int[] arrn = SELECTED_STATE_SET;
            int n = RippleUtils.getColorForState(colorStateList, SELECTED_PRESSED_STATE_SET);
            int[] arrn2 = StateSet.NOTHING;
            int n2 = RippleUtils.getColorForState(colorStateList, PRESSED_STATE_SET);
            return new ColorStateList((int[][])new int[][]{arrn, arrn2}, new int[]{n, n2});
        }
        int[] arrn = SELECTED_PRESSED_STATE_SET;
        int n = RippleUtils.getColorForState(colorStateList, arrn);
        int[] arrn3 = SELECTED_HOVERED_FOCUSED_STATE_SET;
        int n3 = RippleUtils.getColorForState(colorStateList, arrn3);
        int[] arrn4 = SELECTED_FOCUSED_STATE_SET;
        int n4 = RippleUtils.getColorForState(colorStateList, arrn4);
        int[] arrn5 = SELECTED_HOVERED_STATE_SET;
        int n5 = RippleUtils.getColorForState(colorStateList, arrn5);
        int[] arrn6 = SELECTED_STATE_SET;
        int[] arrn7 = PRESSED_STATE_SET;
        int n6 = RippleUtils.getColorForState(colorStateList, arrn7);
        int[] arrn8 = HOVERED_FOCUSED_STATE_SET;
        int n7 = RippleUtils.getColorForState(colorStateList, arrn8);
        int[] arrn9 = FOCUSED_STATE_SET;
        int n8 = RippleUtils.getColorForState(colorStateList, arrn9);
        int[] arrn10 = HOVERED_STATE_SET;
        int n9 = RippleUtils.getColorForState(colorStateList, arrn10);
        return new ColorStateList((int[][])new int[][]{arrn, arrn3, arrn4, arrn5, arrn6, arrn7, arrn8, arrn9, arrn10, StateSet.NOTHING}, new int[]{n, n3, n4, n5, 0, n6, n7, n8, n9, 0});
    }

    private static int doubleAlpha(int n) {
        return ColorUtils.setAlphaComponent(n, Math.min(Color.alpha((int)n) * 2, 255));
    }

    private static int getColorForState(ColorStateList colorStateList, int[] arrn) {
        int n = colorStateList != null ? colorStateList.getColorForState(arrn, colorStateList.getDefaultColor()) : 0;
        int n2 = n;
        if (!USE_FRAMEWORK_RIPPLE) return n2;
        return RippleUtils.doubleAlpha(n);
    }

    public static ColorStateList sanitizeRippleDrawableColor(ColorStateList colorStateList) {
        if (colorStateList == null) return ColorStateList.valueOf((int)0);
        if (Build.VERSION.SDK_INT < 22) return colorStateList;
        if (Build.VERSION.SDK_INT > 27) return colorStateList;
        if (Color.alpha((int)colorStateList.getDefaultColor()) != 0) return colorStateList;
        if (Color.alpha((int)colorStateList.getColorForState(ENABLED_PRESSED_STATE_SET, 0)) == 0) return colorStateList;
        Log.w((String)LOG_TAG, (String)TRANSPARENT_DEFAULT_COLOR_WARNING);
        return colorStateList;
    }

    public static boolean shouldDrawRippleCompat(int[] arrn) {
        int n = arrn.length;
        boolean bl = false;
        int n2 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        do {
            boolean bl4;
            block6 : {
                block7 : {
                    int n3;
                    block5 : {
                        if (n2 >= n) {
                            boolean bl5 = bl;
                            if (!bl2) return bl5;
                            bl5 = bl;
                            if (!bl3) return bl5;
                            return true;
                        }
                        n3 = arrn[n2];
                        if (n3 != 16842910) break block5;
                        bl4 = true;
                        break block6;
                    }
                    if (n3 == 16842908 || n3 == 16842919) break block7;
                    bl4 = bl2;
                    if (n3 != 16843623) break block6;
                }
                bl3 = true;
                bl4 = bl2;
            }
            ++n2;
            bl2 = bl4;
        } while (true);
    }
}

