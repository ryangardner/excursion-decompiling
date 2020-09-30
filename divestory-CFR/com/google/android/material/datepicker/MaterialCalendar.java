/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.ContextThemeWrapper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.GridView
 *  android.widget.ListAdapter
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CalendarItemStyle;
import com.google.android.material.datepicker.CalendarStyle;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.DaysOfWeekAdapter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.MonthsPagerAdapter;
import com.google.android.material.datepicker.OnSelectionChangedListener;
import com.google.android.material.datepicker.PickerFragment;
import com.google.android.material.datepicker.SmoothCalendarLayoutManager;
import com.google.android.material.datepicker.UtcDates;
import com.google.android.material.datepicker.YearGridAdapter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class MaterialCalendar<S>
extends PickerFragment<S> {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
    private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    static final Object NAVIGATION_NEXT_TAG;
    static final Object NAVIGATION_PREV_TAG;
    static final Object SELECTOR_TOGGLE_TAG;
    private static final int SMOOTH_SCROLL_MAX = 3;
    private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
    private CalendarConstraints calendarConstraints;
    private CalendarSelector calendarSelector;
    private CalendarStyle calendarStyle;
    private Month current;
    private DateSelector<S> dateSelector;
    private View dayFrame;
    private RecyclerView recyclerView;
    private int themeResId;
    private View yearFrame;
    private RecyclerView yearSelector;

    static {
        NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
        NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
        SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";
    }

    static /* synthetic */ CalendarStyle access$400(MaterialCalendar materialCalendar) {
        return materialCalendar.calendarStyle;
    }

    private void addActionsToMonthNavigation(View view, final MonthsPagerAdapter monthsPagerAdapter) {
        final MaterialButton materialButton = (MaterialButton)view.findViewById(R.id.month_navigation_fragment_toggle);
        materialButton.setTag(SELECTOR_TOGGLE_TAG);
        ViewCompat.setAccessibilityDelegate((View)materialButton, new AccessibilityDelegateCompat(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
                object = MaterialCalendar.this.dayFrame.getVisibility() == 0 ? MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection) : MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
                accessibilityNodeInfoCompat.setHintText((CharSequence)object);
            }
        });
        MaterialButton materialButton2 = (MaterialButton)view.findViewById(R.id.month_navigation_previous);
        materialButton2.setTag(NAVIGATION_PREV_TAG);
        MaterialButton materialButton3 = (MaterialButton)view.findViewById(R.id.month_navigation_next);
        materialButton3.setTag(NAVIGATION_NEXT_TAG);
        this.yearFrame = view.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.dayFrame = view.findViewById(R.id.mtrl_calendar_day_selector_frame);
        this.setSelector(CalendarSelector.DAY);
        materialButton.setText((CharSequence)this.current.getLongName());
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int n) {
                if (n != 0) return;
                CharSequence charSequence = materialButton.getText();
                if (Build.VERSION.SDK_INT >= 16) {
                    recyclerView.announceForAccessibility(charSequence);
                    return;
                }
                recyclerView.sendAccessibilityEvent(2048);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int n, int n2) {
                n = n < 0 ? MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition() : MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                MaterialCalendar.this.current = monthsPagerAdapter.getPageMonth(n);
                materialButton.setText(monthsPagerAdapter.getPageTitle(n));
            }
        });
        materialButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MaterialCalendar.this.toggleVisibleSelector();
            }
        });
        materialButton3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                int n = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition() + 1;
                if (n >= MaterialCalendar.this.recyclerView.getAdapter().getItemCount()) return;
                MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(n));
            }
        });
        materialButton2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                int n = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition() - 1;
                if (n < 0) return;
                MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(n));
            }
        });
    }

    private RecyclerView.ItemDecoration createItemDecoration() {
        return new RecyclerView.ItemDecoration(){
            private final Calendar endItem = UtcDates.getUtcCalendar();
            private final Calendar startItem = UtcDates.getUtcCalendar();

            /*
             * Unable to fully structure code
             */
            @Override
            public void onDraw(Canvas var1_1, RecyclerView var2_2, RecyclerView.State var3_3) {
                if (var2_2.getAdapter() instanceof YearGridAdapter == false) return;
                if (!(var2_2.getLayoutManager() instanceof GridLayoutManager)) {
                    return;
                }
                var3_3 = (YearGridAdapter)var2_2.getAdapter();
                var4_4 = (GridLayoutManager)var2_2.getLayoutManager();
                var5_5 = MaterialCalendar.access$200(MaterialCalendar.this).getSelectedRanges().iterator();
                block0 : do lbl-1000: // 3 sources:
                {
                    if (var5_5.hasNext() == false) return;
                    var6_6 = var5_5.next();
                    if (var6_6.first == null || var6_6.second == null) ** GOTO lbl-1000
                    this.startItem.setTimeInMillis((Long)var6_6.first);
                    this.endItem.setTimeInMillis((Long)var6_6.second);
                    var7_7 = var3_3.getPositionForYear(this.startItem.get(1));
                    var8_8 = var3_3.getPositionForYear(this.endItem.get(1));
                    var9_9 = var4_4.findViewByPosition(var7_7);
                    var10_10 = var4_4.findViewByPosition(var8_8);
                    var11_11 = var7_7 / var4_4.getSpanCount();
                    var12_12 = var8_8 / var4_4.getSpanCount();
                    var8_8 = var11_11;
                    do {
                        if (var8_8 > var12_12) continue block0;
                        var6_6 = var4_4.findViewByPosition(var4_4.getSpanCount() * var8_8);
                        if (var6_6 != null) {
                            var13_13 = var6_6.getTop();
                            var14_14 = MaterialCalendar.access$400((MaterialCalendar)MaterialCalendar.this).year.getTopInset();
                            var15_15 = var6_6.getBottom();
                            var16_16 = MaterialCalendar.access$400((MaterialCalendar)MaterialCalendar.this).year.getBottomInset();
                            var7_7 = var8_8 == var11_11 ? var9_9.getLeft() + var9_9.getWidth() / 2 : 0;
                            var17_17 = var8_8 == var12_12 ? var10_10.getLeft() + var10_10.getWidth() / 2 : var2_2.getWidth();
                            var1_1.drawRect((float)var7_7, (float)(var13_13 + var14_14), (float)var17_17, (float)(var15_15 - var16_16), MaterialCalendar.access$400((MaterialCalendar)MaterialCalendar.this).rangeFill);
                        }
                        ++var8_8;
                    } while (true);
                    break;
                } while (true);
            }
        };
    }

    static int getDayHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
    }

    static <T> MaterialCalendar<T> newInstance(DateSelector<T> dateSelector, int n, CalendarConstraints calendarConstraints) {
        MaterialCalendar<S> materialCalendar = new MaterialCalendar<S>();
        Bundle bundle = new Bundle();
        bundle.putInt(THEME_RES_ID_KEY, n);
        bundle.putParcelable(GRID_SELECTOR_KEY, dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, (Parcelable)calendarConstraints);
        bundle.putParcelable(CURRENT_MONTH_KEY, (Parcelable)calendarConstraints.getOpenAt());
        materialCalendar.setArguments(bundle);
        return materialCalendar;
    }

    private void postSmoothRecyclerViewScroll(final int n) {
        this.recyclerView.post(new Runnable(){

            @Override
            public void run() {
                MaterialCalendar.this.recyclerView.smoothScrollToPosition(n);
            }
        });
    }

    CalendarConstraints getCalendarConstraints() {
        return this.calendarConstraints;
    }

    CalendarStyle getCalendarStyle() {
        return this.calendarStyle;
    }

    Month getCurrentMonth() {
        return this.current;
    }

    @Override
    public DateSelector<S> getDateSelector() {
        return this.dateSelector;
    }

    LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager)this.recyclerView.getLayoutManager();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = this.getArguments();
        }
        this.themeResId = bundle2.getInt(THEME_RES_ID_KEY);
        this.dateSelector = (DateSelector)bundle2.getParcelable(GRID_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints)bundle2.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.current = (Month)bundle2.getParcelable(CURRENT_MONTH_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        int n;
        int n2;
        bundle = new ContextThemeWrapper(this.getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle((Context)bundle);
        layoutInflater = layoutInflater.cloneInContext((Context)bundle);
        Object object2 = this.calendarConstraints.getStart();
        if (MaterialDatePicker.isFullscreen((Context)bundle)) {
            n2 = R.layout.mtrl_calendar_vertical;
            n = 1;
        } else {
            n2 = R.layout.mtrl_calendar_horizontal;
            n = 0;
        }
        layoutInflater = layoutInflater.inflate(n2, (ViewGroup)object, false);
        object = (GridView)layoutInflater.findViewById(R.id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate((View)object, new AccessibilityDelegateCompat(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
        object.setAdapter((ListAdapter)new DaysOfWeekAdapter());
        object.setNumColumns(((Month)object2).daysInWeek);
        object.setEnabled(false);
        this.recyclerView = (RecyclerView)layoutInflater.findViewById(R.id.mtrl_calendar_months);
        object = new SmoothCalendarLayoutManager(this.getContext(), n, false){

            @Override
            protected void calculateExtraLayoutSpace(RecyclerView.State state, int[] arrn) {
                if (n == 0) {
                    arrn[0] = MaterialCalendar.this.recyclerView.getWidth();
                    arrn[1] = MaterialCalendar.this.recyclerView.getWidth();
                    return;
                }
                arrn[0] = MaterialCalendar.this.recyclerView.getHeight();
                arrn[1] = MaterialCalendar.this.recyclerView.getHeight();
            }
        };
        this.recyclerView.setLayoutManager((RecyclerView.LayoutManager)object);
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        object = new MonthsPagerAdapter((Context)bundle, this.dateSelector, this.calendarConstraints, new OnDayClickListener(){

            @Override
            public void onDayClick(long l) {
                if (!MaterialCalendar.this.calendarConstraints.getDateValidator().isValid(l)) return;
                MaterialCalendar.this.dateSelector.select(l);
                Iterator iterator2 = MaterialCalendar.this.onSelectionChangedListeners.iterator();
                do {
                    if (!iterator2.hasNext()) {
                        MaterialCalendar.this.recyclerView.getAdapter().notifyDataSetChanged();
                        if (MaterialCalendar.this.yearSelector == null) return;
                        MaterialCalendar.this.yearSelector.getAdapter().notifyDataSetChanged();
                        return;
                    }
                    ((OnSelectionChangedListener)iterator2.next()).onSelectionChanged(MaterialCalendar.this.dateSelector.getSelection());
                } while (true);
            }
        });
        this.recyclerView.setAdapter((RecyclerView.Adapter)object);
        n2 = bundle.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
        this.yearSelector = object2 = (RecyclerView)layoutInflater.findViewById(R.id.mtrl_calendar_year_selector_frame);
        if (object2 != null) {
            ((RecyclerView)object2).setHasFixedSize(true);
            this.yearSelector.setLayoutManager(new GridLayoutManager((Context)bundle, n2, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(this.createItemDecoration());
        }
        if (layoutInflater.findViewById(R.id.month_navigation_fragment_toggle) != null) {
            this.addActionsToMonthNavigation((View)layoutInflater, (MonthsPagerAdapter)object);
        }
        if (!MaterialDatePicker.isFullscreen((Context)bundle)) {
            new LinearSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(((MonthsPagerAdapter)object).getPosition(this.current));
        return layoutInflater;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(THEME_RES_ID_KEY, this.themeResId);
        bundle.putParcelable(GRID_SELECTOR_KEY, this.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, (Parcelable)this.calendarConstraints);
        bundle.putParcelable(CURRENT_MONTH_KEY, (Parcelable)this.current);
    }

    void setCurrentMonth(Month month) {
        MonthsPagerAdapter monthsPagerAdapter = (MonthsPagerAdapter)this.recyclerView.getAdapter();
        int n = monthsPagerAdapter.getPosition(month);
        int n2 = n - monthsPagerAdapter.getPosition(this.current);
        int n3 = Math.abs(n2);
        boolean bl = true;
        n3 = n3 > 3 ? 1 : 0;
        if (n2 <= 0) {
            bl = false;
        }
        this.current = month;
        if (n3 != 0 && bl) {
            this.recyclerView.scrollToPosition(n - 3);
            this.postSmoothRecyclerViewScroll(n);
            return;
        }
        if (n3 != 0) {
            this.recyclerView.scrollToPosition(n + 3);
            this.postSmoothRecyclerViewScroll(n);
            return;
        }
        this.postSmoothRecyclerViewScroll(n);
    }

    void setSelector(CalendarSelector calendarSelector) {
        this.calendarSelector = calendarSelector;
        if (calendarSelector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter)this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
            return;
        }
        if (calendarSelector != CalendarSelector.DAY) return;
        this.yearFrame.setVisibility(8);
        this.dayFrame.setVisibility(0);
        this.setCurrentMonth(this.current);
    }

    void toggleVisibleSelector() {
        if (this.calendarSelector == CalendarSelector.YEAR) {
            this.setSelector(CalendarSelector.DAY);
            return;
        }
        if (this.calendarSelector != CalendarSelector.DAY) return;
        this.setSelector(CalendarSelector.YEAR);
    }

    static final class CalendarSelector
    extends Enum<CalendarSelector> {
        private static final /* synthetic */ CalendarSelector[] $VALUES;
        public static final /* enum */ CalendarSelector DAY;
        public static final /* enum */ CalendarSelector YEAR;

        static {
            CalendarSelector calendarSelector;
            DAY = new CalendarSelector();
            YEAR = calendarSelector = new CalendarSelector();
            $VALUES = new CalendarSelector[]{DAY, calendarSelector};
        }

        public static CalendarSelector valueOf(String string2) {
            return Enum.valueOf(CalendarSelector.class, string2);
        }

        public static CalendarSelector[] values() {
            return (CalendarSelector[])$VALUES.clone();
        }
    }

    static interface OnDayClickListener {
        public void onDayClick(long var1);
    }

}

