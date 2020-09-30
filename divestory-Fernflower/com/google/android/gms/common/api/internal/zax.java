package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Preconditions;

public class zax extends zak {
   private final ArraySet<ApiKey<?>> zad;
   private final GoogleApiManager zae;

   private zax(LifecycleFragment var1, GoogleApiManager var2) {
      this(var1, var2, GoogleApiAvailability.getInstance());
   }

   private zax(LifecycleFragment var1, GoogleApiManager var2, GoogleApiAvailability var3) {
      super(var1, var3);
      this.zad = new ArraySet();
      this.zae = var2;
      this.mLifecycleFragment.addCallback("ConnectionlessLifecycleHelper", this);
   }

   public static void zaa(Activity var0, GoogleApiManager var1, ApiKey<?> var2) {
      LifecycleFragment var3 = getFragment(var0);
      zax var4 = (zax)var3.getCallbackOrNull("ConnectionlessLifecycleHelper", zax.class);
      zax var5 = var4;
      if (var4 == null) {
         var5 = new zax(var3, var1);
      }

      Preconditions.checkNotNull(var2, "ApiKey cannot be null");
      var5.zad.add(var2);
      var1.zaa(var5);
   }

   private final void zad() {
      if (!this.zad.isEmpty()) {
         this.zae.zaa(this);
      }

   }

   public void onResume() {
      super.onResume();
      this.zad();
   }

   public void onStart() {
      super.onStart();
      this.zad();
   }

   public void onStop() {
      super.onStop();
      this.zae.zab(this);
   }

   protected final void zaa() {
      this.zae.zac();
   }

   protected final void zaa(ConnectionResult var1, int var2) {
      this.zae.zab(var1, var2);
   }

   final ArraySet<ApiKey<?>> zac() {
      return this.zad;
   }
}
