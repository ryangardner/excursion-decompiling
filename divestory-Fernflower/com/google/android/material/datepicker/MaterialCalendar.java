package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
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
import java.util.Calendar;
import java.util.Iterator;

public final class MaterialCalendar<S> extends PickerFragment<S> {
   private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
   private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
   private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
   static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
   static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
   static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
   static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";
   private static final int SMOOTH_SCROLL_MAX = 3;
   private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
   private CalendarConstraints calendarConstraints;
   private MaterialCalendar.CalendarSelector calendarSelector;
   private CalendarStyle calendarStyle;
   private Month current;
   private DateSelector<S> dateSelector;
   private View dayFrame;
   private RecyclerView recyclerView;
   private int themeResId;
   private View yearFrame;
   private RecyclerView yearSelector;

   private void addActionsToMonthNavigation(View var1, final MonthsPagerAdapter var2) {
      final MaterialButton var3 = (MaterialButton)var1.findViewById(R.id.month_navigation_fragment_toggle);
      var3.setTag(SELECTOR_TOGGLE_TAG);
      ViewCompat.setAccessibilityDelegate(var3, new AccessibilityDelegateCompat() {
         public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
            super.onInitializeAccessibilityNodeInfo(var1, var2);
            String var3;
            if (MaterialCalendar.this.dayFrame.getVisibility() == 0) {
               var3 = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection);
            } else {
               var3 = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
            }

            var2.setHintText(var3);
         }
      });
      MaterialButton var4 = (MaterialButton)var1.findViewById(R.id.month_navigation_previous);
      var4.setTag(NAVIGATION_PREV_TAG);
      MaterialButton var5 = (MaterialButton)var1.findViewById(R.id.month_navigation_next);
      var5.setTag(NAVIGATION_NEXT_TAG);
      this.yearFrame = var1.findViewById(R.id.mtrl_calendar_year_selector_frame);
      this.dayFrame = var1.findViewById(R.id.mtrl_calendar_day_selector_frame);
      this.setSelector(MaterialCalendar.CalendarSelector.DAY);
      var3.setText(this.current.getLongName());
      this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
         public void onScrollStateChanged(RecyclerView var1, int var2x) {
            if (var2x == 0) {
               CharSequence var3x = var3.getText();
               if (VERSION.SDK_INT >= 16) {
                  var1.announceForAccessibility(var3x);
               } else {
                  var1.sendAccessibilityEvent(2048);
               }
            }

         }

         public void onScrolled(RecyclerView var1, int var2x, int var3x) {
            if (var2x < 0) {
               var2x = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
            } else {
               var2x = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
            }

            MaterialCalendar.this.current = var2.getPageMonth(var2x);
            var3.setText(var2.getPageTitle(var2x));
         }
      });
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            MaterialCalendar.this.toggleVisibleSelector();
         }
      });
      var5.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2x = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition() + 1;
            if (var2x < MaterialCalendar.this.recyclerView.getAdapter().getItemCount()) {
               MaterialCalendar.this.setCurrentMonth(var2.getPageMonth(var2x));
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2x = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition() - 1;
            if (var2x >= 0) {
               MaterialCalendar.this.setCurrentMonth(var2.getPageMonth(var2x));
            }

         }
      });
   }

   private RecyclerView.ItemDecoration createItemDecoration() {
      return new RecyclerView.ItemDecoration() {
         private final Calendar endItem = UtcDates.getUtcCalendar();
         private final Calendar startItem = UtcDates.getUtcCalendar();

         public void onDraw(Canvas var1, RecyclerView var2, RecyclerView.State var3) {
            if (var2.getAdapter() instanceof YearGridAdapter && var2.getLayoutManager() instanceof GridLayoutManager) {
               YearGridAdapter var18 = (YearGridAdapter)var2.getAdapter();
               GridLayoutManager var4 = (GridLayoutManager)var2.getLayoutManager();
               Iterator var5 = MaterialCalendar.this.dateSelector.getSelectedRanges().iterator();

               while(true) {
                  Pair var6;
                  do {
                     do {
                        if (!var5.hasNext()) {
                           return;
                        }

                        var6 = (Pair)var5.next();
                     } while(var6.first == null);
                  } while(var6.second == null);

                  this.startItem.setTimeInMillis((Long)var6.first);
                  this.endItem.setTimeInMillis((Long)var6.second);
                  int var7 = var18.getPositionForYear(this.startItem.get(1));
                  int var8 = var18.getPositionForYear(this.endItem.get(1));
                  View var9 = var4.findViewByPosition(var7);
                  View var10 = var4.findViewByPosition(var8);
                  int var11 = var7 / var4.getSpanCount();
                  int var12 = var8 / var4.getSpanCount();

                  for(var8 = var11; var8 <= var12; ++var8) {
                     View var19 = var4.findViewByPosition(var4.getSpanCount() * var8);
                     if (var19 != null) {
                        int var13 = var19.getTop();
                        int var14 = MaterialCalendar.this.calendarStyle.year.getTopInset();
                        int var15 = var19.getBottom();
                        int var16 = MaterialCalendar.this.calendarStyle.year.getBottomInset();
                        if (var8 == var11) {
                           var7 = var9.getLeft() + var9.getWidth() / 2;
                        } else {
                           var7 = 0;
                        }

                        int var17;
                        if (var8 == var12) {
                           var17 = var10.getLeft() + var10.getWidth() / 2;
                        } else {
                           var17 = var2.getWidth();
                        }

                        var1.drawRect((float)var7, (float)(var13 + var14), (float)var17, (float)(var15 - var16), MaterialCalendar.this.calendarStyle.rangeFill);
                     }
                  }
               }
            }
         }
      };
   }

   static int getDayHeight(Context var0) {
      return var0.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
   }

   static <T> MaterialCalendar<T> newInstance(DateSelector<T> var0, int var1, CalendarConstraints var2) {
      MaterialCalendar var3 = new MaterialCalendar();
      Bundle var4 = new Bundle();
      var4.putInt("THEME_RES_ID_KEY", var1);
      var4.putParcelable("GRID_SELECTOR_KEY", var0);
      var4.putParcelable("CALENDAR_CONSTRAINTS_KEY", var2);
      var4.putParcelable("CURRENT_MONTH_KEY", var2.getOpenAt());
      var3.setArguments(var4);
      return var3;
   }

   private void postSmoothRecyclerViewScroll(final int var1) {
      this.recyclerView.post(new Runnable() {
         public void run() {
            MaterialCalendar.this.recyclerView.smoothScrollToPosition(var1);
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

   public DateSelector<S> getDateSelector() {
      return this.dateSelector;
   }

   LinearLayoutManager getLayoutManager() {
      return (LinearLayoutManager)this.recyclerView.getLayoutManager();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Bundle var2 = var1;
      if (var1 == null) {
         var2 = this.getArguments();
      }

      this.themeResId = var2.getInt("THEME_RES_ID_KEY");
      this.dateSelector = (DateSelector)var2.getParcelable("GRID_SELECTOR_KEY");
      this.calendarConstraints = (CalendarConstraints)var2.getParcelable("CALENDAR_CONSTRAINTS_KEY");
      this.current = (Month)var2.getParcelable("CURRENT_MONTH_KEY");
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      ContextThemeWrapper var11 = new ContextThemeWrapper(this.getContext(), this.themeResId);
      this.calendarStyle = new CalendarStyle(var11);
      var1 = var1.cloneInContext(var11);
      Month var4 = this.calendarConstraints.getStart();
      int var5;
      final byte var6;
      if (MaterialDatePicker.isFullscreen(var11)) {
         var5 = R.layout.mtrl_calendar_vertical;
         var6 = 1;
      } else {
         var5 = R.layout.mtrl_calendar_horizontal;
         var6 = 0;
      }

      View var7 = var1.inflate(var5, var2, false);
      GridView var8 = (GridView)var7.findViewById(R.id.mtrl_calendar_days_of_week);
      ViewCompat.setAccessibilityDelegate(var8, new AccessibilityDelegateCompat() {
         public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
            super.onInitializeAccessibilityNodeInfo(var1, var2);
            var2.setCollectionInfo((Object)null);
         }
      });
      var8.setAdapter(new DaysOfWeekAdapter());
      var8.setNumColumns(var4.daysInWeek);
      var8.setEnabled(false);
      this.recyclerView = (RecyclerView)var7.findViewById(R.id.mtrl_calendar_months);
      SmoothCalendarLayoutManager var9 = new SmoothCalendarLayoutManager(this.getContext(), var6, false) {
         protected void calculateExtraLayoutSpace(RecyclerView.State var1, int[] var2) {
            if (var6 == 0) {
               var2[0] = MaterialCalendar.this.recyclerView.getWidth();
               var2[1] = MaterialCalendar.this.recyclerView.getWidth();
            } else {
               var2[0] = MaterialCalendar.this.recyclerView.getHeight();
               var2[1] = MaterialCalendar.this.recyclerView.getHeight();
            }

         }
      };
      this.recyclerView.setLayoutManager(var9);
      this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
      MonthsPagerAdapter var10 = new MonthsPagerAdapter(var11, this.dateSelector, this.calendarConstraints, new MaterialCalendar.OnDayClickListener() {
         public void onDayClick(long var1) {
            if (MaterialCalendar.this.calendarConstraints.getDateValidator().isValid(var1)) {
               MaterialCalendar.this.dateSelector.select(var1);
               Iterator var3 = MaterialCalendar.this.onSelectionChangedListeners.iterator();

               while(var3.hasNext()) {
                  ((OnSelectionChangedListener)var3.next()).onSelectionChanged(MaterialCalendar.this.dateSelector.getSelection());
               }

               MaterialCalendar.this.recyclerView.getAdapter().notifyDataSetChanged();
               if (MaterialCalendar.this.yearSelector != null) {
                  MaterialCalendar.this.yearSelector.getAdapter().notifyDataSetChanged();
               }
            }

         }
      });
      this.recyclerView.setAdapter(var10);
      var5 = var11.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
      RecyclerView var12 = (RecyclerView)var7.findViewById(R.id.mtrl_calendar_year_selector_frame);
      this.yearSelector = var12;
      if (var12 != null) {
         var12.setHasFixedSize(true);
         this.yearSelector.setLayoutManager(new GridLayoutManager(var11, var5, 1, false));
         this.yearSelector.setAdapter(new YearGridAdapter(this));
         this.yearSelector.addItemDecoration(this.createItemDecoration());
      }

      if (var7.findViewById(R.id.month_navigation_fragment_toggle) != null) {
         this.addActionsToMonthNavigation(var7, var10);
      }

      if (!MaterialDatePicker.isFullscreen(var11)) {
         (new LinearSnapHelper()).attachToRecyclerView(this.recyclerView);
      }

      this.recyclerView.scrollToPosition(var10.getPosition(this.current));
      return var7;
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      var1.putInt("THEME_RES_ID_KEY", this.themeResId);
      var1.putParcelable("GRID_SELECTOR_KEY", this.dateSelector);
      var1.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
      var1.putParcelable("CURRENT_MONTH_KEY", this.current);
   }

   void setCurrentMonth(Month var1) {
      MonthsPagerAdapter var2 = (MonthsPagerAdapter)this.recyclerView.getAdapter();
      int var3 = var2.getPosition(var1);
      int var4 = var3 - var2.getPosition(this.current);
      int var5 = Math.abs(var4);
      boolean var6 = true;
      boolean var7;
      if (var5 > 3) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var4 <= 0) {
         var6 = false;
      }

      this.current = var1;
      if (var7 && var6) {
         this.recyclerView.scrollToPosition(var3 - 3);
         this.postSmoothRecyclerViewScroll(var3);
      } else if (var7) {
         this.recyclerView.scrollToPosition(var3 + 3);
         this.postSmoothRecyclerViewScroll(var3);
      } else {
         this.postSmoothRecyclerViewScroll(var3);
      }

   }

   void setSelector(MaterialCalendar.CalendarSelector var1) {
      this.calendarSelector = var1;
      if (var1 == MaterialCalendar.CalendarSelector.YEAR) {
         this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter)this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
         this.yearFrame.setVisibility(0);
         this.dayFrame.setVisibility(8);
      } else if (var1 == MaterialCalendar.CalendarSelector.DAY) {
         this.yearFrame.setVisibility(8);
         this.dayFrame.setVisibility(0);
         this.setCurrentMonth(this.current);
      }

   }

   void toggleVisibleSelector() {
      if (this.calendarSelector == MaterialCalendar.CalendarSelector.YEAR) {
         this.setSelector(MaterialCalendar.CalendarSelector.DAY);
      } else if (this.calendarSelector == MaterialCalendar.CalendarSelector.DAY) {
         this.setSelector(MaterialCalendar.CalendarSelector.YEAR);
      }

   }

   static enum CalendarSelector {
      DAY,
      YEAR;

      static {
         MaterialCalendar.CalendarSelector var0 = new MaterialCalendar.CalendarSelector("YEAR", 1);
         YEAR = var0;
      }
   }

   interface OnDayClickListener {
      void onDayClick(long var1);
   }
}
