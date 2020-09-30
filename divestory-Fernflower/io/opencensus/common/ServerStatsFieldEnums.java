package io.opencensus.common;

import java.util.TreeMap;
import javax.annotation.Nullable;

public final class ServerStatsFieldEnums {
   private static final int TOTALSIZE = computeTotalSize();

   private ServerStatsFieldEnums() {
   }

   private static int computeTotalSize() {
      ServerStatsFieldEnums.Size[] var0 = ServerStatsFieldEnums.Size.values();
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 = var3 + var0[var2].value() + 1;
      }

      return var3;
   }

   public static int getTotalSize() {
      return TOTALSIZE;
   }

   public static enum Id {
      SERVER_STATS_LB_LATENCY_ID,
      SERVER_STATS_SERVICE_LATENCY_ID,
      SERVER_STATS_TRACE_OPTION_ID;

      private static final TreeMap<Integer, ServerStatsFieldEnums.Id> map;
      private final int value;

      static {
         int var0 = 0;
         SERVER_STATS_LB_LATENCY_ID = new ServerStatsFieldEnums.Id("SERVER_STATS_LB_LATENCY_ID", 0, 0);
         SERVER_STATS_SERVICE_LATENCY_ID = new ServerStatsFieldEnums.Id("SERVER_STATS_SERVICE_LATENCY_ID", 1, 1);
         ServerStatsFieldEnums.Id var1 = new ServerStatsFieldEnums.Id("SERVER_STATS_TRACE_OPTION_ID", 2, 2);
         SERVER_STATS_TRACE_OPTION_ID = var1;
         map = new TreeMap();
         ServerStatsFieldEnums.Id[] var4 = values();

         for(int var2 = var4.length; var0 < var2; ++var0) {
            ServerStatsFieldEnums.Id var3 = var4[var0];
            map.put(var3.value, var3);
         }

      }

      private Id(int var3) {
         this.value = var3;
      }

      @Nullable
      public static ServerStatsFieldEnums.Id valueOf(int var0) {
         return (ServerStatsFieldEnums.Id)map.get(var0);
      }

      public int value() {
         return this.value;
      }
   }

   public static enum Size {
      SERVER_STATS_LB_LATENCY_SIZE(8),
      SERVER_STATS_SERVICE_LATENCY_SIZE(8),
      SERVER_STATS_TRACE_OPTION_SIZE;

      private final int value;

      static {
         ServerStatsFieldEnums.Size var0 = new ServerStatsFieldEnums.Size("SERVER_STATS_TRACE_OPTION_SIZE", 2, 1);
         SERVER_STATS_TRACE_OPTION_SIZE = var0;
      }

      private Size(int var3) {
         this.value = var3;
      }

      public int value() {
         return this.value;
      }
   }
}
