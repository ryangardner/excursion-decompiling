package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.R;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialDatePicker<S> extends DialogFragment {
   private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
   static final Object CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
   static final Object CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
   private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
   public static final int INPUT_MODE_CALENDAR = 0;
   private static final String INPUT_MODE_KEY = "INPUT_MODE_KEY";
   public static final int INPUT_MODE_TEXT = 1;
   private static final String OVERRIDE_THEME_RES_ID = "OVERRIDE_THEME_RES_ID";
   private static final String TITLE_TEXT_KEY = "TITLE_TEXT_KEY";
   private static final String TITLE_TEXT_RES_ID_KEY = "TITLE_TEXT_RES_ID_KEY";
   static final Object TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
   private MaterialShapeDrawable background;
   private MaterialCalendar<S> calendar;
   private CalendarConstraints calendarConstraints;
   private Button confirmButton;
   private DateSelector<S> dateSelector;
   private boolean fullscreen;
   private TextView headerSelectionText;
   private CheckableImageButton headerToggleButton;
   private int inputMode;
   private final LinkedHashSet<OnCancelListener> onCancelListeners = new LinkedHashSet();
   private final LinkedHashSet<OnDismissListener> onDismissListeners = new LinkedHashSet();
   private final LinkedHashSet<OnClickListener> onNegativeButtonClickListeners = new LinkedHashSet();
   private final LinkedHashSet<MaterialPickerOnPositiveButtonClickListener<? super S>> onPositiveButtonClickListeners = new LinkedHashSet();
   private int overrideThemeResId;
   private PickerFragment<S> pickerFragment;
   private CharSequence titleText;
   private int titleTextResId;

   private static Drawable createHeaderToggleDrawable(Context var0) {
      StateListDrawable var1 = new StateListDrawable();
      Drawable var2 = AppCompatResources.getDrawable(var0, R.drawable.material_ic_calendar_black_24dp);
      var1.addState(new int[]{16842912}, var2);
      Drawable var3 = AppCompatResources.getDrawable(var0, R.drawable.material_ic_edit_black_24dp);
      var1.addState(new int[0], var3);
      return var1;
   }

   private static int getDialogPickerHeight(Context var0) {
      Resources var1 = var0.getResources();
      return var1.getDimensionPixelSize(R.dimen.mtrl_calendar_navigation_height) + var1.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_top_padding) + var1.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_bottom_padding) + var1.getDimensionPixelSize(R.dimen.mtrl_calendar_days_of_week_height) + MonthAdapter.MAXIMUM_WEEKS * var1.getDimensionPixelSize(R.dimen.mtrl_calendar_day_height) + (MonthAdapter.MAXIMUM_WEEKS - 1) * var1.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_vertical_padding) + var1.getDimensionPixelOffset(R.dimen.mtrl_calendar_bottom_padding);
   }

   private static int getPaddedPickerWidth(Context var0) {
      Resources var3 = var0.getResources();
      int var1 = var3.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
      int var2 = Month.current().daysInWeek;
      return var1 * 2 + var3.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width) * var2 + (var2 - 1) * var3.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_horizontal_padding);
   }

   private int getThemeResId(Context var1) {
      int var2 = this.overrideThemeResId;
      return var2 != 0 ? var2 : this.dateSelector.getDefaultThemeResId(var1);
   }

   private void initHeaderToggle(Context var1) {
      this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
      this.headerToggleButton.setImageDrawable(createHeaderToggleDrawable(var1));
      CheckableImageButton var3 = this.headerToggleButton;
      boolean var2;
      if (this.inputMode != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      ViewCompat.setAccessibilityDelegate(this.headerToggleButton, (AccessibilityDelegateCompat)null);
      this.updateToggleContentDescription(this.headerToggleButton);
      this.headerToggleButton.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            MaterialDatePicker.this.confirmButton.setEnabled(MaterialDatePicker.this.dateSelector.isSelectionComplete());
            MaterialDatePicker.this.headerToggleButton.toggle();
            MaterialDatePicker var2 = MaterialDatePicker.this;
            var2.updateToggleContentDescription(var2.headerToggleButton);
            MaterialDatePicker.this.startPickerFragment();
         }
      });
   }

   static boolean isFullscreen(Context var0) {
      TypedArray var2 = var0.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(var0, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[]{16843277});
      boolean var1 = var2.getBoolean(0, false);
      var2.recycle();
      return var1;
   }

   static <S> MaterialDatePicker<S> newInstance(MaterialDatePicker.Builder<S> var0) {
      MaterialDatePicker var1 = new MaterialDatePicker();
      Bundle var2 = new Bundle();
      var2.putInt("OVERRIDE_THEME_RES_ID", var0.overrideThemeResId);
      var2.putParcelable("DATE_SELECTOR_KEY", var0.dateSelector);
      var2.putParcelable("CALENDAR_CONSTRAINTS_KEY", var0.calendarConstraints);
      var2.putInt("TITLE_TEXT_RES_ID_KEY", var0.titleTextResId);
      var2.putCharSequence("TITLE_TEXT_KEY", var0.titleText);
      var2.putInt("INPUT_MODE_KEY", var0.inputMode);
      var1.setArguments(var2);
      return var1;
   }

   private void startPickerFragment() {
      this.calendar = MaterialCalendar.newInstance(this.dateSelector, this.getThemeResId(this.requireContext()), this.calendarConstraints);
      Object var1;
      if (this.headerToggleButton.isChecked()) {
         var1 = MaterialTextInputPicker.newInstance(this.dateSelector, this.calendarConstraints);
      } else {
         var1 = this.calendar;
      }

      this.pickerFragment = (PickerFragment)var1;
      this.updateHeader();
      FragmentTransaction var2 = this.getChildFragmentManager().beginTransaction();
      var2.replace(R.id.mtrl_calendar_frame, this.pickerFragment);
      var2.commitNow();
      this.pickerFragment.addOnSelectionChangedListener(new OnSelectionChangedListener<S>() {
         void onIncompleteSelectionChanged() {
            MaterialDatePicker.this.confirmButton.setEnabled(false);
         }

         public void onSelectionChanged(S var1) {
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
      String var1 = this.getHeaderText();
      this.headerSelectionText.setContentDescription(String.format(this.getString(R.string.mtrl_picker_announce_current_selection), var1));
      this.headerSelectionText.setText(var1);
   }

   private void updateToggleContentDescription(CheckableImageButton var1) {
      String var2;
      if (this.headerToggleButton.isChecked()) {
         var2 = var1.getContext().getString(R.string.mtrl_picker_toggle_to_calendar_input_mode);
      } else {
         var2 = var1.getContext().getString(R.string.mtrl_picker_toggle_to_text_input_mode);
      }

      this.headerToggleButton.setContentDescription(var2);
   }

   public boolean addOnCancelListener(OnCancelListener var1) {
      return this.onCancelListeners.add(var1);
   }

   public boolean addOnDismissListener(OnDismissListener var1) {
      return this.onDismissListeners.add(var1);
   }

   public boolean addOnNegativeButtonClickListener(OnClickListener var1) {
      return this.onNegativeButtonClickListeners.add(var1);
   }

   public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> var1) {
      return this.onPositiveButtonClickListeners.add(var1);
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

   public final void onCancel(DialogInterface var1) {
      Iterator var2 = this.onCancelListeners.iterator();

      while(var2.hasNext()) {
         ((OnCancelListener)var2.next()).onCancel(var1);
      }

      super.onCancel(var1);
   }

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      Bundle var2 = var1;
      if (var1 == null) {
         var2 = this.getArguments();
      }

      this.overrideThemeResId = var2.getInt("OVERRIDE_THEME_RES_ID");
      this.dateSelector = (DateSelector)var2.getParcelable("DATE_SELECTOR_KEY");
      this.calendarConstraints = (CalendarConstraints)var2.getParcelable("CALENDAR_CONSTRAINTS_KEY");
      this.titleTextResId = var2.getInt("TITLE_TEXT_RES_ID_KEY");
      this.titleText = var2.getCharSequence("TITLE_TEXT_KEY");
      this.inputMode = var2.getInt("INPUT_MODE_KEY");
   }

   public final Dialog onCreateDialog(Bundle var1) {
      Dialog var5 = new Dialog(this.requireContext(), this.getThemeResId(this.requireContext()));
      Context var2 = var5.getContext();
      this.fullscreen = isFullscreen(var2);
      int var3 = MaterialAttributes.resolveOrThrow(var2, R.attr.colorSurface, MaterialDatePicker.class.getCanonicalName());
      MaterialShapeDrawable var4 = new MaterialShapeDrawable(var2, (AttributeSet)null, R.attr.materialCalendarStyle, R.style.Widget_MaterialComponents_MaterialCalendar);
      this.background = var4;
      var4.initializeElevationOverlay(var2);
      this.background.setFillColor(ColorStateList.valueOf(var3));
      this.background.setElevation(ViewCompat.getElevation(var5.getWindow().getDecorView()));
      return var5;
   }

   public final View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      int var4;
      if (this.fullscreen) {
         var4 = R.layout.mtrl_picker_fullscreen;
      } else {
         var4 = R.layout.mtrl_picker_dialog;
      }

      View var6 = var1.inflate(var4, var2);
      Context var7 = var6.getContext();
      if (this.fullscreen) {
         var6.findViewById(R.id.mtrl_calendar_frame).setLayoutParams(new LayoutParams(getPaddedPickerWidth(var7), -2));
      } else {
         View var9 = var6.findViewById(R.id.mtrl_calendar_main_pane);
         View var5 = var6.findViewById(R.id.mtrl_calendar_frame);
         var9.setLayoutParams(new LayoutParams(getPaddedPickerWidth(var7), -1));
         var5.setMinimumHeight(getDialogPickerHeight(this.requireContext()));
      }

      TextView var10 = (TextView)var6.findViewById(R.id.mtrl_picker_header_selection_text);
      this.headerSelectionText = var10;
      ViewCompat.setAccessibilityLiveRegion(var10, 1);
      this.headerToggleButton = (CheckableImageButton)var6.findViewById(R.id.mtrl_picker_header_toggle);
      var10 = (TextView)var6.findViewById(R.id.mtrl_picker_title_text);
      CharSequence var11 = this.titleText;
      if (var11 != null) {
         var10.setText(var11);
      } else {
         var10.setText(this.titleTextResId);
      }

      this.initHeaderToggle(var7);
      this.confirmButton = (Button)var6.findViewById(R.id.confirm_button);
      if (this.dateSelector.isSelectionComplete()) {
         this.confirmButton.setEnabled(true);
      } else {
         this.confirmButton.setEnabled(false);
      }

      this.confirmButton.setTag(CONFIRM_BUTTON_TAG);
      this.confirmButton.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Iterator var2 = MaterialDatePicker.this.onPositiveButtonClickListeners.iterator();

            while(var2.hasNext()) {
               ((MaterialPickerOnPositiveButtonClickListener)var2.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
            }

            MaterialDatePicker.this.dismiss();
         }
      });
      Button var8 = (Button)var6.findViewById(R.id.cancel_button);
      var8.setTag(CANCEL_BUTTON_TAG);
      var8.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Iterator var2 = MaterialDatePicker.this.onNegativeButtonClickListeners.iterator();

            while(var2.hasNext()) {
               ((OnClickListener)var2.next()).onClick(var1);
            }

            MaterialDatePicker.this.dismiss();
         }
      });
      return var6;
   }

   public final void onDismiss(DialogInterface var1) {
      Iterator var2 = this.onDismissListeners.iterator();

      while(var2.hasNext()) {
         ((OnDismissListener)var2.next()).onDismiss(var1);
      }

      ViewGroup var3 = (ViewGroup)this.getView();
      if (var3 != null) {
         var3.removeAllViews();
      }

      super.onDismiss(var1);
   }

   public final void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      var1.putInt("OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
      var1.putParcelable("DATE_SELECTOR_KEY", this.dateSelector);
      CalendarConstraints.Builder var2 = new CalendarConstraints.Builder(this.calendarConstraints);
      if (this.calendar.getCurrentMonth() != null) {
         var2.setOpenAt(this.calendar.getCurrentMonth().timeInMillis);
      }

      var1.putParcelable("CALENDAR_CONSTRAINTS_KEY", var2.build());
      var1.putInt("TITLE_TEXT_RES_ID_KEY", this.titleTextResId);
      var1.putCharSequence("TITLE_TEXT_KEY", this.titleText);
   }

   public void onStart() {
      super.onStart();
      Window var1 = this.requireDialog().getWindow();
      if (this.fullscreen) {
         var1.setLayout(-1, -1);
         var1.setBackgroundDrawable(this.background);
      } else {
         var1.setLayout(-2, -2);
         int var2 = this.getResources().getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
         Rect var3 = new Rect(var2, var2, var2, var2);
         var1.setBackgroundDrawable(new InsetDrawable(this.background, var2, var2, var2, var2));
         var1.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(this.requireDialog(), var3));
      }

      this.startPickerFragment();
   }

   public void onStop() {
      this.pickerFragment.clearOnSelectionChangedListeners();
      super.onStop();
   }

   public boolean removeOnCancelListener(OnCancelListener var1) {
      return this.onCancelListeners.remove(var1);
   }

   public boolean removeOnDismissListener(OnDismissListener var1) {
      return this.onDismissListeners.remove(var1);
   }

   public boolean removeOnNegativeButtonClickListener(OnClickListener var1) {
      return this.onNegativeButtonClickListeners.remove(var1);
   }

   public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> var1) {
      return this.onPositiveButtonClickListeners.remove(var1);
   }

   public static final class Builder<S> {
      CalendarConstraints calendarConstraints;
      final DateSelector<S> dateSelector;
      int inputMode = 0;
      int overrideThemeResId = 0;
      S selection = null;
      CharSequence titleText = null;
      int titleTextResId = 0;

      private Builder(DateSelector<S> var1) {
         this.dateSelector = var1;
      }

      public static <S> MaterialDatePicker.Builder<S> customDatePicker(DateSelector<S> var0) {
         return new MaterialDatePicker.Builder(var0);
      }

      public static MaterialDatePicker.Builder<Long> datePicker() {
         return new MaterialDatePicker.Builder(new SingleDateSelector());
      }

      public static MaterialDatePicker.Builder<Pair<Long, Long>> dateRangePicker() {
         return new MaterialDatePicker.Builder(new RangeDateSelector());
      }

      public MaterialDatePicker<S> build() {
         if (this.calendarConstraints == null) {
            this.calendarConstraints = (new CalendarConstraints.Builder()).build();
         }

         if (this.titleTextResId == 0) {
            this.titleTextResId = this.dateSelector.getDefaultTitleResId();
         }

         Object var1 = this.selection;
         if (var1 != null) {
            this.dateSelector.setSelection(var1);
         }

         return MaterialDatePicker.newInstance(this);
      }

      public MaterialDatePicker.Builder<S> setCalendarConstraints(CalendarConstraints var1) {
         this.calendarConstraints = var1;
         return this;
      }

      public MaterialDatePicker.Builder<S> setInputMode(int var1) {
         this.inputMode = var1;
         return this;
      }

      public MaterialDatePicker.Builder<S> setSelection(S var1) {
         this.selection = var1;
         return this;
      }

      public MaterialDatePicker.Builder<S> setTheme(int var1) {
         this.overrideThemeResId = var1;
         return this;
      }

      public MaterialDatePicker.Builder<S> setTitleText(int var1) {
         this.titleTextResId = var1;
         this.titleText = null;
         return this;
      }

      public MaterialDatePicker.Builder<S> setTitleText(CharSequence var1) {
         this.titleText = var1;
         this.titleTextResId = 0;
         return this;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface InputMode {
   }
}
