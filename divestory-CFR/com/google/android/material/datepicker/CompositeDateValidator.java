/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.util.Preconditions;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CompositeDateValidator
implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<CompositeDateValidator> CREATOR = new Parcelable.Creator<CompositeDateValidator>(){

        public CompositeDateValidator createFromParcel(Parcel parcel) {
            return new CompositeDateValidator(Preconditions.checkNotNull(parcel.readArrayList(CalendarConstraints.DateValidator.class.getClassLoader())));
        }

        public CompositeDateValidator[] newArray(int n) {
            return new CompositeDateValidator[n];
        }
    };
    private final List<CalendarConstraints.DateValidator> validators;

    private CompositeDateValidator(List<CalendarConstraints.DateValidator> list) {
        this.validators = list;
    }

    public static CalendarConstraints.DateValidator allOf(List<CalendarConstraints.DateValidator> list) {
        return new CompositeDateValidator(list);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CompositeDateValidator)) {
            return false;
        }
        object = (CompositeDateValidator)object;
        return this.validators.equals(((CompositeDateValidator)object).validators);
    }

    public int hashCode() {
        return this.validators.hashCode();
    }

    @Override
    public boolean isValid(long l) {
        CalendarConstraints.DateValidator dateValidator;
        Iterator<CalendarConstraints.DateValidator> iterator2 = this.validators.iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while ((dateValidator = iterator2.next()) == null || dateValidator.isValid(l));
        return false;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeList(this.validators);
    }

}

