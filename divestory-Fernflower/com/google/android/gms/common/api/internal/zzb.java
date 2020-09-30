package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import androidx.collection.ArrayMap;
import com.google.android.gms.internal.common.zzi;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;

public final class zzb extends Fragment implements LifecycleFragment {
   private static WeakHashMap<Activity, WeakReference<zzb>> zza = new WeakHashMap();
   private Map<String, LifecycleCallback> zzb = Collections.synchronizedMap(new ArrayMap());
   private int zzc = 0;
   private Bundle zzd;

   // $FF: synthetic method
   static int zza(zzb var0) {
      return var0.zzc;
   }

   public static zzb zza(Activity var0) {
      WeakReference var1 = (WeakReference)zza.get(var0);
      zzb var4;
      if (var1 != null) {
         var4 = (zzb)var1.get();
         if (var4 != null) {
            return var4;
         }
      }

      zzb var2;
      try {
         var2 = (zzb)var0.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
      } catch (ClassCastException var3) {
         throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", var3);
      }

      label20: {
         if (var2 != null) {
            var4 = var2;
            if (!var2.isRemoving()) {
               break label20;
            }
         }

         var4 = new zzb();
         var0.getFragmentManager().beginTransaction().add(var4, "LifecycleFragmentImpl").commitAllowingStateLoss();
      }

      zza.put(var0, new WeakReference(var4));
      return var4;
   }

   // $FF: synthetic method
   static Bundle zzb(zzb var0) {
      return var0.zzd;
   }

   public final void addCallback(String var1, LifecycleCallback var2) {
      if (!this.zzb.containsKey(var1)) {
         this.zzb.put(var1, var2);
         if (this.zzc > 0) {
            (new zzi(Looper.getMainLooper())).post(new zza(this, var2, var1));
         }

      } else {
         StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 59);
         var3.append("LifecycleCallback with tag ");
         var3.append(var1);
         var3.append(" already added to this fragment.");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      super.dump(var1, var2, var3, var4);
      Iterator var5 = this.zzb.values().iterator();

      while(var5.hasNext()) {
         ((LifecycleCallback)var5.next()).dump(var1, var2, var3, var4);
      }

   }

   public final <T extends LifecycleCallback> T getCallbackOrNull(String var1, Class<T> var2) {
      return (LifecycleCallback)var2.cast(this.zzb.get(var1));
   }

   public final Activity getLifecycleActivity() {
      return this.getActivity();
   }

   public final boolean isCreated() {
      return this.zzc > 0;
   }

   public final boolean isStarted() {
      return this.zzc >= 2;
   }

   public final void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      Iterator var4 = this.zzb.values().iterator();

      while(var4.hasNext()) {
         ((LifecycleCallback)var4.next()).onActivityResult(var1, var2, var3);
      }

   }

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.zzc = 1;
      this.zzd = var1;

      LifecycleCallback var4;
      Bundle var5;
      for(Iterator var2 = this.zzb.entrySet().iterator(); var2.hasNext(); var4.onCreate(var5)) {
         Entry var3 = (Entry)var2.next();
         var4 = (LifecycleCallback)var3.getValue();
         if (var1 != null) {
            var5 = var1.getBundle((String)var3.getKey());
         } else {
            var5 = null;
         }
      }

   }

   public final void onDestroy() {
      super.onDestroy();
      this.zzc = 5;
      Iterator var1 = this.zzb.values().iterator();

      while(var1.hasNext()) {
         ((LifecycleCallback)var1.next()).onDestroy();
      }

   }

   public final void onResume() {
      super.onResume();
      this.zzc = 3;
      Iterator var1 = this.zzb.values().iterator();

      while(var1.hasNext()) {
         ((LifecycleCallback)var1.next()).onResume();
      }

   }

   public final void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if (var1 != null) {
         Iterator var2 = this.zzb.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Bundle var4 = new Bundle();
            ((LifecycleCallback)var3.getValue()).onSaveInstanceState(var4);
            var1.putBundle((String)var3.getKey(), var4);
         }

      }
   }

   public final void onStart() {
      super.onStart();
      this.zzc = 2;
      Iterator var1 = this.zzb.values().iterator();

      while(var1.hasNext()) {
         ((LifecycleCallback)var1.next()).onStart();
      }

   }

   public final void onStop() {
      super.onStop();
      this.zzc = 4;
      Iterator var1 = this.zzb.values().iterator();

      while(var1.hasNext()) {
         ((LifecycleCallback)var1.next()).onStop();
      }

   }
}
