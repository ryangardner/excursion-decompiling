/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.MaterialCalendarGridView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.MonthAdapter;

class MonthsPagerAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private final CalendarConstraints calendarConstraints;
    private final DateSelector<?> dateSelector;
    private final int itemHeight;
    private final MaterialCalendar.OnDayClickListener onDayClickListener;

    MonthsPagerAdapter(Context context, DateSelector<?> dateSelector, CalendarConstraints calendarConstraints, MaterialCalendar.OnDayClickListener onDayClickListener) {
        Month month = calendarConstraints.getStart();
        Month month2 = calendarConstraints.getEnd();
        Month month3 = calendarConstraints.getOpenAt();
        if (month.compareTo(month3) > 0) throw new IllegalArgumentException("firstPage cannot be after currentPage");
        if (month3.compareTo(month2) > 0) throw new IllegalArgumentException("currentPage cannot be after lastPage");
        int n = MonthAdapter.MAXIMUM_WEEKS;
        int n2 = MaterialCalendar.getDayHeight(context);
        int n3 = MaterialDatePicker.isFullscreen(context) ? MaterialCalendar.getDayHeight(context) : 0;
        this.itemHeight = n * n2 + n3;
        this.calendarConstraints = calendarConstraints;
        this.dateSelector = dateSelector;
        this.onDayClickListener = onDayClickListener;
        this.setHasStableIds(true);
    }

    @Override
    public int getItemCount() {
        return this.calendarConstraints.getMonthSpan();
    }

    @Override
    public long getItemId(int n) {
        return this.calendarConstraints.getStart().monthsLater(n).getStableId();
    }

    Month getPageMonth(int n) {
        return this.calendarConstraints.getStart().monthsLater(n);
    }

    CharSequence getPageTitle(int n) {
        return this.getPageMonth(n).getLongName();
    }

    int getPosition(Month month) {
        return this.calendarConstraints.getStart().monthsUntil(month);
    }

    @Override
    public void onBindViewHolder(ViewHolder object, int n) {
        Month month = this.calendarConstraints.getStart().monthsLater(n);
        ((ViewHolder)object).monthTitle.setText((CharSequence)month.getLongName());
        object = (MaterialCalendarGridView)((ViewHolder)object).monthGrid.findViewById(R.id.month_grid);
        if (((MaterialCalendarGridView)((Object)object)).getAdapter() != null && month.equals(object.getAdapter().month)) {
            ((MaterialCalendarGridView)((Object)object)).getAdapter().notifyDataSetChanged();
        } else {
            MonthAdapter monthAdapter = new MonthAdapter(month, this.dateSelector, this.calendarConstraints);
            object.setNumColumns(month.daysInWeek);
            ((MaterialCalendarGridView)((Object)object)).setAdapter((ListAdapter)monthAdapter);
        }
        object.setOnItemClickListener(new AdapterView.OnItemClickListener((MaterialCalendarGridView)((Object)object)){
            final /* synthetic */ MaterialCalendarGridView val$monthGrid;
            {
                this.val$monthGrid = materialCalendarGridView;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                if (!this.val$monthGrid.getAdapter().withinMonth(n)) return;
                MonthsPagerAdapter.this.onDayClickListener.onDayClick(this.val$monthGrid.getAdapter().getItem(n));
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        LinearLayout linearLayout = (LinearLayout)LayoutInflater.from((Context)viewGroup.getContext()).inflate(R.layout.mtrl_calendar_month_labeled, viewGroup, false);
        if (!MaterialDatePicker.isFullscreen(viewGroup.getContext())) return new ViewHolder(linearLayout, false);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new RecyclerView.LayoutParams(-1, this.itemHeight));
        return new ViewHolder(linearLayout, true);
    }

    public static class ViewHolder
    extends RecyclerView.ViewHolder {
        final MaterialCalendarGridView monthGrid;
        final TextView monthTitle;

        ViewHolder(LinearLayout linearLayout, boolean bl) {
            super((View)linearLayout);
            TextView textView;
            this.monthTitle = textView = (TextView)linearLayout.findViewById(R.id.month_title);
            ViewCompat.setAccessibilityHeading((View)textView, true);
            this.monthGrid = (MaterialCalendarGridView)linearLayout.findViewById(R.id.month_grid);
            if (bl) return;
            this.monthTitle.setVisibility(8);
        }
    }

}

