package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.util.ClientLibraryUtils;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;

public class ConnectionTracker {
   private static final Object zza = new Object();
   @Nullable
   private static volatile ConnectionTracker zzb;
   private static boolean zzc = false;
   private ConcurrentHashMap<ServiceConnection, ServiceConnection> zzd = new ConcurrentHashMap();

   private ConnectionTracker() {
   }

   public static ConnectionTracker getInstance() {
      if (zzb == null) {
         Object var0 = zza;
         synchronized(var0){}

         Throwable var10000;
         boolean var10001;
         label144: {
            try {
               if (zzb == null) {
                  ConnectionTracker var1 = new ConnectionTracker();
                  zzb = var1;
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label144;
            }

            label141:
            try {
               return (ConnectionTracker)Preconditions.checkNotNull(zzb);
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label141;
            }
         }

         while(true) {
            Throwable var14 = var10000;

            try {
               throw var14;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               continue;
            }
         }
      } else {
         return (ConnectionTracker)Preconditions.checkNotNull(zzb);
      }
   }

   private final boolean zza(Context var1, String var2, Intent var3, ServiceConnection var4, int var5, boolean var6) {
      ComponentName var7 = var3.getComponent();
      if (var7 == null) {
         var6 = false;
      } else {
         var6 = ClientLibraryUtils.zza(var1, var7.getPackageName());
      }

      if (var6) {
         Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
         return false;
      } else {
         if (zza(var4)) {
            ServiceConnection var12 = (ServiceConnection)this.zzd.putIfAbsent(var4, var4);
            if (var12 != null && var4 != var12) {
               Log.w("ConnectionTracker", String.format("Duplicate binding with the same ServiceConnection: %s, %s, %s.", var4, var2, var3.getAction()));
            }

            boolean var10 = false;

            boolean var8;
            try {
               var10 = true;
               var8 = var1.bindService(var3, var4, var5);
               var10 = false;
            } finally {
               if (var10) {
                  this.zzd.remove(var4, var4);
               }
            }

            var6 = var8;
            if (!var8) {
               this.zzd.remove(var4, var4);
               var6 = var8;
            }
         } else {
            var6 = var1.bindService(var3, var4, var5);
         }

         return var6;
      }
   }

   private static boolean zza(ServiceConnection var0) {
      return !(var0 instanceof zzk);
   }

   public boolean bindService(Context var1, Intent var2, ServiceConnection var3, int var4) {
      return this.zza(var1, var1.getClass().getName(), var2, var3, var4);
   }

   public void unbindService(Context var1, ServiceConnection var2) {
      if (zza(var2) && this.zzd.containsKey(var2)) {
         try {
            ServiceConnection var3 = (ServiceConnection)this.zzd.get(var2);

            try {
               var1.unbindService(var3);
            } catch (IllegalStateException | IllegalArgumentException var7) {
            }
         } finally {
            this.zzd.remove(var2);
         }

      } else {
         try {
            var1.unbindService(var2);
         } catch (IllegalStateException | IllegalArgumentException var9) {
         }

      }
   }

   public void unbindServiceSafe(Context var1, ServiceConnection var2) {
      try {
         this.unbindService(var1, var2);
      } catch (IllegalArgumentException var3) {
         Log.w("ConnectionTracker", "Exception thrown while unbinding", var3);
      }
   }

   public final boolean zza(Context var1, String var2, Intent var3, ServiceConnection var4, int var5) {
      return this.zza(var1, var2, var3, var4, var5, true);
   }
}
