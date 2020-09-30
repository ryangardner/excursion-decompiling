package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

final class zac implements DeferredLifecycleHelper.zaa {
   // $FF: synthetic field
   private final FrameLayout zaa;
   // $FF: synthetic field
   private final LayoutInflater zab;
   // $FF: synthetic field
   private final ViewGroup zac;
   // $FF: synthetic field
   private final Bundle zad;
   // $FF: synthetic field
   private final DeferredLifecycleHelper zae;

   zac(DeferredLifecycleHelper var1, FrameLayout var2, LayoutInflater var3, ViewGroup var4, Bundle var5) {
      this.zae = var1;
      this.zaa = var2;
      this.zab = var3;
      this.zac = var4;
      this.zad = var5;
   }

   public final int zaa() {
      return 2;
   }

   public final void zaa(LifecycleDelegate var1) {
      this.zaa.removeAllViews();
      this.zaa.addView(DeferredLifecycleHelper.zab(this.zae).onCreateView(this.zab, this.zac, this.zad));
   }
}
