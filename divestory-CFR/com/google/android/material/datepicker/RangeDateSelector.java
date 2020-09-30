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
 *  android.util.DisplayMetrics
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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
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

public class RangeDateSelector
implements DateSelector<Pair<Long, Long>> {
    public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator<RangeDateSelector>(){

        public RangeDateSelector createFromParcel(Parcel parcel) {
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            rangeDateSelector.selectedStartItem = (Long)parcel.readValue(Long.class.getClassLoader());
            rangeDateSelector.selectedEndItem = (Long)parcel.readValue(Long.class.getClassLoader());
            return rangeDateSelector;
        }

        public RangeDateSelector[] newArray(int n) {
            return new RangeDateSelector[n];
        }
    };
    private final String invalidRangeEndError;
    private String invalidRangeStartError;
    private Long proposedTextEnd = null;
    private Long proposedTextStart = null;
    private Long selectedEndItem = null;
    private Long selectedStartItem = null;

    public RangeDateSelector() {
        this.invalidRangeEndError = " ";
    }

    private void clearInvalidRange(TextInputLayout textInputLayout, TextInputLayout textInputLayout2) {
        if (textInputLayout.getError() != null && this.invalidRangeStartError.contentEquals(textInputLayout.getError())) {
            textInputLayout.setError(null);
        }
        if (textInputLayout2.getError() == null) return;
        if (!" ".contentEquals(textInputLayout2.getError())) return;
        textInputLayout2.setError(null);
    }

    private boolean isValidRange(long l, long l2) {
        if (l > l2) return false;
        return true;
    }

    private void setInvalidRange(TextInputLayout textInputLayout, TextInputLayout textInputLayout2) {
        textInputLayout.setError(this.invalidRangeStartError);
        textInputLayout2.setError(" ");
    }

    private void updateIfValidTextProposal(TextInputLayout textInputLayout, TextInputLayout textInputLayout2, OnSelectionChangedListener<Pair<Long, Long>> onSelectionChangedListener) {
        Long l = this.proposedTextStart;
        if (l != null && this.proposedTextEnd != null) {
            if (this.isValidRange(l, this.proposedTextEnd)) {
                this.selectedStartItem = this.proposedTextStart;
                this.selectedEndItem = this.proposedTextEnd;
                onSelectionChangedListener.onSelectionChanged((Pair<Long, Long>)this.getSelection());
                return;
            }
            this.setInvalidRange(textInputLayout, textInputLayout2);
            onSelectionChangedListener.onIncompleteSelectionChanged();
            return;
        }
        this.clearInvalidRange(textInputLayout, textInputLayout2);
        onSelectionChangedListener.onIncompleteSelectionChanged();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public int getDefaultThemeResId(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int n = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis);
        if (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) > n) {
            n = R.attr.materialCalendarTheme;
            return MaterialAttributes.resolveOrThrow(context, n, MaterialDatePicker.class.getCanonicalName());
        }
        n = R.attr.materialCalendarFullscreenTheme;
        return MaterialAttributes.resolveOrThrow(context, n, MaterialDatePicker.class.getCanonicalName());
    }

    @Override
    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_range_header_title;
    }

    @Override
    public Collection<Long> getSelectedDays() {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        Long l = this.selectedStartItem;
        if (l != null) {
            arrayList.add(l);
        }
        if ((l = this.selectedEndItem) == null) return arrayList;
        arrayList.add(l);
        return arrayList;
    }

    @Override
    public Collection<Pair<Long, Long>> getSelectedRanges() {
        if (this.selectedStartItem == null) return new ArrayList<Pair<Long, Long>>();
        if (this.selectedEndItem == null) {
            return new ArrayList<Pair<Long, Long>>();
        }
        ArrayList<Pair<Long, Long>> arrayList = new ArrayList<Pair<Long, Long>>();
        arrayList.add(new Pair<Long, Long>(this.selectedStartItem, this.selectedEndItem));
        return arrayList;
    }

    @Override
    public Pair<Long, Long> getSelection() {
        return new Pair<Long, Long>(this.selectedStartItem, this.selectedEndItem);
    }

    @Override
    public String getSelectionDisplayString(Context context) {
        context = context.getResources();
        if (this.selectedStartItem == null && this.selectedEndItem == null) {
            return context.getString(R.string.mtrl_picker_range_header_unselected);
        }
        Long l = this.selectedEndItem;
        if (l == null) {
            return context.getString(R.string.mtrl_picker_range_header_only_start_selected, new Object[]{DateStrings.getDateString(this.selectedStartItem)});
        }
        Object object = this.selectedStartItem;
        if (object == null) {
            return context.getString(R.string.mtrl_picker_range_header_only_end_selected, new Object[]{DateStrings.getDateString(this.selectedEndItem)});
        }
        object = DateStrings.getDateRangeString((Long)object, l);
        return context.getString(R.string.mtrl_picker_range_header_selected, new Object[]{((Pair)object).first, ((Pair)object).second});
    }

    @Override
    public boolean isSelectionComplete() {
        Long l = this.selectedStartItem;
        if (l == null) return false;
        if (this.selectedEndItem == null) return false;
        if (!this.isValidRange(l, this.selectedEndItem)) return false;
        return true;
    }

    @Override
    public View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle, CalendarConstraints calendarConstraints, OnSelectionChangedListener<Pair<Long, Long>> onSelectionChangedListener) {
        View view = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date_range, (ViewGroup)object, false);
        object = (TextInputLayout)view.findViewById(R.id.mtrl_picker_text_input_range_start);
        TextInputLayout textInputLayout = (TextInputLayout)view.findViewById(R.id.mtrl_picker_text_input_range_end);
        layoutInflater = ((TextInputLayout)((Object)object)).getEditText();
        bundle = textInputLayout.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            layoutInflater.setInputType(17);
            bundle.setInputType(17);
        }
        this.invalidRangeStartError = view.getResources().getString(R.string.mtrl_picker_invalid_range);
        SimpleDateFormat simpleDateFormat = UtcDates.getTextInputFormat();
        Object object2 = this.selectedStartItem;
        if (object2 != null) {
            layoutInflater.setText((CharSequence)simpleDateFormat.format(object2));
            this.proposedTextStart = this.selectedStartItem;
        }
        if ((object2 = this.selectedEndItem) != null) {
            bundle.setText((CharSequence)simpleDateFormat.format(object2));
            this.proposedTextEnd = this.selectedEndItem;
        }
        object2 = UtcDates.getTextInputHint(view.getResources(), simpleDateFormat);
        layoutInflater.addTextChangedListener((TextWatcher)new DateFormatTextWatcher((String)object2, simpleDateFormat, (TextInputLayout)((Object)object), calendarConstraints, (TextInputLayout)((Object)object), textInputLayout, onSelectionChangedListener){
            final /* synthetic */ TextInputLayout val$endTextInput;
            final /* synthetic */ OnSelectionChangedListener val$listener;
            final /* synthetic */ TextInputLayout val$startTextInput;
            {
                this.val$startTextInput = textInputLayout2;
                this.val$endTextInput = textInputLayout3;
                this.val$listener = onSelectionChangedListener;
                super(string2, dateFormat, textInputLayout, calendarConstraints);
            }

            @Override
            void onInvalidDate() {
                RangeDateSelector.this.proposedTextStart = null;
                RangeDateSelector.this.updateIfValidTextProposal(this.val$startTextInput, this.val$endTextInput, this.val$listener);
            }

            @Override
            void onValidDate(Long l) {
                RangeDateSelector.this.proposedTextStart = l;
                RangeDateSelector.this.updateIfValidTextProposal(this.val$startTextInput, this.val$endTextInput, this.val$listener);
            }
        });
        bundle.addTextChangedListener((TextWatcher)new DateFormatTextWatcher((String)object2, simpleDateFormat, textInputLayout, calendarConstraints, (TextInputLayout)((Object)object), textInputLayout, onSelectionChangedListener){
            final /* synthetic */ TextInputLayout val$endTextInput;
            final /* synthetic */ OnSelectionChangedListener val$listener;
            final /* synthetic */ TextInputLayout val$startTextInput;
            {
                this.val$startTextInput = textInputLayout2;
                this.val$endTextInput = textInputLayout3;
                this.val$listener = onSelectionChangedListener;
                super(string2, dateFormat, textInputLayout, calendarConstraints);
            }

            @Override
            void onInvalidDate() {
                RangeDateSelector.this.proposedTextEnd = null;
                RangeDateSelector.this.updateIfValidTextProposal(this.val$startTextInput, this.val$endTextInput, this.val$listener);
            }

            @Override
            void onValidDate(Long l) {
                RangeDateSelector.this.proposedTextEnd = l;
                RangeDateSelector.this.updateIfValidTextProposal(this.val$startTextInput, this.val$endTextInput, this.val$listener);
            }
        });
        ViewUtils.requestFocusAndShowKeyboard((View)layoutInflater);
        return view;
    }

    @Override
    public void select(long l) {
        Long l2 = this.selectedStartItem;
        if (l2 == null) {
            this.selectedStartItem = l;
            return;
        }
        if (this.selectedEndItem == null && this.isValidRange(l2, l)) {
            this.selectedEndItem = l;
            return;
        }
        this.selectedEndItem = null;
        this.selectedStartItem = l;
    }

    @Override
    public void setSelection(Pair<Long, Long> object) {
        if (((Pair)object).first != null && ((Pair)object).second != null) {
            Preconditions.checkArgument(this.isValidRange((Long)((Pair)object).first, (Long)((Pair)object).second));
        }
        Object object2 = ((Pair)object).first;
        Object var3_3 = null;
        object2 = object2 == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay((Long)((Pair)object).first));
        this.selectedStartItem = object2;
        object = ((Pair)object).second == null ? var3_3 : Long.valueOf(UtcDates.canonicalYearMonthDay((Long)((Pair)object).second));
        this.selectedEndItem = object;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeValue((Object)this.selectedStartItem);
        parcel.writeValue((Object)this.selectedEndItem);
    }

}

