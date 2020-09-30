/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.ThemeUtils;
import androidx.appcompat.widget.TintInfo;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.graphics.ColorUtils;

public final class AppCompatDrawableManager {
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    private static AppCompatDrawableManager INSTANCE;
    private static final String TAG = "AppCompatDrawableManag";
    private ResourceManagerInternal mResourceManager;

    public static AppCompatDrawableManager get() {
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE == null) {
                AppCompatDrawableManager.preload();
            }
            AppCompatDrawableManager appCompatDrawableManager = INSTANCE;
            return appCompatDrawableManager;
        }
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int n, PorterDuff.Mode mode) {
        synchronized (AppCompatDrawableManager.class) {
            mode = ResourceManagerInternal.getPorterDuffColorFilter(n, mode);
            return mode;
        }
    }

    public static void preload() {
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE != null) return;
            Object object = new AppCompatDrawableManager();
            INSTANCE = object;
            ((AppCompatDrawableManager)object).mResourceManager = ResourceManagerInternal.get();
            object = AppCompatDrawableManager.INSTANCE.mResourceManager;
            ResourceManagerInternal.ResourceManagerHooks resourceManagerHooks = new ResourceManagerInternal.ResourceManagerHooks(){
                private final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
                private final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
                private final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
                private final int[] TINT_CHECKABLE_BUTTON_LIST = new int[]{R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material, R.drawable.abc_btn_check_material_anim, R.drawable.abc_btn_radio_material_anim};
                private final int[] TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
                private final int[] TINT_COLOR_CONTROL_STATE_LIST = new int[]{R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};

                private boolean arrayContains(int[] arrn, int n) {
                    int n2 = arrn.length;
                    int n3 = 0;
                    while (n3 < n2) {
                        if (arrn[n3] == n) {
                            return true;
                        }
                        ++n3;
                    }
                    return false;
                }

                private ColorStateList createBorderlessButtonColorStateList(Context context) {
                    return this.createButtonColorStateList(context, 0);
                }

                private ColorStateList createButtonColorStateList(Context arrn, int n) {
                    int n2 = ThemeUtils.getThemeAttrColor((Context)arrn, R.attr.colorControlHighlight);
                    int n3 = ThemeUtils.getDisabledThemeAttrColor((Context)arrn, R.attr.colorButtonNormal);
                    int[] arrn2 = ThemeUtils.DISABLED_STATE_SET;
                    int[] arrn3 = ThemeUtils.PRESSED_STATE_SET;
                    int n4 = ColorUtils.compositeColors(n2, n);
                    arrn = ThemeUtils.FOCUSED_STATE_SET;
                    n2 = ColorUtils.compositeColors(n2, n);
                    return new ColorStateList((int[][])new int[][]{arrn2, arrn3, arrn, ThemeUtils.EMPTY_STATE_SET}, new int[]{n3, n4, n2, n});
                }

                private ColorStateList createColoredButtonColorStateList(Context context) {
                    return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorAccent));
                }

                private ColorStateList createDefaultButtonColorStateList(Context context) {
                    return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal));
                }

                private ColorStateList createSwitchThumbColorStateList(Context context) {
                    int[][] arrarrn = new int[3][];
                    int[] arrn = new int[3];
                    ColorStateList colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal);
                    if (colorStateList != null && colorStateList.isStateful()) {
                        arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
                        arrn[0] = colorStateList.getColorForState(arrarrn[0], 0);
                        arrarrn[1] = ThemeUtils.CHECKED_STATE_SET;
                        arrn[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
                        arrarrn[2] = ThemeUtils.EMPTY_STATE_SET;
                        arrn[2] = colorStateList.getDefaultColor();
                        return new ColorStateList((int[][])arrarrn, arrn);
                    }
                    arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
                    arrn[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
                    arrarrn[1] = ThemeUtils.CHECKED_STATE_SET;
                    arrn[1] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
                    arrarrn[2] = ThemeUtils.EMPTY_STATE_SET;
                    arrn[2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
                    return new ColorStateList((int[][])arrarrn, arrn);
                }

                private void setPorterDuffColorFilter(Drawable drawable2, int n, PorterDuff.Mode mode) {
                    Drawable drawable3 = drawable2;
                    if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
                        drawable3 = drawable2.mutate();
                    }
                    drawable2 = mode;
                    if (mode == null) {
                        drawable2 = DEFAULT_MODE;
                    }
                    drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(n, (PorterDuff.Mode)drawable2));
                }

                @Override
                public Drawable createDrawableFor(ResourceManagerInternal resourceManagerInternal, Context context, int n) {
                    if (n != R.drawable.abc_cab_background_top_material) return null;
                    return new LayerDrawable(new Drawable[]{resourceManagerInternal.getDrawable(context, R.drawable.abc_cab_background_internal_bg), resourceManagerInternal.getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
                }

                @Override
                public ColorStateList getTintListForDrawableRes(Context context, int n) {
                    if (n == R.drawable.abc_edit_text_material) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext);
                    }
                    if (n == R.drawable.abc_switch_track_mtrl_alpha) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track);
                    }
                    if (n == R.drawable.abc_switch_thumb_material) {
                        return this.createSwitchThumbColorStateList(context);
                    }
                    if (n == R.drawable.abc_btn_default_mtrl_shape) {
                        return this.createDefaultButtonColorStateList(context);
                    }
                    if (n == R.drawable.abc_btn_borderless_material) {
                        return this.createBorderlessButtonColorStateList(context);
                    }
                    if (n == R.drawable.abc_btn_colored_material) {
                        return this.createColoredButtonColorStateList(context);
                    }
                    if (n == R.drawable.abc_spinner_mtrl_am_alpha) return AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
                    if (n == R.drawable.abc_spinner_textfield_background_material) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
                    }
                    if (this.arrayContains(this.TINT_COLOR_CONTROL_NORMAL, n)) {
                        return ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal);
                    }
                    if (this.arrayContains(this.TINT_COLOR_CONTROL_STATE_LIST, n)) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_default);
                    }
                    if (this.arrayContains(this.TINT_CHECKABLE_BUTTON_LIST, n)) {
                        return AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable);
                    }
                    if (n != R.drawable.abc_seekbar_thumb_material) return null;
                    return AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb);
                }

                @Override
                public PorterDuff.Mode getTintModeForDrawableRes(int n) {
                    if (n != R.drawable.abc_switch_thumb_material) return null;
                    return PorterDuff.Mode.MULTIPLY;
                }

                @Override
                public boolean tintDrawable(Context context, int n, Drawable drawable2) {
                    if (n == R.drawable.abc_seekbar_track_material) {
                        drawable2 = (LayerDrawable)drawable2;
                        this.setPorterDuffColorFilter(drawable2.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
                        this.setPorterDuffColorFilter(drawable2.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
                        this.setPorterDuffColorFilter(drawable2.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
                        return true;
                    }
                    if (n != R.drawable.abc_ratingbar_material && n != R.drawable.abc_ratingbar_indicator_material) {
                        if (n != R.drawable.abc_ratingbar_small_material) return false;
                    }
                    drawable2 = (LayerDrawable)drawable2;
                    this.setPorterDuffColorFilter(drawable2.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
                    this.setPorterDuffColorFilter(drawable2.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
                    this.setPorterDuffColorFilter(drawable2.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
                    return true;
                }

                /*
                 * Unable to fully structure code
                 */
                @Override
                public boolean tintDrawableUsingColorFilter(Context var1_1, int var2_2, Drawable var3_3) {
                    block6 : {
                        block5 : {
                            block4 : {
                                block3 : {
                                    var4_4 = AppCompatDrawableManager.access$000();
                                    var5_5 = this.arrayContains(this.COLORFILTER_TINT_COLOR_CONTROL_NORMAL, var2_2);
                                    var6_6 = 16842801;
                                    if (!var5_5) break block3;
                                    var2_2 = R.attr.colorControlNormal;
                                    ** GOTO lbl24
                                }
                                if (!this.arrayContains(this.COLORFILTER_COLOR_CONTROL_ACTIVATED, var2_2)) break block4;
                                var2_2 = R.attr.colorControlActivated;
                                ** GOTO lbl24
                            }
                            if (!this.arrayContains(this.COLORFILTER_COLOR_BACKGROUND_MULTIPLY, var2_2)) break block5;
                            var4_4 = PorterDuff.Mode.MULTIPLY;
                            var2_2 = var6_6;
                            ** GOTO lbl24
                        }
                        if (var2_2 != R.drawable.abc_list_divider_mtrl_alpha) break block6;
                        var2_2 = 16842800;
                        var6_6 = Math.round(40.8f);
                        ** GOTO lbl25
                    }
                    if (var2_2 == R.drawable.abc_dialog_material_background) {
                        var2_2 = var6_6;
lbl24: // 4 sources:
                        var6_6 = -1;
lbl25: // 2 sources:
                        var7_7 = true;
                    } else {
                        var6_6 = -1;
                        var7_7 = false;
                        var2_2 = 0;
                    }
                    if (var7_7 == false) return false;
                    var8_8 = var3_3;
                    if (DrawableUtils.canSafelyMutateDrawable(var3_3)) {
                        var8_8 = var3_3.mutate();
                    }
                    var8_8.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(var1_1, var2_2), var4_4));
                    if (var6_6 == -1) return true;
                    var8_8.setAlpha(var6_6);
                    return true;
                }
            };
            ((ResourceManagerInternal)object).setHooks(resourceManagerHooks);
            return;
        }
    }

    static void tintDrawable(Drawable drawable2, TintInfo tintInfo, int[] arrn) {
        ResourceManagerInternal.tintDrawable(drawable2, tintInfo, arrn);
    }

    public Drawable getDrawable(Context context, int n) {
        synchronized (this) {
            return this.mResourceManager.getDrawable(context, n);
        }
    }

    Drawable getDrawable(Context context, int n, boolean bl) {
        synchronized (this) {
            return this.mResourceManager.getDrawable(context, n, bl);
        }
    }

    ColorStateList getTintList(Context context, int n) {
        synchronized (this) {
            return this.mResourceManager.getTintList(context, n);
        }
    }

    public void onConfigurationChanged(Context context) {
        synchronized (this) {
            this.mResourceManager.onConfigurationChanged(context);
            return;
        }
    }

    Drawable onDrawableLoadedFromResources(Context context, VectorEnabledTintResources vectorEnabledTintResources, int n) {
        synchronized (this) {
            return this.mResourceManager.onDrawableLoadedFromResources(context, vectorEnabledTintResources, n);
        }
    }

    boolean tintDrawableUsingColorFilter(Context context, int n, Drawable drawable2) {
        return this.mResourceManager.tintDrawableUsingColorFilter(context, n, drawable2);
    }

}

