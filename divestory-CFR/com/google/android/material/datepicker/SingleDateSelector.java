/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateFormatTextWatcher;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.DateStrings;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.OnSelectionChangedListener;
import com.google.android.material.datepicker.UtcDates;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class SingleDateSelector
implements DateSelector<Long> {
    public static final Parcelable.Creator<SingleDateSelector> CREATOR = new Parcelable.Creator<SingleDateSelector>(){

        public SingleDateSelector createFromParcel(Parcel parcel) {
            SingleDateSelector singleDateSelector = new SingleDateSelector();
            singleDateSelector.selectedItem = (Long)parcel.readValue(Long.class.getClassLoader());
            return singleDateSelector;
        }

        public SingleDateSelector[] newArray(int n) {
            return new SingleDateSelector[n];
        }
    };
    private Long selectedItem;

    private void clearSelection() {
        this.selectedItem = null;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public int getDefaultThemeResId(Context context) {
        return MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
    }

    @Override
    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_date_header_title;
    }

    @Override
    public Collection<Long> getSelectedDays() {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        Long l = this.selectedItem;
        if (l == null) return arrayList;
        arrayList.add(l);
        return arrayList;
    }

    @Override
    public Collection<Pair<Long, Long>> getSelectedRanges() {
        return new ArrayList<Pair<Long, Long>>();
    }

    @Override
    public Long getSelection() {
        return this.selectedItem;
    }

    @Override
    public String getSelectionDisplayString(Context context) {
        context = context.getResources();
        Object object = this.selectedItem;
        if (object == null) {
            return context.getString(R.string.mtrl_picker_date_header_unselected);
        }
        object = DateStrings.getYearMonthDay((Long)object);
        return context.getString(R.string.mtrl_picker_date_header_selected, new Object[]{object});
    }

    @Override
    public boolean isSelectionComplete() {
        if (this.selectedItem == null) return false;
        return true;
    }

    @Override
    public View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle, CalendarConstraints calendarConstraints, final OnSelectionChangedListener<Long> onSelectionChangedListener) {
        layoutInflater = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date, (ViewGroup)object, false);
        object = (TextInputLayout)layoutInflater.findViewById(R.id.mtrl_picker_text_input_date);
        bundle = ((TextInputLayout)((Object)object)).getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            bundle.setInputType(17);
        }
        SimpleDateFormat simpleDateFormat = UtcDates.getTextInputFormat();
        String string2 = UtcDates.getTextInputHint(layoutInflater.getResources(), simpleDateFormat);
        Long l = this.selectedItem;
        if (l != null) {
            bundle.setText((CharSequence)simpleDateFormat.format(l));
        }
        bundle.addTextChangedListener((TextWatcher)new DateFormatTextWatcher(string2, simpleDateFormat, (TextInputLayout)((Object)object), calendarConstraints){

            @Override
            void onInvalidDate() {
                onSelectionChangedListener.onIncompleteSelectionChanged();
            }

            @Override
            void onValidDate(Long l) {
                if (l == null) {
                    SingleDateSelector.this.clearSelection();
                } else {
                    SingleDateSelector.this.select(l);
                }
                onSelectionChangedListener.onSelectionChanged(SingleDateSelector.this.getSelection());
            }
        });
        ViewUtils.requestFocusAndShowKeyboard((View)bundle);
        return layoutInflater;
    }

    @Override
    public void select(long l) {
        this.selectedItem = l;
    }

    @Override
    public void setSelection(Long l) {
        l = l == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay(l));
        this.selectedItem = l;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeValue((Object)this.selectedItem);
    }

}

