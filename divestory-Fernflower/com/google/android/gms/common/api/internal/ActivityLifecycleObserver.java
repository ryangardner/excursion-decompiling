package com.google.android.gms.common.api.internal;

import android.app.Activity;

public abstract class ActivityLifecycleObserver {
   public static final ActivityLifecycleObserver of(Activity var0) {
      return new zaa(var0);
   }

   public abstract ActivityLifecycleObserver onStopCallOnce(Runnable var1);
}
