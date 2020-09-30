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

public class DateValidatorPointBackward
implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<DateValidatorPointBackward> CREATOR = new Parcelable.Creator<DateValidatorPointBackward>(){

        public DateValidatorPointBackward createFromParcel(Parcel parcel) {
            return new DateValidatorPointBackward(parcel.readLong());
        }

        public DateValidatorPointBackward[] newArray(int n) {
            return new DateValidatorPointBackward[n];
        }
    };
    private final long point;

    private DateValidatorPointBackward(long l) {
        this.point = l;
    }

    public static DateValidatorPointBackward before(long l) {
        return new DateValidatorPointBackward(l);
    }

    public static DateValidatorPointBackward now() {
        return DateValidatorPointBackward.before(UtcDates.getTodayCalendar().getTimeInMillis());
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof DateValidatorPointBackward)) {
            return false;
        }
        object = (DateValidatorPointBackward)object;
        if (this.point != ((DateValidatorPointBackward)object).point) return false;
        return bl;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.point});
    }

    @Override
    public boolean isValid(long l) {
        if (l > this.point) return false;
        return true;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.point);
    }

}

