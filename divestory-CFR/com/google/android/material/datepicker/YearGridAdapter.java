/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CalendarItemStyle;
import com.google.android.material.datepicker.CalendarStyle;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.UtcDates;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

class YearGridAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private final MaterialCalendar<?> materialCalendar;

    YearGridAdapter(MaterialCalendar<?> materialCalendar) {
        this.materialCalendar = materialCalendar;
    }

    private View.OnClickListener createYearClickListener(final int n) {
        return new View.OnClickListener(){

            public void onClick(View object) {
                object = Month.create(n, YearGridAdapter.access$000((YearGridAdapter)YearGridAdapter.this).getCurrentMonth().month);
                object = YearGridAdapter.this.materialCalendar.getCalendarConstraints().clamp((Month)object);
                YearGridAdapter.this.materialCalendar.setCurrentMonth((Month)object);
                YearGridAdapter.this.materialCalendar.setSelector(MaterialCalendar.CalendarSelector.DAY);
            }
        };
    }

    @Override
    public int getItemCount() {
        return this.materialCalendar.getCalendarConstraints().getYearSpan();
    }

    int getPositionForYear(int n) {
        return n - this.materialCalendar.getCalendarConstraints().getStart().year;
    }

    int getYearForPosition(int n) {
        return this.materialCalendar.getCalendarConstraints().getStart().year + n;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        n = this.getYearForPosition(n);
        Object object = viewHolder.textView.getContext().getString(R.string.mtrl_picker_navigate_to_year_description);
        viewHolder.textView.setText((CharSequence)String.format(Locale.getDefault(), "%d", n));
        viewHolder.textView.setContentDescription((CharSequence)String.format((String)object, n));
        CalendarStyle calendarStyle = this.materialCalendar.getCalendarStyle();
        Calendar calendar = UtcDates.getTodayCalendar();
        object = calendar.get(1) == n ? calendarStyle.todayYear : calendarStyle.year;
        Iterator<Long> iterator2 = this.materialCalendar.getDateSelector().getSelectedDays().iterator();
        do {
            if (!iterator2.hasNext()) {
                ((CalendarItemStyle)object).styleItem(viewHolder.textView);
                viewHolder.textView.setOnClickListener(this.createYearClickListener(n));
                return;
            }
            calendar.setTimeInMillis(iterator2.next());
            if (calendar.get(1) != n) continue;
            object = calendarStyle.selectedYear;
        } while (true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder((TextView)LayoutInflater.from((Context)viewGroup.getContext()).inflate(R.layout.mtrl_calendar_year, viewGroup, false));
    }

    public static class ViewHolder
    extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(TextView textView) {
            super((View)textView);
            this.textView = textView;
        }
    }

}

