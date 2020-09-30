package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class BackgroundDetector implements ActivityLifecycleCallbacks, ComponentCallbacks2 {
   private static final BackgroundDetector zza = new BackgroundDetector();
   private final AtomicBoolean zzb = new AtomicBoolean();
   private final AtomicBoolean zzc = new AtomicBoolean();
   private final ArrayList<BackgroundDetector.BackgroundStateChangeListener> zzd = new ArrayList();
   private boolean zze = false;

   private BackgroundDetector() {
   }

   public static BackgroundDetector getInstance() {
      return zza;
   }

   public static void initialize(Application var0) {
      BackgroundDetector var1 = zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (!zza.zze) {
               var0.registerActivityLifecycleCallbacks(zza);
               var0.registerComponentCallbacks(zza);
               zza.zze = true;
            }
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label119;
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
   }

   private final void zza(boolean var1) {
      BackgroundDetector var2 = zza;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label283: {
         ArrayList var3;
         int var4;
         try {
            var3 = (ArrayList)this.zzd;
            var4 = var3.size();
         } catch (Throwable var36) {
            var10000 = var36;
            var10001 = false;
            break label283;
         }

         int var5 = 0;

         while(var5 < var4) {
            Object var6;
            try {
               var6 = var3.get(var5);
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label283;
            }

            ++var5;

            try {
               ((BackgroundDetector.BackgroundStateChangeListener)var6).onBackgroundStateChanged(var1);
            } catch (Throwable var34) {
               var10000 = var34;
               var10001 = false;
               break label283;
            }
         }

         label265:
         try {
            return;
         } catch (Throwable var33) {
            var10000 = var33;
            var10001 = false;
            break label265;
         }
      }

      while(true) {
         Throwable var37 = var10000;

         try {
            throw var37;
         } catch (Throwable var32) {
            var10000 = var32;
            var10001 = false;
            continue;
         }
      }
   }

   public final void addListener(BackgroundDetector.BackgroundStateChangeListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final boolean isInBackground() {
      return this.zzb.get();
   }

   public final void onActivityCreated(Activity var1, Bundle var2) {
      boolean var3 = this.zzb.compareAndSet(true, false);
      this.zzc.set(true);
      if (var3) {
         this.zza(false);
      }

   }

   public final void onActivityDestroyed(Activity var1) {
   }

   public final void onActivityPaused(Activity var1) {
   }

   public final void onActivityResumed(Activity var1) {
      boolean var2 = this.zzb.compareAndSet(true, false);
      this.zzc.set(true);
      if (var2) {
         this.zza(false);
      }

   }

   public final void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public final void onActivityStarted(Activity var1) {
   }

   public final void onActivityStopped(Activity var1) {
   }

   public final void onConfigurationChanged(Configuration var1) {
   }

   public final void onLowMemory() {
   }

   public final void onTrimMemory(int var1) {
      if (var1 == 20 && this.zzb.compareAndSet(false, true)) {
         this.zzc.set(true);
         this.zza(true);
      }

   }

   public final boolean readCurrentStateIfPossible(boolean var1) {
      if (!this.zzc.get()) {
         if (!PlatformVersion.isAtLeastJellyBean()) {
            return var1;
         }

         RunningAppProcessInfo var2 = new RunningAppProcessInfo();
         ActivityManager.getMyMemoryState(var2);
         if (!this.zzc.getAndSet(true) && var2.importance > 100) {
            this.zzb.set(true);
         }
      }

      return this.isInBackground();
   }

   public interface BackgroundStateChangeListener {
      void onBackgroundStateChanged(boolean var1);
   }
}
