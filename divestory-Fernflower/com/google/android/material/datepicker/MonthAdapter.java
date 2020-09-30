package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.material.R;
import java.util.Iterator;

class MonthAdapter extends BaseAdapter {
   static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendar().getMaximum(4);
   final CalendarConstraints calendarConstraints;
   CalendarStyle calendarStyle;
   final DateSelector<?> dateSelector;
   final Month month;

   MonthAdapter(Month var1, DateSelector<?> var2, CalendarConstraints var3) {
      this.month = var1;
      this.dateSelector = var2;
      this.calendarConstraints = var3;
   }

   private void initializeStyles(Context var1) {
      if (this.calendarStyle == null) {
         this.calendarStyle = new CalendarStyle(var1);
      }

   }

   int dayToPosition(int var1) {
      return this.firstPositionInMonth() + (var1 - 1);
   }

   int firstPositionInMonth() {
      return this.month.daysFromStartOfWeekToFirstOfMonth();
   }

   public int getCount() {
      return this.month.daysInMonth + this.firstPositionInMonth();
   }

   public Long getItem(int var1) {
      return var1 >= this.month.daysFromStartOfWeekToFirstOfMonth() && var1 <= this.lastPositionInMonth() ? this.month.getDay(this.positionToDay(var1)) : null;
   }

   public long getItemId(int var1) {
      return (long)(var1 / this.month.daysInWeek);
   }

   public TextView getView(int var1, View var2, ViewGroup var3) {
      this.initializeStyles(var3.getContext());
      TextView var4 = (TextView)var2;
      if (var2 == null) {
         var4 = (TextView)LayoutInflater.from(var3.getContext()).inflate(R.layout.mtrl_calendar_day, var3, false);
      }

      int var5 = var1 - this.firstPositionInMonth();
      long var6;
      if (var5 >= 0 && var5 < this.month.daysInMonth) {
         ++var5;
         var4.setTag(this.month);
         var4.setText(String.valueOf(var5));
         var6 = this.month.getDay(var5);
         if (this.month.year == Month.current().year) {
            var4.setContentDescription(DateStrings.getMonthDayOfWeekDay(var6));
         } else {
            var4.setContentDescription(DateStrings.getYearMonthDayOfWeekDay(var6));
         }

         var4.setVisibility(0);
         var4.setEnabled(true);
      } else {
         var4.setVisibility(8);
         var4.setEnabled(false);
      }

      Long var8 = this.getItem(var1);
      if (var8 == null) {
         return var4;
      } else if (this.calendarConstraints.getDateValidator().isValid(var8)) {
         var4.setEnabled(true);
         Iterator var9 = this.dateSelector.getSelectedDays().iterator();

         do {
            if (!var9.hasNext()) {
               if (UtcDates.getTodayCalendar().getTimeInMillis() == var8) {
                  this.calendarStyle.todayDay.styleItem(var4);
                  return var4;
               }

               this.calendarStyle.day.styleItem(var4);
               return var4;
            }

            var6 = (Long)var9.next();
         } while(UtcDates.canonicalYearMonthDay(var8) != UtcDates.canonicalYearMonthDay(var6));

         this.calendarStyle.selectedDay.styleItem(var4);
         return var4;
      } else {
         var4.setEnabled(false);
         this.calendarStyle.invalidDay.styleItem(var4);
         return var4;
      }
   }

   public boolean hasStableIds() {
      return true;
   }

   boolean isFirstInRow(int var1) {
      boolean var2;
      if (var1 % this.month.daysInWeek == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   boolean isLastInRow(int var1) {
      boolean var2 = true;
      if ((var1 + 1) % this.month.daysInWeek != 0) {
         var2 = false;
      }

      return var2;
   }

   int lastPositionInMonth() {
      return this.month.daysFromStartOfWeekToFirstOfMonth() + this.month.daysInMonth - 1;
   }

   int positionToDay(int var1) {
      return var1 - this.month.daysFromStartOfWeekToFirstOfMonth() + 1;
   }

   boolean withinMonth(int var1) {
      boolean var2;
      if (var1 >= this.firstPositionInMonth() && var1 <= this.lastPositionInMonth()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
