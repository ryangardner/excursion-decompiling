/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.MonthAdapter;
import com.google.android.material.datepicker.OnSelectionChangedListener;
import com.google.android.material.datepicker.PickerFragment;
import com.google.android.material.datepicker.RangeDateSelector;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.android.material.datepicker.UtcDates;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialDatePicker<S>
extends DialogFragment {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    static final Object CANCEL_BUTTON_TAG;
    static final Object CONFIRM_BUTTON_TAG;
    private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
    public static final int INPUT_MODE_CALENDAR = 0;
    private static final String INPUT_MODE_KEY = "INPUT_MODE_KEY";
    public static final int INPUT_MODE_TEXT = 1;
    private static final String OVERRIDE_THEME_RES_ID = "OVERRIDE_THEME_RES_ID";
    private static final String TITLE_TEXT_KEY = "TITLE_TEXT_KEY";
    private static final String TITLE_TEXT_RES_ID_KEY = "TITLE_TEXT_RES_ID_KEY";
    static final Object TOGGLE_BUTTON_TAG;
    private MaterialShapeDrawable background;
    private MaterialCalendar<S> calendar;
    private CalendarConstraints calendarConstraints;
    private Button confirmButton;
    private DateSelector<S> dateSelector;
    private boolean fullscreen;
    private TextView headerSelectionText;
    private CheckableImageButton headerToggleButton;
    private int inputMode;
    private final LinkedHashSet<DialogInterface.OnCancelListener> onCancelListeners = new LinkedHashSet();
    private final LinkedHashSet<DialogInterface.OnDismissListener> onDismissListeners = new LinkedHashSet();
    private final LinkedHashSet<View.OnClickListener> onNegativeButtonClickListeners = new LinkedHashSet();
    private final LinkedHashSet<MaterialPickerOnPositiveButtonClickListener<? super S>> onPositiveButtonClickListeners = new LinkedHashSet();
    private int overrideThemeResId;
    private PickerFragment<S> pickerFragment;
    private CharSequence titleText;
    private int titleTextResId;

    static {
        CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
        CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
        TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
    }

    private static Drawable createHeaderToggleDrawable(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable2 = AppCompatResources.getDrawable(context, R.drawable.material_ic_calendar_black_24dp);
        stateListDrawable.addState(new int[]{16842912}, drawable2);
        context = AppCompatResources.getDrawable(context, R.drawable.material_ic_edit_black_24dp);
        stateListDrawable.addState(new int[0], (Drawable)context);
        return stateListDrawable;
    }

    private static int getDialogPickerHeight(Context context) {
        context = context.getResources();
        return context.getDimensionPixelSize(R.dimen.mtrl_calendar_navigation_height) + context.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_top_padding) + context.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_bottom_padding) + context.getDimensionPixelSize(R.dimen.mtrl_calendar_days_of_week_height) + (MonthAdapter.MAXIMUM_WEEKS * context.getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) + (MonthAdapter.MAXIMUM_WEEKS - 1) * context.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_vertical_padding)) + context.getDimensionPixelOffset(R.dimen.mtrl_calendar_bottom_padding);
    }

    private static int getPaddedPickerWidth(Context context) {
        context = context.getResources();
        int n = context.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
        int n2 = Month.current().daysInWeek;
        return n * 2 + context.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width) * n2 + (n2 - 1) * context.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_horizontal_padding);
    }

    private int getThemeResId(Context context) {
        int n = this.overrideThemeResId;
        if (n == 0) return this.dateSelector.getDefaultThemeResId(context);
        return n;
    }

    private void initHeaderToggle(Context object) {
        this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
        this.headerToggleButton.setImageDrawable(MaterialDatePicker.createHeaderToggleDrawable((Context)object));
        object = this.headerToggleButton;
        boolean bl = this.inputMode != 0;
        ((CheckableImageButton)object).setChecked(bl);
        ViewCompat.setAccessibilityDelegate((View)this.headerToggleButton, null);
        this.updateToggleContentDescription(this.headerToggleButton);
        this.headerToggleButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                MaterialDatePicker.this.confirmButton.setEnabled(MaterialDatePicker.this.dateSelector.isSelectionComplete());
                MaterialDatePicker.this.headerToggleButton.toggle();
                object = MaterialDatePicker.this;
                ((MaterialDatePicker)object).updateToggleContentDescription(((MaterialDatePicker)object).headerToggleButton);
                MaterialDatePicker.this.startPickerFragment();
            }
        });
    }

    static boolean isFullscreen(Context context) {
        context = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[]{16843277});
        boolean bl = context.getBoolean(0, false);
        context.recycle();
        return bl;
    }

    static <S> MaterialDatePicker<S> newInstance(Builder<S> builder) {
        MaterialDatePicker<S> materialDatePicker = new MaterialDatePicker<S>();
        Bundle bundle = new Bundle();
        bundle.putInt(OVERRIDE_THEME_RES_ID, builder.overrideThemeResId);
        bundle.putParcelable(DATE_SELECTOR_KEY, builder.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, (Parcelable)builder.calendarConstraints);
        bundle.putInt(TITLE_TEXT_RES_ID_KEY, builder.titleTextResId);
        bundle.putCharSequence(TITLE_TEXT_KEY, builder.titleText);
        bundle.putInt(INPUT_MODE_KEY, builder.inputMode);
        materialDatePicker.setArguments(bundle);
        return materialDatePicker;
    }

    private void startPickerFragment() {
        this.calendar = MaterialCalendar.newInstance(this.dateSelector, this.getThemeResId(this.requireContext()), this.calendarConstraints);
        Object object = this.headerToggleButton.isChecked() ? MaterialTextInputPicker.newInstance(this.dateSelector, this.calendarConstraints) : this.calendar;
        this.pickerFragment = object;
        this.updateHeader();
        object = this.getChildFragmentManager().beginTransaction();
        ((FragmentTransaction)object).replace(R.id.mtrl_calendar_frame, this.pickerFragment);
        ((FragmentTransaction)object).commitNow();
        this.pickerFragment.addOnSelectionChangedListener(new OnSelectionChangedListener<S>(){

            @Override
            void onIncompleteSelectionChanged() {
                MaterialDatePicker.this.confirmButton.setEnabled(false);
            }

            @Override
            public void onSelectionChanged(S s) {
                MaterialDatePicker.this.updateHeader();
                MaterialDatePicker.this.confirmButton.setEnabled(MaterialDatePicker.this.dateSelector.isSelectionComplete());
            }
        });
    }

    public static long thisMonthInUtcMilliseconds() {
        return Month.current().timeInMillis;
    }

    public static long todayInUtcMilliseconds() {
        return UtcDates.getTodayCalendar().getTimeInMillis();
    }

    private void updateHeader() {
        String string2 = this.getHeaderText();
        this.headerSelectionText.setContentDescription((CharSequence)String.format(this.getString(R.string.mtrl_picker_announce_current_selection), string2));
        this.headerSelectionText.setText((CharSequence)string2);
    }

    private void updateToggleContentDescription(CheckableImageButton object) {
        object = this.headerToggleButton.isChecked() ? object.getContext().getString(R.string.mtrl_picker_toggle_to_calendar_input_mode) : object.getContext().getString(R.string.mtrl_picker_toggle_to_text_input_mode);
        this.headerToggleButton.setContentDescription((CharSequence)object);
    }

    public boolean addOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.onCancelListeners.add(onCancelListener);
    }

    public boolean addOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.onDismissListeners.add(onDismissListener);
    }

    public boolean addOnNegativeButtonClickListener(View.OnClickListener onClickListener) {
        return this.onNegativeButtonClickListeners.add(onClickListener);
    }

    public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.add(materialPickerOnPositiveButtonClickListener);
    }

    public void clearOnCancelListeners() {
        this.onCancelListeners.clear();
    }

    public void clearOnDismissListeners() {
        this.onDismissListeners.clear();
    }

    public void clearOnNegativeButtonClickListeners() {
        this.onNegativeButtonClickListeners.clear();
    }

    public void clearOnPositiveButtonClickListeners() {
        this.onPositiveButtonClickListeners.clear();
    }

    public String getHeaderText() {
        return this.dateSelector.getSelectionDisplayString(this.getContext());
    }

    public final S getSelection() {
        return this.dateSelector.getSelection();
    }

    @Override
    public final void onCancel(DialogInterface dialogInterface) {
        Iterator iterator2 = this.onCancelListeners.iterator();
        do {
            if (!iterator2.hasNext()) {
                super.onCancel(dialogInterface);
                return;
            }
            ((DialogInterface.OnCancelListener)iterator2.next()).onCancel(dialogInterface);
        } while (true);
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = this.getArguments();
        }
        this.overrideThemeResId = bundle2.getInt(OVERRIDE_THEME_RES_ID);
        this.dateSelector = (DateSelector)bundle2.getParcelable(DATE_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints)bundle2.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.titleTextResId = bundle2.getInt(TITLE_TEXT_RES_ID_KEY);
        this.titleText = bundle2.getCharSequence(TITLE_TEXT_KEY);
        this.inputMode = bundle2.getInt(INPUT_MODE_KEY);
    }

    @Override
    public final Dialog onCreateDialog(Bundle bundle) {
        MaterialShapeDrawable materialShapeDrawable;
        bundle = new Dialog(this.requireContext(), this.getThemeResId(this.requireContext()));
        Context context = bundle.getContext();
        this.fullscreen = MaterialDatePicker.isFullscreen(context);
        int n = MaterialAttributes.resolveOrThrow(context, R.attr.colorSurface, MaterialDatePicker.class.getCanonicalName());
        this.background = materialShapeDrawable = new MaterialShapeDrawable(context, null, R.attr.materialCalendarStyle, R.style.Widget_MaterialComponents_MaterialCalendar);
        materialShapeDrawable.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf((int)n));
        this.background.setElevation(ViewCompat.getElevation(bundle.getWindow().getDecorView()));
        return bundle;
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Object object;
        int n = this.fullscreen ? R.layout.mtrl_picker_fullscreen : R.layout.mtrl_picker_dialog;
        layoutInflater = layoutInflater.inflate(n, viewGroup);
        viewGroup = layoutInflater.getContext();
        if (this.fullscreen) {
            layoutInflater.findViewById(R.id.mtrl_calendar_frame).setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(MaterialDatePicker.getPaddedPickerWidth((Context)viewGroup), -2));
        } else {
            bundle = layoutInflater.findViewById(R.id.mtrl_calendar_main_pane);
            object = layoutInflater.findViewById(R.id.mtrl_calendar_frame);
            bundle.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(MaterialDatePicker.getPaddedPickerWidth((Context)viewGroup), -1));
            object.setMinimumHeight(MaterialDatePicker.getDialogPickerHeight(this.requireContext()));
        }
        bundle = (TextView)layoutInflater.findViewById(R.id.mtrl_picker_header_selection_text);
        this.headerSelectionText = bundle;
        ViewCompat.setAccessibilityLiveRegion((View)bundle, 1);
        this.headerToggleButton = (CheckableImageButton)layoutInflater.findViewById(R.id.mtrl_picker_header_toggle);
        bundle = (TextView)layoutInflater.findViewById(R.id.mtrl_picker_title_text);
        object = this.titleText;
        if (object != null) {
            bundle.setText((CharSequence)object);
        } else {
            bundle.setText(this.titleTextResId);
        }
        this.initHeaderToggle((Context)viewGroup);
        this.confirmButton = (Button)layoutInflater.findViewById(R.id.confirm_button);
        if (this.dateSelector.isSelectionComplete()) {
            this.confirmButton.setEnabled(true);
        } else {
            this.confirmButton.setEnabled(false);
        }
        this.confirmButton.setTag(CONFIRM_BUTTON_TAG);
        this.confirmButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = MaterialDatePicker.this.onPositiveButtonClickListeners.iterator();
                do {
                    if (!object.hasNext()) {
                        MaterialDatePicker.this.dismiss();
                        return;
                    }
                    ((MaterialPickerOnPositiveButtonClickListener)object.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
                } while (true);
            }
        });
        viewGroup = (Button)layoutInflater.findViewById(R.id.cancel_button);
        viewGroup.setTag(CANCEL_BUTTON_TAG);
        viewGroup.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Iterator iterator2 = MaterialDatePicker.this.onNegativeButtonClickListeners.iterator();
                do {
                    if (!iterator2.hasNext()) {
                        MaterialDatePicker.this.dismiss();
                        return;
                    }
                    ((View.OnClickListener)iterator2.next()).onClick(view);
                } while (true);
            }
        });
        return layoutInflater;
    }

    @Override
    public final void onDismiss(DialogInterface dialogInterface) {
        ViewGroup viewGroup = this.onDismissListeners.iterator();
        while (viewGroup.hasNext()) {
            ((DialogInterface.OnDismissListener)viewGroup.next()).onDismiss(dialogInterface);
        }
        viewGroup = (ViewGroup)this.getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(OVERRIDE_THEME_RES_ID, this.overrideThemeResId);
        bundle.putParcelable(DATE_SELECTOR_KEY, this.dateSelector);
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder(this.calendarConstraints);
        if (this.calendar.getCurrentMonth() != null) {
            builder.setOpenAt(this.calendar.getCurrentMonth().timeInMillis);
        }
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, (Parcelable)builder.build());
        bundle.putInt(TITLE_TEXT_RES_ID_KEY, this.titleTextResId);
        bundle.putCharSequence(TITLE_TEXT_KEY, this.titleText);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = this.requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable((Drawable)this.background);
        } else {
            window.setLayout(-2, -2);
            int n = this.getResources().getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(n, n, n, n);
            window.setBackgroundDrawable((Drawable)new InsetDrawable((Drawable)this.background, n, n, n, n));
            window.getDecorView().setOnTouchListener((View.OnTouchListener)new InsetDialogOnTouchListener(this.requireDialog(), rect));
        }
        this.startPickerFragment();
    }

    @Override
    public void onStop() {
        this.pickerFragment.clearOnSelectionChangedListeners();
        super.onStop();
    }

    public boolean removeOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.onCancelListeners.remove((Object)onCancelListener);
    }

    public boolean removeOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.onDismissListeners.remove((Object)onDismissListener);
    }

    public boolean removeOnNegativeButtonClickListener(View.OnClickListener onClickListener) {
        return this.onNegativeButtonClickListeners.remove((Object)onClickListener);
    }

    public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.remove(materialPickerOnPositiveButtonClickListener);
    }

    public static final class Builder<S> {
        CalendarConstraints calendarConstraints;
        final DateSelector<S> dateSelector;
        int inputMode = 0;
        int overrideThemeResId = 0;
        S selection = null;
        CharSequence titleText = null;
        int titleTextResId = 0;

        private Builder(DateSelector<S> dateSelector) {
            this.dateSelector = dateSelector;
        }

        public static <S> Builder<S> customDatePicker(DateSelector<S> dateSelector) {
            return new Builder<S>(dateSelector);
        }

        public static Builder<Long> datePicker() {
            return new Builder<Long>(new SingleDateSelector());
        }

        public static Builder<Pair<Long, Long>> dateRangePicker() {
            return new Builder<Pair<Long, Long>>(new RangeDateSelector());
        }

        public MaterialDatePicker<S> build() {
            S s;
            if (this.calendarConstraints == null) {
                this.calendarConstraints = new CalendarConstraints.Builder().build();
            }
            if (this.titleTextResId == 0) {
                this.titleTextResId = this.dateSelector.getDefaultTitleResId();
            }
            if ((s = this.selection) == null) return MaterialDatePicker.newInstance(this);
            this.dateSelector.setSelection(s);
            return MaterialDatePicker.newInstance(this);
        }

        public Builder<S> setCalendarConstraints(CalendarConstraints calendarConstraints) {
            this.calendarConstraints = calendarConstraints;
            return this;
        }

        public Builder<S> setInputMode(int n) {
            this.inputMode = n;
            return this;
        }

        public Builder<S> setSelection(S s) {
            this.selection = s;
            return this;
        }

        public Builder<S> setTheme(int n) {
            this.overrideThemeResId = n;
            return this;
        }

        public Builder<S> setTitleText(int n) {
            this.titleTextResId = n;
            this.titleText = null;
            return this;
        }

        public Builder<S> setTitleText(CharSequence charSequence) {
            this.titleText = charSequence;
            this.titleTextResId = 0;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InputMode {
    }

}

