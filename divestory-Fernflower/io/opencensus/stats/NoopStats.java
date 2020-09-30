package io.opencensus.stats;

import io.opencensus.common.Functions;
import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.tags.TagContext;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

final class NoopStats {
   private NoopStats() {
   }

   static StatsRecorder getNoopStatsRecorder() {
      return NoopStats.NoopStatsRecorder.INSTANCE;
   }

   static MeasureMap newNoopMeasureMap() {
      return new NoopStats.NoopMeasureMap();
   }

   static StatsComponent newNoopStatsComponent() {
      return new NoopStats.NoopStatsComponent();
   }

   static ViewManager newNoopViewManager() {
      return new NoopStats.NoopViewManager();
   }

   private static final class NoopMeasureMap extends MeasureMap {
      private static final Logger logger = Logger.getLogger(NoopStats.NoopMeasureMap.class.getName());
      private boolean hasUnsupportedValues;

      private NoopMeasureMap() {
      }

      // $FF: synthetic method
      NoopMeasureMap(Object var1) {
         this();
      }

      public MeasureMap put(Measure.MeasureDouble var1, double var2) {
         if (var2 < 0.0D) {
            this.hasUnsupportedValues = true;
         }

         return this;
      }

      public MeasureMap put(Measure.MeasureLong var1, long var2) {
         if (var2 < 0L) {
            this.hasUnsupportedValues = true;
         }

         return this;
      }

      public void record() {
      }

      public void record(TagContext var1) {
         Utils.checkNotNull(var1, "tags");
         if (this.hasUnsupportedValues) {
            logger.log(Level.WARNING, "Dropping values, value to record must be non-negative.");
         }

      }
   }

   private static final class NoopStatsComponent extends StatsComponent {
      private volatile boolean isRead;
      private final ViewManager viewManager;

      private NoopStatsComponent() {
         this.viewManager = NoopStats.newNoopViewManager();
      }

      // $FF: synthetic method
      NoopStatsComponent(Object var1) {
         this();
      }

      public StatsCollectionState getState() {
         this.isRead = true;
         return StatsCollectionState.DISABLED;
      }

      public StatsRecorder getStatsRecorder() {
         return NoopStats.getNoopStatsRecorder();
      }

      public ViewManager getViewManager() {
         return this.viewManager;
      }

      @Deprecated
      public void setState(StatsCollectionState var1) {
         Utils.checkNotNull(var1, "state");
         Utils.checkState(this.isRead ^ true, "State was already read, cannot set state.");
      }
   }

   private static final class NoopStatsRecorder extends StatsRecorder {
      static final StatsRecorder INSTANCE = new NoopStats.NoopStatsRecorder();

      public MeasureMap newMeasureMap() {
         return NoopStats.newNoopMeasureMap();
      }
   }

   private static final class NoopViewManager extends ViewManager {
      private static final Timestamp ZERO_TIMESTAMP = Timestamp.create(0L, 0);
      @Nullable
      private volatile Set<View> exportedViews;
      private final Map<View.Name, View> registeredViews;

      private NoopViewManager() {
         this.registeredViews = new HashMap();
      }

      // $FF: synthetic method
      NoopViewManager(Object var1) {
         this();
      }

      private static Set<View> filterExportedViews(Collection<View> var0) {
         HashSet var1 = new HashSet();
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            View var3 = (View)var2.next();
            if (!(var3.getWindow() instanceof View.AggregationWindow.Interval)) {
               var1.add(var3);
            }
         }

         return Collections.unmodifiableSet(var1);
      }

      public Set<View> getAllExportedViews() {
         // $FF: Couldn't be decompiled
      }

      @Nullable
      public ViewData getView(View.Name var1) {
         Utils.checkNotNull(var1, "name");
         Map var2 = this.registeredViews;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label164: {
            View var23;
            try {
               var23 = (View)this.registeredViews.get(var1);
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label164;
            }

            if (var23 == null) {
               label158:
               try {
                  return null;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label158;
               }
            } else {
               label160:
               try {
                  ViewData var25 = ViewData.create(var23, Collections.emptyMap(), (ViewData.AggregationWindowData)var23.getWindow().match(Functions.returnConstant(ViewData.AggregationWindowData.CumulativeData.create(ZERO_TIMESTAMP, ZERO_TIMESTAMP)), Functions.returnConstant(ViewData.AggregationWindowData.IntervalData.create(ZERO_TIMESTAMP)), Functions.throwAssertionError()));
                  return var25;
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label160;
               }
            }
         }

         while(true) {
            Throwable var24 = var10000;

            try {
               throw var24;
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               continue;
            }
         }
      }

      public void registerView(View var1) {
         Utils.checkNotNull(var1, "newView");
         Map var2 = this.registeredViews;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label380: {
            View var3;
            try {
               this.exportedViews = null;
               var3 = (View)this.registeredViews.get(var1.getName());
            } catch (Throwable var46) {
               var10000 = var46;
               var10001 = false;
               break label380;
            }

            boolean var4;
            label372: {
               label371: {
                  if (var3 != null) {
                     try {
                        if (!var1.equals(var3)) {
                           break label371;
                        }
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label380;
                     }
                  }

                  var4 = true;
                  break label372;
               }

               var4 = false;
            }

            try {
               Utils.checkArgument(var4, "A different view with the same name already exists.");
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label380;
            }

            if (var3 == null) {
               try {
                  this.registeredViews.put(var1.getName(), var1);
               } catch (Throwable var43) {
                  var10000 = var43;
                  var10001 = false;
                  break label380;
               }
            }

            label357:
            try {
               return;
            } catch (Throwable var42) {
               var10000 = var42;
               var10001 = false;
               break label357;
            }
         }

         while(true) {
            Throwable var47 = var10000;

            try {
               throw var47;
            } catch (Throwable var41) {
               var10000 = var41;
               var10001 = false;
               continue;
            }
         }
      }
   }
}
