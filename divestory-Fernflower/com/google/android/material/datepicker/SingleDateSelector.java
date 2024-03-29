package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class SingleDateSelector implements DateSelector<Long> {
   public static final Creator<SingleDateSelector> CREATOR = new Creator<SingleDateSelector>() {
      public SingleDateSelector createFromParcel(Parcel var1) {
         SingleDateSelector var2 = new SingleDateSelector();
         var2.selectedItem = (Long)var1.readValue(Long.class.getClassLoader());
         return var2;
      }

      public SingleDateSelector[] newArray(int var1) {
         return new SingleDateSelector[var1];
      }
   };
   private Long selectedItem;

   private void clearSelection() {
      this.selectedItem = null;
   }

   public int describeContents() {
      return 0;
   }

   public int getDefaultThemeResId(Context var1) {
      return MaterialAttributes.resolveOrThrow(var1, R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
   }

   public int getDefaultTitleResId() {
      return R.string.mtrl_picker_date_header_title;
   }

   public Collection<Long> getSelectedDays() {
      ArrayList var1 = new ArrayList();
      Long var2 = this.selectedItem;
      if (var2 != null) {
         var1.add(var2);
      }

      return var1;
   }

   public Collection<Pair<Long, Long>> getSelectedRanges() {
      return new ArrayList();
   }

   public Long getSelection() {
      return this.selectedItem;
   }

   public String getSelectionDisplayString(Context var1) {
      Resources var3 = var1.getResources();
      Long var2 = this.selectedItem;
      if (var2 == null) {
         return var3.getString(R.string.mtrl_picker_date_header_unselected);
      } else {
         String var4 = DateStrings.getYearMonthDay(var2);
         return var3.getString(R.string.mtrl_picker_date_header_selected, new Object[]{var4});
      }
   }

   public boolean isSelectionComplete() {
      boolean var1;
      if (this.selectedItem != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public View onCreateTextInputView(LayoutInflater var1, ViewGroup var2, Bundle var3, CalendarConstraints var4, final OnSelectionChangedListener<Long> var5) {
      View var9 = var1.inflate(R.layout.mtrl_picker_text_input_date, var2, false);
      TextInputLayout var10 = (TextInputLayout)var9.findViewById(R.id.mtrl_picker_text_input_date);
      EditText var11 = var10.getEditText();
      if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
         var11.setInputType(17);
      }

      SimpleDateFormat var6 = UtcDates.getTextInputFormat();
      String var7 = UtcDates.getTextInputHint(var9.getResources(), var6);
      Long var8 = this.selectedItem;
      if (var8 != null) {
         var11.setText(var6.format(var8));
      }

      var11.addTextChangedListener(new DateFormatTextWatcher(var7, var6, var10, var4) {
         void onInvalidDate() {
            var5.onIncompleteSelectionChanged();
         }

         void onValidDate(Long var1) {
            if (var1 == null) {
               SingleDateSelector.this.clearSelection();
            } else {
               SingleDateSelector.this.select(var1);
            }

            var5.onSelectionChanged(SingleDateSelector.this.getSelection());
         }
      });
      ViewUtils.requestFocusAndShowKeyboard(var11);
      return var9;
   }

   public void select(long var1) {
      this.selectedItem = var1;
   }

   public void setSelection(Long var1) {
      if (var1 == null) {
         var1 = null;
      } else {
         var1 = UtcDates.canonicalYearMonthDay(var1);
      }

      this.selectedItem = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeValue(this.selectedItem);
   }
}
