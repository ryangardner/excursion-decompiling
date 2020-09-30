package com.google.android.gms.drive.events;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.internal.drive.zzet;
import com.google.android.gms.internal.drive.zzfp;
import com.google.android.gms.internal.drive.zzir;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DriveEventService extends Service implements ChangeListener, CompletionListener, zzd, zzi {
   public static final String ACTION_HANDLE_EVENT = "com.google.android.gms.drive.events.HANDLE_EVENT";
   private static final GmsLogger zzbz = new GmsLogger("DriveEventService", "");
   private final String name;
   private CountDownLatch zzcj;
   DriveEventService.zza zzck;
   boolean zzcl;
   private int zzcm;

   protected DriveEventService() {
      this("DriveEventService");
   }

   protected DriveEventService(String var1) {
      this.zzcl = false;
      this.zzcm = -1;
      this.name = var1;
   }

   private final void zza(zzfp var1) {
      DriveEvent var9 = var1.zzat();

      Exception var10000;
      label51: {
         int var2;
         boolean var10001;
         try {
            var2 = var9.getType();
         } catch (Exception var8) {
            var10000 = var8;
            var10001 = false;
            break label51;
         }

         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 4) {
                  if (var2 != 7) {
                     try {
                        zzbz.wfmt("DriveEventService", "Unhandled event: %s", var9);
                        return;
                     } catch (Exception var3) {
                        var10000 = var3;
                        var10001 = false;
                     }
                  } else {
                     try {
                        zzv var10 = (zzv)var9;
                        zzbz.wfmt("DriveEventService", "Unhandled transfer state event in %s: %s", this.name, var10);
                        return;
                     } catch (Exception var4) {
                        var10000 = var4;
                        var10001 = false;
                     }
                  }
               } else {
                  try {
                     this.zza((com.google.android.gms.drive.events.zzb)var9);
                     return;
                  } catch (Exception var5) {
                     var10000 = var5;
                     var10001 = false;
                  }
               }
            } else {
               try {
                  this.onCompletion((CompletionEvent)var9);
                  return;
               } catch (Exception var6) {
                  var10000 = var6;
                  var10001 = false;
               }
            }
         } else {
            try {
               this.onChange((ChangeEvent)var9);
               return;
            } catch (Exception var7) {
               var10000 = var7;
               var10001 = false;
            }
         }
      }

      Exception var11 = var10000;
      zzbz.e("DriveEventService", String.format("Error handling event in %s", this.name), var11);
   }

   // $FF: synthetic method
   static CountDownLatch zzb(DriveEventService var0) {
      return var0.zzcj;
   }

   private final void zzw() throws SecurityException {
      int var1 = this.getCallingUid();
      if (var1 != this.zzcm) {
         if (UidVerifier.isGooglePlayServicesUid(this, var1)) {
            this.zzcm = var1;
         } else {
            throw new SecurityException("Caller is not GooglePlayServices");
         }
      }
   }

   protected int getCallingUid() {
      return Binder.getCallingUid();
   }

   public final IBinder onBind(Intent param1) {
      // $FF: Couldn't be decompiled
   }

   public void onChange(ChangeEvent var1) {
      zzbz.wfmt("DriveEventService", "Unhandled change event in %s: %s", this.name, var1);
   }

   public void onCompletion(CompletionEvent var1) {
      zzbz.wfmt("DriveEventService", "Unhandled completion event in %s: %s", this.name, var1);
   }

   public void onDestroy() {
      synchronized(this){}

      try {
         if (this.zzck != null) {
            Message var1 = this.zzck.zzy();
            this.zzck.sendMessage(var1);
            this.zzck = null;

            try {
               if (!this.zzcj.await(5000L, TimeUnit.MILLISECONDS)) {
                  zzbz.w("DriveEventService", "Failed to synchronously quit event handler. Will quit itself");
               }
            } catch (InterruptedException var4) {
            }

            this.zzcj = null;
         }

         super.onDestroy();
      } finally {
         ;
      }

   }

   public boolean onUnbind(Intent var1) {
      return true;
   }

   public final void zza(com.google.android.gms.drive.events.zzb var1) {
      zzbz.wfmt("DriveEventService", "Unhandled changes available event in %s: %s", this.name, var1);
   }

   static final class zza extends zzir {
      private final WeakReference<DriveEventService> zzcp;

      private zza(DriveEventService var1) {
         this.zzcp = new WeakReference(var1);
      }

      // $FF: synthetic method
      zza(DriveEventService var1, zzh var2) {
         this(var1);
      }

      private final Message zzb(zzfp var1) {
         return this.obtainMessage(1, var1);
      }

      private final Message zzy() {
         return this.obtainMessage(2);
      }

      public final void handleMessage(Message var1) {
         int var2 = var1.what;
         if (var2 != 1) {
            if (var2 != 2) {
               DriveEventService.zzbz.wfmt("DriveEventService", "Unexpected message type: %s", var1.what);
            } else {
               this.getLooper().quit();
            }
         } else {
            DriveEventService var3 = (DriveEventService)this.zzcp.get();
            if (var3 != null) {
               var3.zza((zzfp)var1.obj);
            } else {
               this.getLooper().quit();
            }
         }
      }
   }

   final class zzb extends zzet {
      private zzb() {
      }

      // $FF: synthetic method
      zzb(zzh var2) {
         this();
      }

      public final void zzc(zzfp var1) throws RemoteException {
         DriveEventService var2 = DriveEventService.this;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label195: {
            label189: {
               try {
                  DriveEventService.this.zzw();
                  if (DriveEventService.this.zzck != null) {
                     Message var23 = DriveEventService.this.zzck.zzb(var1);
                     DriveEventService.this.zzck.sendMessage(var23);
                     break label189;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label195;
               }

               try {
                  DriveEventService.zzbz.e("DriveEventService", "Receiving event before initialize is completed.");
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label195;
               }
            }

            label180:
            try {
               return;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label180;
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
   }
}
