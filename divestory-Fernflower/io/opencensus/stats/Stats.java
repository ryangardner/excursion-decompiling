package io.opencensus.stats;

import io.opencensus.internal.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Stats {
   private static final Logger logger = Logger.getLogger(Stats.class.getName());
   private static final StatsComponent statsComponent = loadStatsComponent(StatsComponent.class.getClassLoader());

   private Stats() {
   }

   public static StatsCollectionState getState() {
      return statsComponent.getState();
   }

   public static StatsRecorder getStatsRecorder() {
      return statsComponent.getStatsRecorder();
   }

   public static ViewManager getViewManager() {
      return statsComponent.getViewManager();
   }

   static StatsComponent loadStatsComponent(@Nullable ClassLoader var0) {
      try {
         StatsComponent var1 = (StatsComponent)Provider.createInstance(Class.forName("io.opencensus.impl.stats.StatsComponentImpl", true, var0), StatsComponent.class);
         return var1;
      } catch (ClassNotFoundException var3) {
         logger.log(Level.FINE, "Couldn't load full implementation for StatsComponent, now trying to load lite implementation.", var3);

         try {
            StatsComponent var4 = (StatsComponent)Provider.createInstance(Class.forName("io.opencensus.impllite.stats.StatsComponentImplLite", true, var0), StatsComponent.class);
            return var4;
         } catch (ClassNotFoundException var2) {
            logger.log(Level.FINE, "Couldn't load lite implementation for StatsComponent, now using default implementation for StatsComponent.", var2);
            return NoopStats.newNoopStatsComponent();
         }
      }
   }

   @Deprecated
   public static void setState(StatsCollectionState var0) {
      statsComponent.setState(var0);
   }
}
