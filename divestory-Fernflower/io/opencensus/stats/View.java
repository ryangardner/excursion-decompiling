package io.opencensus.stats;

import io.opencensus.common.Duration;
import io.opencensus.common.Function;
import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import io.opencensus.tags.TagKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public abstract class View {
   static final int NAME_MAX_LENGTH = 255;
   private static final Comparator<TagKey> TAG_KEY_COMPARATOR = new Comparator<TagKey>() {
      public int compare(TagKey var1, TagKey var2) {
         return var1.getName().compareToIgnoreCase(var2.getName());
      }
   };

   View() {
   }

   public static View create(View.Name var0, String var1, Measure var2, Aggregation var3, List<TagKey> var4) {
      boolean var5;
      if ((new HashSet(var4)).size() == var4.size()) {
         var5 = true;
      } else {
         var5 = false;
      }

      Utils.checkArgument(var5, "Columns have duplicate.");
      return create(var0, var1, var2, var3, var4, View.AggregationWindow.Cumulative.CUMULATIVE);
   }

   @Deprecated
   public static View create(View.Name var0, String var1, Measure var2, Aggregation var3, List<TagKey> var4, View.AggregationWindow var5) {
      boolean var6;
      if ((new HashSet(var4)).size() == var4.size()) {
         var6 = true;
      } else {
         var6 = false;
      }

      Utils.checkArgument(var6, "Columns have duplicate.");
      ArrayList var7 = new ArrayList(var4);
      Collections.sort(var7, TAG_KEY_COMPARATOR);
      return new AutoValue_View(var0, var1, var2, var3, Collections.unmodifiableList(var7), var5);
   }

   public abstract Aggregation getAggregation();

   public abstract List<TagKey> getColumns();

   public abstract String getDescription();

   public abstract Measure getMeasure();

   public abstract View.Name getName();

   @Deprecated
   public abstract View.AggregationWindow getWindow();

   @Deprecated
   public abstract static class AggregationWindow {
      private AggregationWindow() {
      }

      // $FF: synthetic method
      AggregationWindow(Object var1) {
         this();
      }

      public abstract <T> T match(Function<? super View.AggregationWindow.Cumulative, T> var1, Function<? super View.AggregationWindow.Interval, T> var2, Function<? super View.AggregationWindow, T> var3);

      @Deprecated
      public abstract static class Cumulative extends View.AggregationWindow {
         private static final View.AggregationWindow.Cumulative CUMULATIVE = new AutoValue_View_AggregationWindow_Cumulative();

         Cumulative() {
            super(null);
         }

         public static View.AggregationWindow.Cumulative create() {
            return CUMULATIVE;
         }

         public final <T> T match(Function<? super View.AggregationWindow.Cumulative, T> var1, Function<? super View.AggregationWindow.Interval, T> var2, Function<? super View.AggregationWindow, T> var3) {
            return var1.apply(this);
         }
      }

      @Deprecated
      public abstract static class Interval extends View.AggregationWindow {
         private static final Duration ZERO = Duration.create(0L, 0);

         Interval() {
            super(null);
         }

         public static View.AggregationWindow.Interval create(Duration var0) {
            boolean var1;
            if (var0.compareTo(ZERO) > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            Utils.checkArgument(var1, "Duration must be positive");
            return new AutoValue_View_AggregationWindow_Interval(var0);
         }

         public abstract Duration getDuration();

         public final <T> T match(Function<? super View.AggregationWindow.Cumulative, T> var1, Function<? super View.AggregationWindow.Interval, T> var2, Function<? super View.AggregationWindow, T> var3) {
            return var2.apply(this);
         }
      }
   }

   public abstract static class Name {
      Name() {
      }

      public static View.Name create(String var0) {
         boolean var1;
         if (StringUtils.isPrintableString(var0) && var0.length() <= 255) {
            var1 = true;
         } else {
            var1 = false;
         }

         Utils.checkArgument(var1, "Name should be a ASCII string with a length no greater than 255 characters.");
         return new AutoValue_View_Name(var0);
      }

      public abstract String asString();
   }
}
