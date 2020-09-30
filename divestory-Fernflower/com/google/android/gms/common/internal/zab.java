package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.api.internal.LifecycleFragment;

public abstract class zab implements OnClickListener {
   public static zab zaa(Activity var0, Intent var1, int var2) {
      return new zae(var1, var0, var2);
   }

   public static zab zaa(Fragment var0, Intent var1, int var2) {
      return new zad(var1, var0, var2);
   }

   public static zab zaa(LifecycleFragment var0, Intent var1, int var2) {
      return new zaf(var1, var0, 2);
   }

   public void onClick(DialogInterface param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   protected abstract void zaa();
}
