package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

final class Month implements Comparable<Month>, Parcelable {
   public static final Creator<Month> CREATOR = new Creator<Month>() {
      public Month createFromParcel(Parcel var1) {
         return Month.create(var1.readInt(), var1.readInt());
      }

      public Month[] newArray(int var1) {
         return new Month[var1];
      }
   };
   final int daysInMonth;
   final int daysInWeek;
   private final Calendar firstOfMonth;
   private final String longName;
   final int month;
   final long timeInMillis;
   final int year;

   private Month(Calendar var1) {
      var1.set(5, 1);
      var1 = UtcDates.getDayCopy(var1);
      this.firstOfMonth = var1;
      this.month = var1.get(2);
      this.year = this.firstOfMonth.get(1);
      this.daysInWeek = this.firstOfMonth.getMaximum(7);
      this.daysInMonth = this.firstOfMonth.getActualMaximum(5);
      this.longName = UtcDates.getYearMonthFormat().format(this.firstOfMonth.getTime());
      this.timeInMillis = this.firstOfMonth.getTimeInMillis();
   }

   static Month create(int var0, int var1) {
      Calendar var2 = UtcDates.getUtcCalendar();
      var2.set(1, var0);
      var2.set(2, var1);
      return new Month(var2);
   }

   static Month create(long var0) {
      Calendar var2 = UtcDates.getUtcCalendar();
      var2.setTimeInMillis(var0);
      return new Month(var2);
   }

   static Month current() {
      return new Month(UtcDates.getTodayCalendar());
   }

   public int compareTo(Month var1) {
      return this.firstOfMonth.compareTo(var1.firstOfMonth);
   }

   int daysFromStartOfWeekToFirstOfMonth() {
      int var1 = this.firstOfMonth.get(7) - this.firstOfMonth.getFirstDayOfWeek();
      int var2 = var1;
      if (var1 < 0) {
         var2 = var1 + this.daysInWeek;
      }

      return var2;
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Month)) {
         return false;
      } else {
         Month var3 = (Month)var1;
         if (this.month != var3.month || this.year != var3.year) {
            var2 = false;
         }

         return var2;
      }
   }

   long getDay(int var1) {
      Calendar var2 = UtcDates.getDayCopy(this.firstOfMonth);
      var2.set(5, var1);
      return var2.getTimeInMillis();
   }

   String getLongName() {
      return this.longName;
   }

   long getStableId() {
      return this.firstOfMonth.getTimeInMillis();
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.month, this.year});
   }

   Month monthsLater(int var1) {
      Calendar var2 = UtcDates.getDayCopy(this.firstOfMonth);
      var2.add(2, var1);
      return new Month(var2);
   }

   int monthsUntil(Month var1) {
      if (this.firstOfMonth instanceof GregorianCalendar) {
         return (var1.year - this.year) * 12 + (var1.month - this.month);
      } else {
         throw new IllegalArgumentException("Only Gregorian calendars are supported.");
      }
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeInt(this.year);
      var1.writeInt(this.month);
   }
}
