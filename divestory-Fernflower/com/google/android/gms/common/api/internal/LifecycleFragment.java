package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;

public interface LifecycleFragment {
   void addCallback(String var1, LifecycleCallback var2);

   <T extends LifecycleCallback> T getCallbackOrNull(String var1, Class<T> var2);

   Activity getLifecycleActivity();

   boolean isCreated();

   boolean isStarted();

   void startActivityForResult(Intent var1, int var2);
}
