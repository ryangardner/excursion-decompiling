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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.UtcDates;
import java.util.Arrays;

public class DateValidatorPointForward
implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<DateValidatorPointForward> CREATOR = new Parcelable.Creator<DateValidatorPointForward>(){

        public DateValidatorPointForward createFromParcel(Parcel parcel) {
            return new DateValidatorPointForward(parcel.readLong());
        }

        public DateValidatorPointForward[] newArray(int n) {
            return new DateValidatorPointForward[n];
        }
    };
    private final long point;

    private DateValidatorPointForward(long l) {
        this.point = l;
    }

    public static DateValidatorPointForward from(long l) {
        return new DateValidatorPointForward(l);
    }

    public static DateValidatorPointForward now() {
        return DateValidatorPointForward.from(UtcDates.getTodayCalendar().getTimeInMillis());
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof DateValidatorPointForward)) {
            return false;
        }
        object = (DateValidatorPointForward)object;
        if (this.point != ((DateValidatorPointForward)object).point) return false;
        return bl;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.point});
    }

    @Override
    public boolean isValid(long l) {
        if (l < this.point) return false;
        return true;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.point);
    }

}

