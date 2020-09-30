package io.opencensus.stats;

import io.opencensus.common.Duration;
import io.opencensus.common.Function;
import io.opencensus.common.Functions;
import io.opencensus.common.Timestamp;
import io.opencensus.tags.TagValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ViewData {
   ViewData() {
   }

   private static void checkAggregation(final Aggregation var0, final AggregationData var1, final Measure var2) {
      var0.match(new Function<Aggregation.Sum, Void>() {
         public Void apply(Aggregation.Sum var1x) {
            var2.match(new Function<Measure.MeasureDouble, Void>() {
               public Void apply(Measure.MeasureDouble var1x) {
                  ViewData.throwIfAggregationMismatch(var1 instanceof AggregationData.SumDataDouble, var0, var1);
                  return null;
               }
            }, new Function<Measure.MeasureLong, Void>() {
               public Void apply(Measure.MeasureLong var1x) {
                  ViewData.throwIfAggregationMismatch(var1 instanceof AggregationData.SumDataLong, var0, var1);
                  return null;
               }
            }, Functions.throwAssertionError());
            return null;
         }
      }, new Function<Aggregation.Count, Void>() {
         public Void apply(Aggregation.Count var1x) {
            AggregationData var2 = var1;
            ViewData.throwIfAggregationMismatch(var2 instanceof AggregationData.CountData, var0, var2);
            return null;
         }
      }, new Function<Aggregation.Distribution, Void>() {
         public Void apply(Aggregation.Distribution var1x) {
            AggregationData var2 = var1;
            ViewData.throwIfAggregationMismatch(var2 instanceof AggregationData.DistributionData, var0, var2);
            return null;
         }
      }, new Function<Aggregation.LastValue, Void>() {
         public Void apply(Aggregation.LastValue var1x) {
            var2.match(new Function<Measure.MeasureDouble, Void>() {
               public Void apply(Measure.MeasureDouble var1x) {
                  ViewData.throwIfAggregationMismatch(var1 instanceof AggregationData.LastValueDataDouble, var0, var1);
                  return null;
               }
            }, new Function<Measure.MeasureLong, Void>() {
               public Void apply(Measure.MeasureLong var1x) {
                  ViewData.throwIfAggregationMismatch(var1 instanceof AggregationData.LastValueDataLong, var0, var1);
                  return null;
               }
            }, Functions.throwAssertionError());
            return null;
         }
      }, new Function<Aggregation, Void>() {
         public Void apply(Aggregation var1x) {
            if (var1x instanceof Aggregation.Mean) {
               AggregationData var2 = var1;
               ViewData.throwIfAggregationMismatch(var2 instanceof AggregationData.MeanData, var0, var2);
               return null;
            } else {
               throw new AssertionError();
            }
         }
      });
   }

   private static void checkWindow(View.AggregationWindow var0, final ViewData.AggregationWindowData var1) {
      var0.match(new Function<View.AggregationWindow.Cumulative, Void>() {
         public Void apply(View.AggregationWindow.Cumulative var1x) {
            ViewData.AggregationWindowData var2 = var1;
            ViewData.throwIfWindowMismatch(var2 instanceof ViewData.AggregationWindowData.CumulativeData, var1x, var2);
            return null;
         }
      }, new Function<View.AggregationWindow.Interval, Void>() {
         public Void apply(View.AggregationWindow.Interval var1x) {
            ViewData.AggregationWindowData var2 = var1;
            ViewData.throwIfWindowMismatch(var2 instanceof ViewData.AggregationWindowData.IntervalData, var1x, var2);
            return null;
         }
      }, Functions.throwAssertionError());
   }

   public static ViewData create(View var0, Map<? extends List<TagValue>, ? extends AggregationData> var1, Timestamp var2, Timestamp var3) {
      HashMap var4 = new HashMap();
      Iterator var5 = var1.entrySet().iterator();

      while(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         checkAggregation(var0.getAggregation(), (AggregationData)var6.getValue(), var0.getMeasure());
         var4.put(Collections.unmodifiableList(new ArrayList((Collection)var6.getKey())), var6.getValue());
      }

      return createInternal(var0, Collections.unmodifiableMap(var4), ViewData.AggregationWindowData.CumulativeData.create(var2, var3), var2, var3);
   }

   @Deprecated
   public static ViewData create(final View var0, Map<? extends List<TagValue>, ? extends AggregationData> var1, ViewData.AggregationWindowData var2) {
      checkWindow(var0.getWindow(), var2);
      final HashMap var3 = new HashMap();
      Iterator var4 = var1.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         checkAggregation(var0.getAggregation(), (AggregationData)var5.getValue(), var0.getMeasure());
         var3.put(Collections.unmodifiableList(new ArrayList((Collection)var5.getKey())), var5.getValue());
      }

      return (ViewData)var2.match(new Function<ViewData.AggregationWindowData.CumulativeData, ViewData>() {
         public ViewData apply(ViewData.AggregationWindowData.CumulativeData var1) {
            return ViewData.createInternal(var0, Collections.unmodifiableMap(var3), var1, var1.getStart(), var1.getEnd());
         }
      }, new Function<ViewData.AggregationWindowData.IntervalData, ViewData>() {
         public ViewData apply(ViewData.AggregationWindowData.IntervalData var1) {
            Duration var2 = ((View.AggregationWindow.Interval)var0.getWindow()).getDuration();
            return ViewData.createInternal(var0, Collections.unmodifiableMap(var3), var1, var1.getEnd().addDuration(Duration.create(-var2.getSeconds(), -var2.getNanos())), var1.getEnd());
         }
      }, Functions.throwAssertionError());
   }

   private static String createErrorMessageForAggregation(Aggregation var0, AggregationData var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Aggregation and AggregationData types mismatch. Aggregation: ");
      var2.append(var0.getClass().getSimpleName());
      var2.append(" AggregationData: ");
      var2.append(var1.getClass().getSimpleName());
      return var2.toString();
   }

   private static String createErrorMessageForWindow(View.AggregationWindow var0, ViewData.AggregationWindowData var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("AggregationWindow and AggregationWindowData types mismatch. AggregationWindow: ");
      var2.append(var0.getClass().getSimpleName());
      var2.append(" AggregationWindowData: ");
      var2.append(var1.getClass().getSimpleName());
      return var2.toString();
   }

   private static ViewData createInternal(View var0, Map<List<TagValue>, AggregationData> var1, ViewData.AggregationWindowData var2, Timestamp var3, Timestamp var4) {
      return new AutoValue_ViewData(var0, var1, var2, var3, var4);
   }

   private static void throwIfAggregationMismatch(boolean var0, Aggregation var1, AggregationData var2) {
      if (!var0) {
         throw new IllegalArgumentException(createErrorMessageForAggregation(var1, var2));
      }
   }

   private static void throwIfWindowMismatch(boolean var0, View.AggregationWindow var1, ViewData.AggregationWindowData var2) {
      if (!var0) {
         throw new IllegalArgumentException(createErrorMessageForWindow(var1, var2));
      }
   }

   public abstract Map<List<TagValue>, AggregationData> getAggregationMap();

   public abstract Timestamp getEnd();

   public abstract Timestamp getStart();

   public abstract View getView();

   @Deprecated
   public abstract ViewData.AggregationWindowData getWindowData();

   @Deprecated
   public abstract static class AggregationWindowData {
      private AggregationWindowData() {
      }

      // $FF: synthetic method
      AggregationWindowData(Object var1) {
         this();
      }

      public abstract <T> T match(Function<? super ViewData.AggregationWindowData.CumulativeData, T> var1, Function<? super ViewData.AggregationWindowData.IntervalData, T> var2, Function<? super ViewData.AggregationWindowData, T> var3);

      @Deprecated
      public abstract static class CumulativeData extends ViewData.AggregationWindowData {
         CumulativeData() {
            super(null);
         }

         public static ViewData.AggregationWindowData.CumulativeData create(Timestamp var0, Timestamp var1) {
            if (var0.compareTo(var1) <= 0) {
               return new AutoValue_ViewData_AggregationWindowData_CumulativeData(var0, var1);
            } else {
               throw new IllegalArgumentException("Start time is later than end time.");
            }
         }

         public abstract Timestamp getEnd();

         public abstract Timestamp getStart();

         public final <T> T match(Function<? super ViewData.AggregationWindowData.CumulativeData, T> var1, Function<? super ViewData.AggregationWindowData.IntervalData, T> var2, Function<? super ViewData.AggregationWindowData, T> var3) {
            return var1.apply(this);
         }
      }

      @Deprecated
      public abstract static class IntervalData extends ViewData.AggregationWindowData {
         IntervalData() {
            super(null);
         }

         public static ViewData.AggregationWindowData.IntervalData create(Timestamp var0) {
            return new AutoValue_ViewData_AggregationWindowData_IntervalData(var0);
         }

         public abstract Timestamp getEnd();

         public final <T> T match(Function<? super ViewData.AggregationWindowData.CumulativeData, T> var1, Function<? super ViewData.AggregationWindowData.IntervalData, T> var2, Function<? super ViewData.AggregationWindowData, T> var3) {
            return var2.apply(this);
         }
      }
   }
}
