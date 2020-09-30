package io.opencensus.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ServerStatsEncoding {
   public static final byte CURRENT_VERSION = 0;

   private ServerStatsEncoding() {
   }

   public static ServerStats parseBytes(byte[] var0) throws ServerStatsDeserializationException {
      ByteBuffer var1 = ByteBuffer.wrap(var0);
      var1.order(ByteOrder.LITTLE_ENDIAN);
      if (var1.hasRemaining()) {
         byte var2 = var1.get();
         StringBuilder var9;
         if (var2 <= 0 && var2 >= 0) {
            long var3 = 0L;
            long var5 = 0L;
            byte var12 = 0;
            byte var7 = var12;

            while(var1.hasRemaining()) {
               ServerStatsFieldEnums.Id var10 = ServerStatsFieldEnums.Id.valueOf(var1.get() & 255);
               if (var10 == null) {
                  var1.position(var1.limit());
               } else {
                  int var13 = null.$SwitchMap$io$opencensus$common$ServerStatsFieldEnums$Id[var10.ordinal()];
                  if (var13 != 1) {
                     if (var13 != 2) {
                        if (var13 == 3) {
                           var2 = var1.get();
                           var7 = var2;
                        }
                     } else {
                        var5 = var1.getLong();
                     }
                  } else {
                     var3 = var1.getLong();
                  }
               }
            }

            try {
               ServerStats var11 = ServerStats.create(var3, var5, var7);
               return var11;
            } catch (IllegalArgumentException var8) {
               var9 = new StringBuilder();
               var9.append("Serialized ServiceStats contains invalid values: ");
               var9.append(var8.getMessage());
               throw new ServerStatsDeserializationException(var9.toString());
            }
         } else {
            var9 = new StringBuilder();
            var9.append("Invalid ServerStats version: ");
            var9.append(var2);
            throw new ServerStatsDeserializationException(var9.toString());
         }
      } else {
         throw new ServerStatsDeserializationException("Serialized ServerStats buffer is empty");
      }
   }

   public static byte[] toBytes(ServerStats var0) {
      ByteBuffer var1 = ByteBuffer.allocate(ServerStatsFieldEnums.getTotalSize() + 1);
      var1.order(ByteOrder.LITTLE_ENDIAN);
      var1.put((byte)0);
      var1.put((byte)ServerStatsFieldEnums.Id.SERVER_STATS_LB_LATENCY_ID.value());
      var1.putLong(var0.getLbLatencyNs());
      var1.put((byte)ServerStatsFieldEnums.Id.SERVER_STATS_SERVICE_LATENCY_ID.value());
      var1.putLong(var0.getServiceLatencyNs());
      var1.put((byte)ServerStatsFieldEnums.Id.SERVER_STATS_TRACE_OPTION_ID.value());
      var1.put(var0.getTraceOption());
      return var1.array();
   }
}
