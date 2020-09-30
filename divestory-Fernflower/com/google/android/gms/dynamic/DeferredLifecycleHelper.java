package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.LinkedList;

public abstract class DeferredLifecycleHelper<T extends LifecycleDelegate> {
   private T zaa;
   private Bundle zab;
   private LinkedList<DeferredLifecycleHelper.zaa> zac;
   private final OnDelegateCreatedListener<T> zad = new zab(this);

   public static void showGooglePlayUnavailableMessage(FrameLayout var0) {
      GoogleApiAvailability var1 = GoogleApiAvailability.getInstance();
      Context var2 = var0.getContext();
      int var3 = var1.isGooglePlayServicesAvailable(var2);
      String var4 = com.google.android.gms.common.internal.zac.zac(var2, var3);
      String var5 = com.google.android.gms.common.internal.zac.zae(var2, var3);
      LinearLayout var6 = new LinearLayout(var0.getContext());
      var6.setOrientation(1);
      var6.setLayoutParams(new LayoutParams(-2, -2));
      var0.addView(var6);
      TextView var7 = new TextView(var0.getContext());
      var7.setLayoutParams(new LayoutParams(-2, -2));
      var7.setText(var4);
      var6.addView(var7);
      Intent var8 = var1.getErrorResolutionIntent(var2, var3, (String)null);
      if (var8 != null) {
         Button var9 = new Button(var2);
         var9.setId(16908313);
         var9.setLayoutParams(new LayoutParams(-2, -2));
         var9.setText(var5);
         var6.addView(var9);
         var9.setOnClickListener(new zaf(var2, var8));
      }

   }

   // $FF: synthetic method
   static Bundle zaa(DeferredLifecycleHelper var0, Bundle var1) {
      var0.zab = null;
      return null;
   }

   // $FF: synthetic method
   static LifecycleDelegate zaa(DeferredLifecycleHelper var0, LifecycleDelegate var1) {
      var0.zaa = var1;
      return var1;
   }

   // $FF: synthetic method
   static LinkedList zaa(DeferredLifecycleHelper var0) {
      return var0.zac;
   }

   private final void zaa(int var1) {
      while(!this.zac.isEmpty() && ((DeferredLifecycleHelper.zaa)this.zac.getLast()).zaa() >= var1) {
         this.zac.removeLast();
      }

   }

   private final void zaa(Bundle var1, DeferredLifecycleHelper.zaa var2) {
      LifecycleDelegate var3 = this.zaa;
      if (var3 != null) {
         var2.zaa(var3);
      } else {
         if (this.zac == null) {
            this.zac = new LinkedList();
         }

         this.zac.add(var2);
         if (var1 != null) {
            Bundle var4 = this.zab;
            if (var4 == null) {
               this.zab = (Bundle)var1.clone();
            } else {
               var4.putAll(var1);
            }
         }

         this.createDelegate(this.zad);
      }
   }

   // $FF: synthetic method
   static LifecycleDelegate zab(DeferredLifecycleHelper var0) {
      return var0.zaa;
   }

   protected abstract void createDelegate(OnDelegateCreatedListener<T> var1);

   public T getDelegate() {
      return this.zaa;
   }

   protected void handleGooglePlayUnavailable(FrameLayout var1) {
      showGooglePlayUnavailableMessage(var1);
   }

   public void onCreate(Bundle var1) {
      this.zaa((Bundle)var1, (DeferredLifecycleHelper.zaa)(new zad(this, var1)));
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      FrameLayout var4 = new FrameLayout(var1.getContext());
      this.zaa((Bundle)var3, (DeferredLifecycleHelper.zaa)(new zac(this, var4, var1, var2, var3)));
      if (this.zaa == null) {
         this.handleGooglePlayUnavailable(var4);
      }

      return var4;
   }

   public void onDestroy() {
      LifecycleDelegate var1 = this.zaa;
      if (var1 != null) {
         var1.onDestroy();
      } else {
         this.zaa(1);
      }
   }

   public void onDestroyView() {
      LifecycleDelegate var1 = this.zaa;
      if (var1 != null) {
         var1.onDestroyView();
      } else {
         this.zaa(2);
      }
   }

   public void onInflate(Activity var1, Bundle var2, Bundle var3) {
      this.zaa((Bundle)var3, (DeferredLifecycleHelper.zaa)(new com.google.android.gms.dynamic.zaa(this, var1, var2, var3)));
   }

   public void onLowMemory() {
      LifecycleDelegate var1 = this.zaa;
      if (var1 != null) {
         var1.onLowMemory();
      }

   }

   public void onPause() {
      LifecycleDelegate var1 = this.zaa;
      if (var1 != null) {
         var1.onPause();
      } else {
         this.zaa(5);
      }
   }

   public void onResume() {
      this.zaa((Bundle)null, (DeferredLifecycleHelper.zaa)(new zag(this)));
   }

   public void onSaveInstanceState(Bundle var1) {
      LifecycleDelegate var2 = this.zaa;
      if (var2 != null) {
         var2.onSaveInstanceState(var1);
      } else {
         Bundle var3 = this.zab;
         if (var3 != null) {
            var1.putAll(var3);
         }

      }
   }

   public void onStart() {
      this.zaa((Bundle)null, (DeferredLifecycleHelper.zaa)(new zae(this)));
   }

   public void onStop() {
      LifecycleDelegate var1 = this.zaa;
      if (var1 != null) {
         var1.onStop();
      } else {
         this.zaa(4);
      }
   }

   private interface zaa {
      int zaa();

      void zaa(LifecycleDelegate var1);
   }
}
