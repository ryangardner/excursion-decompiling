/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.Window
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ListAdapter
 */
package com.google.android.material.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialAlertDialogBuilder
extends AlertDialog.Builder {
    private static final int DEF_STYLE_ATTR = R.attr.alertDialogStyle;
    private static final int DEF_STYLE_RES = R.style.MaterialAlertDialog_MaterialComponents;
    private static final int MATERIAL_ALERT_DIALOG_THEME_OVERLAY = R.attr.materialAlertDialogTheme;
    private Drawable background;
    private final Rect backgroundInsets;

    public MaterialAlertDialogBuilder(Context context) {
        this(context, 0);
    }

    public MaterialAlertDialogBuilder(Context context, int n) {
        super(MaterialAlertDialogBuilder.createMaterialAlertDialogThemedContext(context), MaterialAlertDialogBuilder.getOverridingThemeResId(context, n));
        Object object = this.getContext();
        context = object.getTheme();
        this.backgroundInsets = MaterialDialogs.getDialogBackgroundInsets(object, DEF_STYLE_ATTR, DEF_STYLE_RES);
        n = MaterialColors.getColor(object, R.attr.colorSurface, this.getClass().getCanonicalName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable((Context)object, null, DEF_STYLE_ATTR, DEF_STYLE_RES);
        materialShapeDrawable.initializeElevationOverlay((Context)object);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf((int)n));
        if (Build.VERSION.SDK_INT >= 28) {
            object = new TypedValue();
            context.resolveAttribute(16844145, (TypedValue)object, true);
            float f = object.getDimension(this.getContext().getResources().getDisplayMetrics());
            if (object.type == 5 && f >= 0.0f) {
                materialShapeDrawable.setCornerSize(f);
            }
        }
        this.background = materialShapeDrawable;
    }

    private static Context createMaterialAlertDialogThemedContext(Context context) {
        int n = MaterialAlertDialogBuilder.getMaterialAlertDialogThemeOverlay(context);
        context = MaterialThemeOverlay.wrap(context, null, DEF_STYLE_ATTR, DEF_STYLE_RES);
        if (n != 0) return new ContextThemeWrapper(context, n);
        return context;
    }

    private static int getMaterialAlertDialogThemeOverlay(Context object) {
        if ((object = MaterialAttributes.resolve(object, MATERIAL_ALERT_DIALOG_THEME_OVERLAY)) != null) return object.data;
        return 0;
    }

    private static int getOverridingThemeResId(Context context, int n) {
        int n2 = n;
        if (n != 0) return n2;
        return MaterialAlertDialogBuilder.getMaterialAlertDialogThemeOverlay(context);
    }

    @Override
    public AlertDialog create() {
        AlertDialog alertDialog = super.create();
        Window window = alertDialog.getWindow();
        View view = window.getDecorView();
        Drawable drawable2 = this.background;
        if (drawable2 instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable)drawable2).setElevation(ViewCompat.getElevation(view));
        }
        window.setBackgroundDrawable((Drawable)MaterialDialogs.insetDrawable(this.background, this.backgroundInsets));
        view.setOnTouchListener((View.OnTouchListener)new InsetDialogOnTouchListener(alertDialog, this.backgroundInsets));
        return alertDialog;
    }

    public Drawable getBackground() {
        return this.background;
    }

    @Override
    public MaterialAlertDialogBuilder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setAdapter(listAdapter, onClickListener);
    }

    public MaterialAlertDialogBuilder setBackground(Drawable drawable2) {
        this.background = drawable2;
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetBottom(int n) {
        this.backgroundInsets.bottom = n;
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetEnd(int n) {
        if (Build.VERSION.SDK_INT >= 17 && this.getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            this.backgroundInsets.left = n;
            return this;
        }
        this.backgroundInsets.right = n;
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetStart(int n) {
        if (Build.VERSION.SDK_INT >= 17 && this.getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            this.backgroundInsets.right = n;
            return this;
        }
        this.backgroundInsets.left = n;
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetTop(int n) {
        this.backgroundInsets.top = n;
        return this;
    }

    @Override
    public MaterialAlertDialogBuilder setCancelable(boolean bl) {
        return (MaterialAlertDialogBuilder)super.setCancelable(bl);
    }

    @Override
    public MaterialAlertDialogBuilder setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String string2) {
        return (MaterialAlertDialogBuilder)super.setCursor(cursor, onClickListener, string2);
    }

    @Override
    public MaterialAlertDialogBuilder setCustomTitle(View view) {
        return (MaterialAlertDialogBuilder)super.setCustomTitle(view);
    }

    @Override
    public MaterialAlertDialogBuilder setIcon(int n) {
        return (MaterialAlertDialogBuilder)super.setIcon(n);
    }

    @Override
    public MaterialAlertDialogBuilder setIcon(Drawable drawable2) {
        return (MaterialAlertDialogBuilder)super.setIcon(drawable2);
    }

    @Override
    public MaterialAlertDialogBuilder setIconAttribute(int n) {
        return (MaterialAlertDialogBuilder)super.setIconAttribute(n);
    }

    @Override
    public MaterialAlertDialogBuilder setItems(int n, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setItems(n, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setItems(CharSequence[] arrcharSequence, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setItems(arrcharSequence, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setMessage(int n) {
        return (MaterialAlertDialogBuilder)super.setMessage(n);
    }

    @Override
    public MaterialAlertDialogBuilder setMessage(CharSequence charSequence) {
        return (MaterialAlertDialogBuilder)super.setMessage(charSequence);
    }

    @Override
    public MaterialAlertDialogBuilder setMultiChoiceItems(int n, boolean[] arrbl, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        return (MaterialAlertDialogBuilder)super.setMultiChoiceItems(n, arrbl, onMultiChoiceClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setMultiChoiceItems(Cursor cursor, String string2, String string3, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        return (MaterialAlertDialogBuilder)super.setMultiChoiceItems(cursor, string2, string3, onMultiChoiceClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setMultiChoiceItems(CharSequence[] arrcharSequence, boolean[] arrbl, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        return (MaterialAlertDialogBuilder)super.setMultiChoiceItems(arrcharSequence, arrbl, onMultiChoiceClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setNegativeButton(int n, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setNegativeButton(n, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setNegativeButton(charSequence, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setNegativeButtonIcon(Drawable drawable2) {
        return (MaterialAlertDialogBuilder)super.setNegativeButtonIcon(drawable2);
    }

    @Override
    public MaterialAlertDialogBuilder setNeutralButton(int n, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setNeutralButton(n, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setNeutralButton(charSequence, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setNeutralButtonIcon(Drawable drawable2) {
        return (MaterialAlertDialogBuilder)super.setNeutralButtonIcon(drawable2);
    }

    @Override
    public MaterialAlertDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return (MaterialAlertDialogBuilder)super.setOnCancelListener(onCancelListener);
    }

    @Override
    public MaterialAlertDialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return (MaterialAlertDialogBuilder)super.setOnDismissListener(onDismissListener);
    }

    @Override
    public MaterialAlertDialogBuilder setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        return (MaterialAlertDialogBuilder)super.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override
    public MaterialAlertDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        return (MaterialAlertDialogBuilder)super.setOnKeyListener(onKeyListener);
    }

    @Override
    public MaterialAlertDialogBuilder setPositiveButton(int n, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setPositiveButton(n, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setPositiveButton(charSequence, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setPositiveButtonIcon(Drawable drawable2) {
        return (MaterialAlertDialogBuilder)super.setPositiveButtonIcon(drawable2);
    }

    @Override
    public MaterialAlertDialogBuilder setSingleChoiceItems(int n, int n2, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setSingleChoiceItems(n, n2, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setSingleChoiceItems(Cursor cursor, int n, String string2, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setSingleChoiceItems(cursor, n, string2, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setSingleChoiceItems(ListAdapter listAdapter, int n, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setSingleChoiceItems(listAdapter, n, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setSingleChoiceItems(CharSequence[] arrcharSequence, int n, DialogInterface.OnClickListener onClickListener) {
        return (MaterialAlertDialogBuilder)super.setSingleChoiceItems(arrcharSequence, n, onClickListener);
    }

    @Override
    public MaterialAlertDialogBuilder setTitle(int n) {
        return (MaterialAlertDialogBuilder)super.setTitle(n);
    }

    @Override
    public MaterialAlertDialogBuilder setTitle(CharSequence charSequence) {
        return (MaterialAlertDialogBuilder)super.setTitle(charSequence);
    }

    @Override
    public MaterialAlertDialogBuilder setView(int n) {
        return (MaterialAlertDialogBuilder)super.setView(n);
    }

    @Override
    public MaterialAlertDialogBuilder setView(View view) {
        return (MaterialAlertDialogBuilder)super.setView(view);
    }
}

