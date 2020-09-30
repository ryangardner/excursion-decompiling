/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.ColorStateList
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.icu.text.DecimalFormatSymbols
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.PrecomputedText
 *  android.text.PrecomputedText$Params
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.Log
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.widget.TextView
 */
package androidx.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.util.Preconditions;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TintableCompoundDrawablesView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompat";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;

    private TextViewCompat() {
    }

    public static int getAutoSizeMaxTextSize(TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMaxTextSize();
        }
        if (!(textView instanceof AutoSizeableTextView)) return -1;
        return ((AutoSizeableTextView)textView).getAutoSizeMaxTextSize();
    }

    public static int getAutoSizeMinTextSize(TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMinTextSize();
        }
        if (!(textView instanceof AutoSizeableTextView)) return -1;
        return ((AutoSizeableTextView)textView).getAutoSizeMinTextSize();
    }

    public static int getAutoSizeStepGranularity(TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeStepGranularity();
        }
        if (!(textView instanceof AutoSizeableTextView)) return -1;
        return ((AutoSizeableTextView)textView).getAutoSizeStepGranularity();
    }

    public static int[] getAutoSizeTextAvailableSizes(TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextAvailableSizes();
        }
        if (!(textView instanceof AutoSizeableTextView)) return new int[0];
        return ((AutoSizeableTextView)textView).getAutoSizeTextAvailableSizes();
    }

    public static int getAutoSizeTextType(TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextType();
        }
        if (!(textView instanceof AutoSizeableTextView)) return 0;
        return ((AutoSizeableTextView)textView).getAutoSizeTextType();
    }

    public static ColorStateList getCompoundDrawableTintList(TextView textView) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            return textView.getCompoundDrawableTintList();
        }
        if (!(textView instanceof TintableCompoundDrawablesView)) return null;
        return ((TintableCompoundDrawablesView)textView).getSupportCompoundDrawablesTintList();
    }

    public static PorterDuff.Mode getCompoundDrawableTintMode(TextView textView) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            return textView.getCompoundDrawableTintMode();
        }
        if (!(textView instanceof TintableCompoundDrawablesView)) return null;
        return ((TintableCompoundDrawablesView)textView).getSupportCompoundDrawablesTintMode();
    }

    public static Drawable[] getCompoundDrawablesRelative(TextView arrdrawable) {
        if (Build.VERSION.SDK_INT >= 18) {
            return arrdrawable.getCompoundDrawablesRelative();
        }
        if (Build.VERSION.SDK_INT < 17) return arrdrawable.getCompoundDrawables();
        int n = arrdrawable.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        arrdrawable = arrdrawable.getCompoundDrawables();
        if (!bl) return arrdrawable;
        Drawable drawable2 = arrdrawable[2];
        Drawable drawable3 = arrdrawable[0];
        arrdrawable[0] = drawable2;
        arrdrawable[2] = drawable3;
        return arrdrawable;
    }

    public static int getFirstBaselineToTopHeight(TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top;
    }

    public static int getLastBaselineToBottomHeight(TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom;
    }

    public static int getMaxLines(TextView textView) {
        Field field;
        if (Build.VERSION.SDK_INT >= 16) {
            return textView.getMaxLines();
        }
        if (!sMaxModeFieldFetched) {
            sMaxModeField = TextViewCompat.retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        if ((field = sMaxModeField) == null) return -1;
        if (TextViewCompat.retrieveIntFromField(field, textView) != 1) return -1;
        if (!sMaximumFieldFetched) {
            sMaximumField = TextViewCompat.retrieveField("mMaximum");
            sMaximumFieldFetched = true;
        }
        if ((field = sMaximumField) == null) return -1;
        return TextViewCompat.retrieveIntFromField(field, textView);
    }

    public static int getMinLines(TextView textView) {
        Field field;
        if (Build.VERSION.SDK_INT >= 16) {
            return textView.getMinLines();
        }
        if (!sMinModeFieldFetched) {
            sMinModeField = TextViewCompat.retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        if ((field = sMinModeField) == null) return -1;
        if (TextViewCompat.retrieveIntFromField(field, textView) != 1) return -1;
        if (!sMinimumFieldFetched) {
            sMinimumField = TextViewCompat.retrieveField("mMinimum");
            sMinimumFieldFetched = true;
        }
        if ((field = sMinimumField) == null) return -1;
        return TextViewCompat.retrieveIntFromField(field, textView);
    }

    private static int getTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
            return 1;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 1;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            return 3;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            return 4;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
            return 5;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 6;
        }
        if (textDirectionHeuristic != TextDirectionHeuristics.FIRSTSTRONG_RTL) return 1;
        return 7;
    }

    private static TextDirectionHeuristic getTextDirectionHeuristic(TextView textView) {
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR;
        }
        int n = Build.VERSION.SDK_INT;
        byte by = 0;
        if (n >= 28 && (textView.getInputType() & 15) == 3) {
            by = Character.getDirectionality(DecimalFormatSymbols.getInstance((Locale)textView.getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (by == 1) return TextDirectionHeuristics.RTL;
            if (by != 2) return TextDirectionHeuristics.LTR;
            return TextDirectionHeuristics.RTL;
        }
        if (textView.getLayoutDirection() == 1) {
            by = 1;
        }
        switch (textView.getTextDirection()) {
            default: {
                if (by == 0) return TextDirectionHeuristics.FIRSTSTRONG_LTR;
                return TextDirectionHeuristics.FIRSTSTRONG_RTL;
            }
            case 7: {
                return TextDirectionHeuristics.FIRSTSTRONG_RTL;
            }
            case 6: {
                return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
            case 5: {
                return TextDirectionHeuristics.LOCALE;
            }
            case 4: {
                return TextDirectionHeuristics.RTL;
            }
            case 3: {
                return TextDirectionHeuristics.LTR;
            }
            case 2: {
                return TextDirectionHeuristics.ANYRTL_LTR;
            }
        }
    }

    public static PrecomputedTextCompat.Params getTextMetricsParams(TextView textView) {
        if (Build.VERSION.SDK_INT >= 28) {
            return new PrecomputedTextCompat.Params(textView.getTextMetricsParams());
        }
        PrecomputedTextCompat.Params.Builder builder = new PrecomputedTextCompat.Params.Builder(new TextPaint((Paint)textView.getPaint()));
        if (Build.VERSION.SDK_INT >= 23) {
            builder.setBreakStrategy(textView.getBreakStrategy());
            builder.setHyphenationFrequency(textView.getHyphenationFrequency());
        }
        if (Build.VERSION.SDK_INT < 18) return builder.build();
        builder.setTextDirection(TextViewCompat.getTextDirectionHeuristic(textView));
        return builder.build();
    }

    private static Field retrieveField(String string2) {
        Field field = null;
        try {
            Field field2;
            field = field2 = TextView.class.getDeclaredField(string2);
            field2.setAccessible(true);
            return field2;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not retrieve ");
            stringBuilder.append(string2);
            stringBuilder.append(" field.");
            Log.e((String)LOG_TAG, (String)stringBuilder.toString());
        }
        return field;
    }

    private static int retrieveIntFromField(Field field, TextView textView) {
        try {
            return field.getInt((Object)textView);
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not retrieve value of ");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field.");
            Log.d((String)LOG_TAG, (String)stringBuilder.toString());
            return -1;
        }
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int n, int n2, int n3, int n4) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
            return;
        }
        if (!(textView instanceof AutoSizeableTextView)) return;
        ((AutoSizeableTextView)textView).setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, int[] arrn, int n) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
            return;
        }
        if (!(textView instanceof AutoSizeableTextView)) return;
        ((AutoSizeableTextView)textView).setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
    }

    public static void setAutoSizeTextTypeWithDefaults(TextView textView, int n) {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeWithDefaults(n);
            return;
        }
        if (!(textView instanceof AutoSizeableTextView)) return;
        ((AutoSizeableTextView)textView).setAutoSizeTextTypeWithDefaults(n);
    }

    public static void setCompoundDrawableTintList(TextView textView, ColorStateList colorStateList) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setCompoundDrawableTintList(colorStateList);
            return;
        }
        if (!(textView instanceof TintableCompoundDrawablesView)) return;
        ((TintableCompoundDrawablesView)textView).setSupportCompoundDrawablesTintList(colorStateList);
    }

    public static void setCompoundDrawableTintMode(TextView textView, PorterDuff.Mode mode) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setCompoundDrawableTintMode(mode);
            return;
        }
        if (!(textView instanceof TintableCompoundDrawablesView)) return;
        ((TintableCompoundDrawablesView)textView).setSupportCompoundDrawablesTintMode(mode);
    }

    public static void setCompoundDrawablesRelative(TextView textView, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelative(drawable2, drawable3, drawable4, drawable5);
            return;
        }
        if (Build.VERSION.SDK_INT < 17) {
            textView.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
            return;
        }
        int n = textView.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        Drawable drawable6 = bl ? drawable4 : drawable2;
        if (!bl) {
            drawable2 = drawable4;
        }
        textView.setCompoundDrawables(drawable6, drawable3, drawable2, drawable5);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, int n, int n2, int n3, int n4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(n, n2, n3, n4);
            return;
        }
        if (Build.VERSION.SDK_INT < 17) {
            textView.setCompoundDrawablesWithIntrinsicBounds(n, n2, n3, n4);
            return;
        }
        int n5 = textView.getLayoutDirection();
        boolean bl = true;
        if (n5 != 1) {
            bl = false;
        }
        n5 = bl ? n3 : n;
        if (!bl) {
            n = n3;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(n5, n2, n, n4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
            return;
        }
        if (Build.VERSION.SDK_INT < 17) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
            return;
        }
        int n = textView.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        Drawable drawable6 = bl ? drawable4 : drawable2;
        if (!bl) {
            drawable2 = drawable4;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable6, drawable3, drawable2, drawable5);
    }

    public static void setCustomSelectionActionModeCallback(TextView textView, ActionMode.Callback callback) {
        textView.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(textView, callback));
    }

    public static void setFirstBaselineToTopHeight(TextView textView, int n) {
        Preconditions.checkArgumentNonnegative(n);
        if (Build.VERSION.SDK_INT >= 28) {
            textView.setFirstBaselineToTopHeight(n);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int n2 = Build.VERSION.SDK_INT >= 16 && !textView.getIncludeFontPadding() ? fontMetricsInt.ascent : fontMetricsInt.top;
        if (n <= Math.abs(n2)) return;
        textView.setPadding(textView.getPaddingLeft(), n + n2, textView.getPaddingRight(), textView.getPaddingBottom());
    }

    public static void setLastBaselineToBottomHeight(TextView textView, int n) {
        Preconditions.checkArgumentNonnegative(n);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int n2 = Build.VERSION.SDK_INT >= 16 && !textView.getIncludeFontPadding() ? fontMetricsInt.descent : fontMetricsInt.bottom;
        if (n <= Math.abs(n2)) return;
        textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), n - n2);
    }

    public static void setLineHeight(TextView textView, int n) {
        Preconditions.checkArgumentNonnegative(n);
        int n2 = textView.getPaint().getFontMetricsInt(null);
        if (n == n2) return;
        textView.setLineSpacing((float)(n - n2), 1.0f);
    }

    public static void setPrecomputedText(TextView textView, PrecomputedTextCompat precomputedTextCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            textView.setText((CharSequence)precomputedTextCompat.getPrecomputedText());
            return;
        }
        if (!TextViewCompat.getTextMetricsParams(textView).equalsWithoutTextDirection(precomputedTextCompat.getParams())) throw new IllegalArgumentException("Given text can not be applied to TextView.");
        textView.setText((CharSequence)((Object)precomputedTextCompat));
    }

    public static void setTextAppearance(TextView textView, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(n);
            return;
        }
        textView.setTextAppearance(textView.getContext(), n);
    }

    public static void setTextMetricsParams(TextView textView, PrecomputedTextCompat.Params params) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setTextDirection(TextViewCompat.getTextDirection(params.getTextDirection()));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            textView.getPaint().set(params.getTextPaint());
            textView.setBreakStrategy(params.getBreakStrategy());
            textView.setHyphenationFrequency(params.getHyphenationFrequency());
            return;
        }
        float f = params.getTextPaint().getTextScaleX();
        textView.getPaint().set(params.getTextPaint());
        if (f == textView.getTextScaleX()) {
            textView.setTextScaleX(f / 2.0f + 1.0f);
        }
        textView.setTextScaleX(f);
    }

    public static ActionMode.Callback wrapCustomSelectionActionModeCallback(TextView textView, ActionMode.Callback callback) {
        if (Build.VERSION.SDK_INT < 26) return callback;
        if (Build.VERSION.SDK_INT > 27) return callback;
        if (!(callback instanceof OreoCallback)) return new OreoCallback(callback, textView);
        return callback;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutoSizeTextType {
    }

    private static class OreoCallback
    implements ActionMode.Callback {
        private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
        private final ActionMode.Callback mCallback;
        private boolean mCanUseMenuBuilderReferences;
        private boolean mInitializedMenuBuilderReferences;
        private Class<?> mMenuBuilderClass;
        private Method mMenuBuilderRemoveItemAtMethod;
        private final TextView mTextView;

        OreoCallback(ActionMode.Callback callback, TextView textView) {
            this.mCallback = callback;
            this.mTextView = textView;
            this.mInitializedMenuBuilderReferences = false;
        }

        private Intent createProcessTextIntent() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo, TextView textView) {
            return this.createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", this.isEditable(textView) ^ true).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        }

        private List<ResolveInfo> getSupportedActivities(Context context, PackageManager object) {
            ArrayList<ResolveInfo> arrayList = new ArrayList<ResolveInfo>();
            if (!(context instanceof Activity)) {
                return arrayList;
            }
            object = object.queryIntentActivities(this.createProcessTextIntent(), 0).iterator();
            while (object.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo)object.next();
                if (!this.isSupportedActivity(resolveInfo, context)) continue;
                arrayList.add(resolveInfo);
            }
            return arrayList;
        }

        private boolean isEditable(TextView textView) {
            if (!(textView instanceof Editable)) return false;
            if (!textView.onCheckIsTextEditor()) return false;
            if (!textView.isEnabled()) return false;
            return true;
        }

        private boolean isSupportedActivity(ResolveInfo resolveInfo, Context context) {
            boolean bl = context.getPackageName().equals(resolveInfo.activityInfo.packageName);
            boolean bl2 = true;
            if (bl) {
                return true;
            }
            if (!resolveInfo.activityInfo.exported) {
                return false;
            }
            bl = bl2;
            if (resolveInfo.activityInfo.permission == null) return bl;
            if (context.checkSelfPermission(resolveInfo.activityInfo.permission) != 0) return false;
            return bl2;
        }

        private void recomputeProcessTextMenuItems(Menu menu2) {
            int n;
            Object object;
            Object object2 = this.mTextView.getContext();
            PackageManager packageManager = object2.getPackageManager();
            if (!this.mInitializedMenuBuilderReferences) {
                this.mInitializedMenuBuilderReferences = true;
                try {
                    object = Class.forName("com.android.internal.view.menu.MenuBuilder");
                    this.mMenuBuilderClass = object;
                    this.mMenuBuilderRemoveItemAtMethod = object.getDeclaredMethod("removeItemAt", Integer.TYPE);
                    this.mCanUseMenuBuilderReferences = true;
                }
                catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
                    this.mMenuBuilderClass = null;
                    this.mMenuBuilderRemoveItemAtMethod = null;
                    this.mCanUseMenuBuilderReferences = false;
                }
            }
            try {
                object = this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance((Object)menu2) ? this.mMenuBuilderRemoveItemAtMethod : menu2.getClass().getDeclaredMethod("removeItemAt", Integer.TYPE);
                for (n = menu2.size() - 1; n >= 0; --n) {
                    MenuItem menuItem = menu2.getItem(n);
                    if (menuItem.getIntent() == null || !"android.intent.action.PROCESS_TEXT".equals(menuItem.getIntent().getAction())) continue;
                    object.invoke((Object)menu2, n);
                }
                object2 = this.getSupportedActivities((Context)object2, packageManager);
                n = 0;
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
                return;
            }
            while (n < object2.size()) {
                object = (ResolveInfo)object2.get(n);
                menu2.add(0, 0, n + 100, object.loadLabel(packageManager)).setIntent(this.createProcessTextIntentForResolveInfo((ResolveInfo)object, this.mTextView)).setShowAsAction(1);
                ++n;
            }
            return;
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mCallback.onActionItemClicked(actionMode, menuItem);
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            return this.mCallback.onCreateActionMode(actionMode, menu2);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.mCallback.onDestroyActionMode(actionMode);
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            this.recomputeProcessTextMenuItems(menu2);
            return this.mCallback.onPrepareActionMode(actionMode, menu2);
        }
    }

}

