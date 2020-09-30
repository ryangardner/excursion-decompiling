package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Arrays;

public class DateValidatorPointForward implements CalendarConstraints.DateValidator {
   public static final Creator<DateValidatorPointForward> CREATOR = new Creator<DateValidatorPointForward>() {
      public DateValidatorPointForward createFromParcel(Parcel var1) {
         return new DateValidatorPointForward(var1.readLong());
      }

      public DateValidatorPointForward[] newArray(int var1) {
         return new DateValidatorPointForward[var1];
      }
   };
   private final long point;

   private DateValidatorPointForward(long var1) {
      this.point = var1;
   }

   // $FF: synthetic method
   DateValidatorPointForward(long var1, Object var3) {
      this(var1);
   }

   public static DateValidatorPointForward from(long var0) {
      return new DateValidatorPointForward(var0);
   }

   public static DateValidatorPointForward now() {
      return from(UtcDates.getTodayCalendar().getTimeInMillis());
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DateValidatorPointForward)) {
         return false;
      } else {
         DateValidatorPointForward var3 = (DateValidatorPointForward)var1;
         if (this.point != var3.point) {
            var2 = false;
         }

         return var2;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.point});
   }

   public boolean isValid(long var1) {
      boolean var3;
      if (var1 >= this.point) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeLong(this.point);
   }
}
