package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import java.util.Calendar;
import java.util.Iterator;

final class MaterialCalendarGridView extends GridView {
   private final Calendar dayCompute;

   public MaterialCalendarGridView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public MaterialCalendarGridView(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public MaterialCalendarGridView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.dayCompute = UtcDates.getUtcCalendar();
      if (MaterialDatePicker.isFullscreen(this.getContext())) {
         this.setNextFocusLeftId(R.id.cancel_button);
         this.setNextFocusRightId(R.id.confirm_button);
      }

      ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
         public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
            super.onInitializeAccessibilityNodeInfo(var1, var2);
            var2.setCollectionInfo((Object)null);
         }
      });
   }

   private void gainFocus(int var1, Rect var2) {
      if (var1 == 33) {
         this.setSelection(this.getAdapter().lastPositionInMonth());
      } else if (var1 == 130) {
         this.setSelection(this.getAdapter().firstPositionInMonth());
      } else {
         super.onFocusChanged(true, var1, var2);
      }

   }

   private static int horizontalMidPoint(View var0) {
      return var0.getLeft() + var0.getWidth() / 2;
   }

   private static boolean skipMonth(Long var0, Long var1, Long var2, Long var3) {
      boolean var4 = true;
      boolean var5 = var4;
      if (var0 != null) {
         var5 = var4;
         if (var1 != null) {
            var5 = var4;
            if (var2 != null) {
               if (var3 == null) {
                  var5 = var4;
               } else {
                  var5 = var4;
                  if (var2 <= var1) {
                     if (var3 < var0) {
                        var5 = var4;
                     } else {
                        var5 = false;
                     }
                  }
               }
            }
         }
      }

      return var5;
   }

   public MonthAdapter getAdapter() {
      return (MonthAdapter)super.getAdapter();
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.getAdapter().notifyDataSetChanged();
   }

   protected final void onDraw(Canvas var1) {
      super.onDraw(var1);
      MonthAdapter var2 = this.getAdapter();
      DateSelector var3 = var2.dateSelector;
      CalendarStyle var4 = var2.calendarStyle;
      Long var5 = var2.getItem(var2.firstPositionInMonth());
      Long var6 = var2.getItem(var2.lastPositionInMonth());
      Iterator var7 = var3.getSelectedRanges().iterator();

      while(true) {
         Pair var8;
         do {
            do {
               if (!var7.hasNext()) {
                  return;
               }

               var8 = (Pair)var7.next();
            } while(var8.first == null);
         } while(var8.second == null);

         long var9 = (Long)var8.first;
         long var11 = (Long)var8.second;
         if (skipMonth(var5, var6, var9, var11)) {
            return;
         }

         int var13;
         int var14;
         if (var9 < var5) {
            var13 = var2.firstPositionInMonth();
            if (var2.isFirstInRow(var13)) {
               var14 = 0;
            } else {
               var14 = this.getChildAt(var13 - 1).getRight();
            }
         } else {
            this.dayCompute.setTimeInMillis(var9);
            var13 = var2.dayToPosition(this.dayCompute.get(5));
            var14 = horizontalMidPoint(this.getChildAt(var13));
         }

         int var15;
         int var16;
         if (var11 > var6) {
            var15 = Math.min(var2.lastPositionInMonth(), this.getChildCount() - 1);
            if (var2.isLastInRow(var15)) {
               var16 = this.getWidth();
            } else {
               var16 = this.getChildAt(var15).getRight();
            }
         } else {
            this.dayCompute.setTimeInMillis(var11);
            var15 = var2.dayToPosition(this.dayCompute.get(5));
            var16 = horizontalMidPoint(this.getChildAt(var15));
         }

         int var17 = (int)var2.getItemId(var13);

         for(int var18 = (int)var2.getItemId(var15); var17 <= var18; ++var17) {
            int var19 = this.getNumColumns() * var17;
            int var20 = this.getNumColumns();
            View var26 = this.getChildAt(var19);
            int var21 = var26.getTop();
            int var22 = var4.day.getTopInset();
            int var23 = var26.getBottom();
            int var24 = var4.day.getBottomInset();
            int var25;
            if (var19 > var13) {
               var25 = 0;
            } else {
               var25 = var14;
            }

            if (var15 > var20 + var19 - 1) {
               var20 = this.getWidth();
            } else {
               var20 = var16;
            }

            var1.drawRect((float)var25, (float)(var21 + var22), (float)var20, (float)(var23 - var24), var4.rangeFill);
         }
      }
   }

   protected void onFocusChanged(boolean var1, int var2, Rect var3) {
      if (var1) {
         this.gainFocus(var2, var3);
      } else {
         super.onFocusChanged(false, var2, var3);
      }

   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if (!super.onKeyDown(var1, var2)) {
         return false;
      } else if (this.getSelectedItemPosition() != -1 && this.getSelectedItemPosition() < this.getAdapter().firstPositionInMonth()) {
         if (19 == var1) {
            this.setSelection(this.getAdapter().firstPositionInMonth());
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public final void setAdapter(ListAdapter var1) {
      if (var1 instanceof MonthAdapter) {
         super.setAdapter(var1);
      } else {
         throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName()));
      }
   }

   public void setSelection(int var1) {
      if (var1 < this.getAdapter().firstPositionInMonth()) {
         super.setSelection(this.getAdapter().firstPositionInMonth());
      } else {
         super.setSelection(var1);
      }

   }
}
