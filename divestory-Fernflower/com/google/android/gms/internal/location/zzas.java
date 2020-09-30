package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zzas {
   private final zzbj<zzao> zzcb;
   private final Context zzcu;
   private ContentProviderClient zzcv = null;
   private boolean zzcw = false;
   private final Map<ListenerHolder.ListenerKey<LocationListener>, zzax> zzcx = new HashMap();
   private final Map<ListenerHolder.ListenerKey<Object>, zzaw> zzcy = new HashMap();
   private final Map<ListenerHolder.ListenerKey<LocationCallback>, zzat> zzcz = new HashMap();

   public zzas(Context var1, zzbj<zzao> var2) {
      this.zzcu = var1;
      this.zzcb = var2;
   }

   private final zzax zza(ListenerHolder<LocationListener> var1) {
      Map var2 = this.zzcx;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label176: {
         zzax var3;
         try {
            var3 = (zzax)this.zzcx.get(var1.getListenerKey());
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label176;
         }

         zzax var4 = var3;
         if (var3 == null) {
            try {
               var4 = new zzax(var1);
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            this.zzcx.put(var1.getListenerKey(), var4);
            return var4;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   private final zzat zzb(ListenerHolder<LocationCallback> var1) {
      Map var2 = this.zzcz;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label176: {
         zzat var3;
         try {
            var3 = (zzat)this.zzcz.get(var1.getListenerKey());
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label176;
         }

         zzat var4 = var3;
         if (var3 == null) {
            try {
               var4 = new zzat(var1);
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            this.zzcz.put(var1.getListenerKey(), var4);
            return var4;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   public final Location getLastLocation() throws RemoteException {
      this.zzcb.checkConnected();
      return ((zzao)this.zzcb.getService()).zza(this.zzcu.getPackageName());
   }

   public final void removeAllListeners() throws RemoteException {
      Map var1 = this.zzcx;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      Throwable var246;
      label2109: {
         label2110: {
            Iterator var2;
            try {
               var2 = this.zzcx.values().iterator();
            } catch (Throwable var244) {
               var10000 = var244;
               var10001 = false;
               break label2110;
            }

            while(true) {
               zzax var3;
               try {
                  if (!var2.hasNext()) {
                     break;
                  }

                  var3 = (zzax)var2.next();
               } catch (Throwable var245) {
                  var10000 = var245;
                  var10001 = false;
                  break label2110;
               }

               if (var3 != null) {
                  try {
                     ((zzao)this.zzcb.getService()).zza(zzbf.zza((com.google.android.gms.location.zzx)var3, (zzaj)null));
                  } catch (Throwable var243) {
                     var10000 = var243;
                     var10001 = false;
                     break label2110;
                  }
               }
            }

            label2090:
            try {
               this.zzcx.clear();
               break label2109;
            } catch (Throwable var242) {
               var10000 = var242;
               var10001 = false;
               break label2090;
            }
         }

         while(true) {
            var246 = var10000;

            try {
               throw var246;
            } catch (Throwable var233) {
               var10000 = var233;
               var10001 = false;
               continue;
            }
         }
      }

      var1 = this.zzcz;
      synchronized(var1){}

      Iterator var248;
      label2111: {
         label2112: {
            try {
               var248 = this.zzcz.values().iterator();
            } catch (Throwable var240) {
               var10000 = var240;
               var10001 = false;
               break label2112;
            }

            while(true) {
               zzat var247;
               try {
                  if (!var248.hasNext()) {
                     break;
                  }

                  var247 = (zzat)var248.next();
               } catch (Throwable var241) {
                  var10000 = var241;
                  var10001 = false;
                  break label2112;
               }

               if (var247 != null) {
                  try {
                     ((zzao)this.zzcb.getService()).zza(zzbf.zza((com.google.android.gms.location.zzu)var247, (zzaj)null));
                  } catch (Throwable var239) {
                     var10000 = var239;
                     var10001 = false;
                     break label2112;
                  }
               }
            }

            label2070:
            try {
               this.zzcz.clear();
               break label2111;
            } catch (Throwable var238) {
               var10000 = var238;
               var10001 = false;
               break label2070;
            }
         }

         while(true) {
            var246 = var10000;

            try {
               throw var246;
            } catch (Throwable var232) {
               var10000 = var232;
               var10001 = false;
               continue;
            }
         }
      }

      var1 = this.zzcy;
      synchronized(var1){}

      label2113: {
         try {
            var248 = this.zzcy.values().iterator();
         } catch (Throwable var236) {
            var10000 = var236;
            var10001 = false;
            break label2113;
         }

         while(true) {
            zzaw var4;
            try {
               if (!var248.hasNext()) {
                  break;
               }

               var4 = (zzaw)var248.next();
            } catch (Throwable var237) {
               var10000 = var237;
               var10001 = false;
               break label2113;
            }

            if (var4 != null) {
               try {
                  zzao var5 = (zzao)this.zzcb.getService();
                  zzo var249 = new zzo(2, (zzm)null, var4.asBinder(), (IBinder)null);
                  var5.zza(var249);
               } catch (Throwable var235) {
                  var10000 = var235;
                  var10001 = false;
                  break label2113;
               }
            }
         }

         label2051:
         try {
            this.zzcy.clear();
            return;
         } catch (Throwable var234) {
            var10000 = var234;
            var10001 = false;
            break label2051;
         }
      }

      while(true) {
         var246 = var10000;

         try {
            throw var246;
         } catch (Throwable var231) {
            var10000 = var231;
            var10001 = false;
            continue;
         }
      }
   }

   public final LocationAvailability zza() throws RemoteException {
      this.zzcb.checkConnected();
      return ((zzao)this.zzcb.getService()).zzb(this.zzcu.getPackageName());
   }

   public final void zza(PendingIntent var1, zzaj var2) throws RemoteException {
      this.zzcb.checkConnected();
      zzao var3 = (zzao)this.zzcb.getService();
      IBinder var4;
      if (var2 != null) {
         var4 = var2.asBinder();
      } else {
         var4 = null;
      }

      var3.zza(new zzbf(2, (zzbd)null, (IBinder)null, var1, (IBinder)null, var4));
   }

   public final void zza(Location var1) throws RemoteException {
      this.zzcb.checkConnected();
      ((zzao)this.zzcb.getService()).zza(var1);
   }

   public final void zza(ListenerHolder.ListenerKey<LocationListener> var1, zzaj var2) throws RemoteException {
      this.zzcb.checkConnected();
      Preconditions.checkNotNull(var1, "Invalid null listener key");
      Map var3 = this.zzcx;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label176: {
         zzax var24;
         try {
            var24 = (zzax)this.zzcx.remove(var1);
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label176;
         }

         if (var24 != null) {
            try {
               var24.release();
               ((zzao)this.zzcb.getService()).zza(zzbf.zza((com.google.android.gms.location.zzx)var24, var2));
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            return;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zza(zzaj var1) throws RemoteException {
      this.zzcb.checkConnected();
      ((zzao)this.zzcb.getService()).zza(var1);
   }

   public final void zza(zzbd var1, ListenerHolder<LocationCallback> var2, zzaj var3) throws RemoteException {
      this.zzcb.checkConnected();
      zzat var6 = this.zzb(var2);
      zzao var4 = (zzao)this.zzcb.getService();
      IBinder var5 = var6.asBinder();
      IBinder var7;
      if (var3 != null) {
         var7 = var3.asBinder();
      } else {
         var7 = null;
      }

      var4.zza(new zzbf(1, var1, (IBinder)null, (PendingIntent)null, var5, var7));
   }

   public final void zza(LocationRequest var1, PendingIntent var2, zzaj var3) throws RemoteException {
      this.zzcb.checkConnected();
      zzao var4 = (zzao)this.zzcb.getService();
      zzbd var5 = zzbd.zza(var1);
      IBinder var6;
      if (var3 != null) {
         var6 = var3.asBinder();
      } else {
         var6 = null;
      }

      var4.zza(new zzbf(1, var5, (IBinder)null, var2, (IBinder)null, var6));
   }

   public final void zza(LocationRequest var1, ListenerHolder<LocationListener> var2, zzaj var3) throws RemoteException {
      this.zzcb.checkConnected();
      zzax var4 = this.zza(var2);
      zzao var7 = (zzao)this.zzcb.getService();
      zzbd var5 = zzbd.zza(var1);
      IBinder var8 = var4.asBinder();
      IBinder var6;
      if (var3 != null) {
         var6 = var3.asBinder();
      } else {
         var6 = null;
      }

      var7.zza(new zzbf(1, var5, var8, (PendingIntent)null, (IBinder)null, var6));
   }

   public final void zza(boolean var1) throws RemoteException {
      this.zzcb.checkConnected();
      ((zzao)this.zzcb.getService()).zza(var1);
      this.zzcw = var1;
   }

   public final void zzb() throws RemoteException {
      if (this.zzcw) {
         this.zza(false);
      }

   }

   public final void zzb(ListenerHolder.ListenerKey<LocationCallback> var1, zzaj var2) throws RemoteException {
      this.zzcb.checkConnected();
      Preconditions.checkNotNull(var1, "Invalid null listener key");
      Map var3 = this.zzcz;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label176: {
         zzat var24;
         try {
            var24 = (zzat)this.zzcz.remove(var1);
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label176;
         }

         if (var24 != null) {
            try {
               var24.release();
               ((zzao)this.zzcb.getService()).zza(zzbf.zza((com.google.android.gms.location.zzu)var24, var2));
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            return;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }
}
