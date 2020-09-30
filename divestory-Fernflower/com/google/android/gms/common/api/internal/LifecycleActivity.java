package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.ContextWrapper;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.internal.Preconditions;

public class LifecycleActivity {
   private final Object zza;

   public LifecycleActivity(Activity var1) {
      this.zza = Preconditions.checkNotNull(var1, "Activity must not be null");
   }

   public LifecycleActivity(ContextWrapper var1) {
      throw new UnsupportedOperationException();
   }

   public Activity asActivity() {
      return (Activity)this.zza;
   }

   public FragmentActivity asFragmentActivity() {
      return (FragmentActivity)this.zza;
   }

   public Object asObject() {
      return this.zza;
   }

   public boolean isChimera() {
      return false;
   }

   public boolean isSupport() {
      return this.zza instanceof FragmentActivity;
   }

   public final boolean zza() {
      return this.zza instanceof Activity;
   }
}
