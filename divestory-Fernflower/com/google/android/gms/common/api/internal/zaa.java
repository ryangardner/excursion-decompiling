package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class zaa extends ActivityLifecycleObserver {
   private final WeakReference<com.google.android.gms.common.api.internal.zaa.zaa> zaa;

   public zaa(Activity var1) {
      this(com.google.android.gms.common.api.internal.zaa.zaa.zab(var1));
   }

   private zaa(com.google.android.gms.common.api.internal.zaa.zaa var1) {
      this.zaa = new WeakReference(var1);
   }

   public final ActivityLifecycleObserver onStopCallOnce(Runnable var1) {
      com.google.android.gms.common.api.internal.zaa.zaa var2 = (com.google.android.gms.common.api.internal.zaa.zaa)this.zaa.get();
      if (var2 != null) {
         var2.zaa(var1);
         return this;
      } else {
         throw new IllegalStateException("The target activity has already been GC'd");
      }
   }

   static class zaa extends LifecycleCallback {
      private List<Runnable> zaa = new ArrayList();

      private zaa(LifecycleFragment var1) {
         super(var1);
         this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
      }

      private final void zaa(Runnable var1) {
         synchronized(this){}

         try {
            this.zaa.add(var1);
         } finally {
            ;
         }

      }

      private static com.google.android.gms.common.api.internal.zaa.zaa zab(Activity var0) {
         synchronized(var0){}

         Throwable var10000;
         boolean var10001;
         label176: {
            LifecycleFragment var1;
            com.google.android.gms.common.api.internal.zaa.zaa var2;
            try {
               var1 = getFragment(var0);
               var2 = (com.google.android.gms.common.api.internal.zaa.zaa)var1.getCallbackOrNull("LifecycleObserverOnStop", com.google.android.gms.common.api.internal.zaa.zaa.class);
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label176;
            }

            com.google.android.gms.common.api.internal.zaa.zaa var3 = var2;
            if (var2 == null) {
               try {
                  var3 = new com.google.android.gms.common.api.internal.zaa.zaa(var1);
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label176;
               }
            }

            label165:
            try {
               return var3;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label165;
            }
         }

         while(true) {
            Throwable var24 = var10000;

            try {
               throw var24;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               continue;
            }
         }
      }

      public void onStop() {
         // $FF: Couldn't be decompiled
      }
   }
}
