package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class LifecycleCallback {
   protected final LifecycleFragment mLifecycleFragment;

   protected LifecycleCallback(LifecycleFragment var1) {
      this.mLifecycleFragment = var1;
   }

   private static LifecycleFragment getChimeraLifecycleFragmentImpl(LifecycleActivity var0) {
      throw new IllegalStateException("Method not available in SDK.");
   }

   public static LifecycleFragment getFragment(Activity var0) {
      return getFragment(new LifecycleActivity(var0));
   }

   public static LifecycleFragment getFragment(ContextWrapper var0) {
      throw new UnsupportedOperationException();
   }

   protected static LifecycleFragment getFragment(LifecycleActivity var0) {
      if (var0.isSupport()) {
         return zzc.zza(var0.asFragmentActivity());
      } else if (var0.zza()) {
         return zzb.zza(var0.asActivity());
      } else {
         throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
   }

   public Activity getActivity() {
      return this.mLifecycleFragment.getLifecycleActivity();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
   }

   public void onCreate(Bundle var1) {
   }

   public void onDestroy() {
   }

   public void onResume() {
   }

   public void onSaveInstanceState(Bundle var1) {
   }

   public void onStart() {
   }

   public void onStop() {
   }
}
