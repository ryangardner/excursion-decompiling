/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.OnSelectionChangedListener;
import com.google.android.material.datepicker.PickerFragment;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialTextInputPicker<S>
extends PickerFragment<S> {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
    private CalendarConstraints calendarConstraints;
    private DateSelector<S> dateSelector;

    static <T> MaterialTextInputPicker<T> newInstance(DateSelector<T> dateSelector, CalendarConstraints calendarConstraints) {
        MaterialTextInputPicker<S> materialTextInputPicker = new MaterialTextInputPicker<S>();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DATE_SELECTOR_KEY, dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, (Parcelable)calendarConstraints);
        materialTextInputPicker.setArguments(bundle);
        return materialTextInputPicker;
    }

    @Override
    public DateSelector<S> getDateSelector() {
        DateSelector<S> dateSelector = this.dateSelector;
        if (dateSelector == null) throw new IllegalStateException("dateSelector should not be null. Use MaterialTextInputPicker#newInstance() to create this fragment with a DateSelector, and call this method after the fragment has been created.");
        return dateSelector;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = this.getArguments();
        }
        this.dateSelector = (DateSelector)bundle2.getParcelable(DATE_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints)bundle2.getParcelable(CALENDAR_CONSTRAINTS_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.dateSelector.onCreateTextInputView(layoutInflater, viewGroup, bundle, this.calendarConstraints, new OnSelectionChangedListener<S>(){

            @Override
            void onIncompleteSelectionChanged() {
                Iterator iterator2 = MaterialTextInputPicker.this.onSelectionChangedListeners.iterator();
                while (iterator2.hasNext()) {
                    ((OnSelectionChangedListener)iterator2.next()).onIncompleteSelectionChanged();
                }
            }

            @Override
            public void onSelectionChanged(S s) {
                Iterator iterator2 = MaterialTextInputPicker.this.onSelectionChangedListeners.iterator();
                while (iterator2.hasNext()) {
                    ((OnSelectionChangedListener)iterator2.next()).onSelectionChanged(s);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(DATE_SELECTOR_KEY, this.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, (Parcelable)this.calendarConstraints);
    }

}

